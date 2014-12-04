angular.module('MscIndex.Processes', [])

  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'processes',
      url: '/processes',
      abstract: true,
      templateUrl: 'processes/index.tpl.jade',
      controller: 'ProcessesCtrl',
      data: {pageTitle: '进程管理', default: 'processes.list'}
    $stateProvider.state 'processes.list',
      url: '/list',
      templateUrl: 'processes/list.tpl.jade'
      controller: 'ProcessListCtrl',
      data: {pageTitle: '进程列表'}
    $stateProvider.state 'processes.new',
      url: '/new/{accountSn}',
      templateUrl: 'processes/new.tpl.jade'
      controller: 'ProcessNewCtrl',
      data: {pageTitle: '新增进程'}
    $stateProvider.state 'processes.view',
      url: '/{name}',
      templateUrl: 'processes/view.tpl.jade'
      controller: 'ProcessViewCtrl',
      data: {pageTitle: '查看进程'}
    $urlRouterProvider.when '/processes', '/processes/list'

  .controller('ProcessesCtrl', ['$scope', '$resource', 'Feedback', 'CacheService', \
                                ($scope,   $resource,   feedback,   CacheService) ->
    console.log("Initialized the Processes controller")
    $scope.options = {page: 1, count: 10}
    actions =
      start:  {method: 'PUT', params: {action: 'start'}}
      stop:   {method: 'PUT', params: {action: 'stop'}}
    $scope.services =  $resource("/admin/api/processes/:name", {name: "@name"})
    $scope.service = $resource("/admin/api/processes/:name/:action", {name: "@name"}, actions)
    $scope.cacheService = new CacheService "name", (value)->
      data = {}
      $.ajax
        url:    "/admin/api/processes/#{value}"
        async:  false
        type:   "GET"
        success: (response)->
          data = response
      return data

    $scope.submited = false
    Processes = $scope.services
    Process = $scope.service
    CancelAction = $resource("/admin/api/processes/:name/cancel/:job", {name: "@name", job: "@job"}, {cancel: {method: "PUT"}})

    $scope.execStart = (process, succCallback, errCallback)->
      acc = new Process process
      acc.$start (data)->
        feedback.success "正在启动 #{process.name} 进程"
        succCallback(data["job"], "start") if succCallback
      , (resp)->
        feedback.error "启动 #{process.name} 进程失败", resp
        errCallback() if errCallback

    $scope.execStop = (process, succCallback, errCallback)->
      acc = new Process process
      acc.$stop (data)->
        feedback.success "正在停止 #{process.name} 进程"
        succCallback(data["job"], "stop") if succCallback
      , (resp)->
        feedback.error "停止 #{process.name} 进程失败", resp
        errCallback() if errCallback

    $scope.execCancel = (process, type, succCallback, errCallback)->
      if type is "starting"
        invokeId = process.configuration.startInvocationId
        status = "启动中"
      else
        invokeId = process.configuration.stopInvocationId
        status = "停止中"
      process.job = invokeId
      acc = new CancelAction process
      acc.$cancel ->
        feedback.success("正在取消#{status}的进程 #{process.name}")
        succCallback() if succCallback
      , (resp)->
        feedback.error "取消#{status}的进程 #{process.name} 失败", resp
        errCallback() if errCallback

    $scope.execDestroy = (process, succCallback, errCallback)->
      acc = new Processes process
      acc.$remove ->
        feedback.success "已删除 #{process.name} 进程"
        succCallback() if succCallback
      , (resp)->
        feedback.error "删除 #{process.name} 进程失败", resp
        errCallback() if errCallback

  ])

  .controller('ProcessListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'Feedback', 'SelectionService', \
                                  ($scope,   $location,   NgTable,         ActionService,   feedback,   SelectionService)->
    console.log("Initialized the Process list controller")
    Processes = $scope.services
    args =
      total: 0
      getData: ($defer, params)->
        $location.search params.url() # put params in url
        Processes.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.processes = datas
          $scope.cacheService.cache datas

    $scope.processesTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.selectionService = new SelectionService($scope.cacheService.records, "name")
    $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}

    $scope.start = (process)->
      $scope.execStart process, ->
        $scope.processesTable.reload()
    $scope.stop = (process)->
      $scope.execStop process, ->
        $scope.processesTable.reload()
    $scope.cancel = (process)->
      $scope.execCancel process ->
        $scope.processesTable.reload()
    $scope.destroy = (process)->
      $scope.execDestroy process, ->
        delete $scope.selectionService.items[process.name]
        $scope.processesTable.reload()

  ])

  .controller('ProcessNewCtrl', ['$scope', '$state', '$http', '$resource', '$stateParams', '$interval', 'Feedback', 'CommonService', \
                                 ($scope,   $state,   $http,   $resource,   $stateParams,   $interval,   feedback,   CommonService)->
    console.log("Initialized the Process New controller")
    process = {}
    $scope.process = process
    Processes = $resource("/admin/api/processes")

    commonService = new CommonService()
    $http.get("/admin/api/accounts/list_no_process").success (accounts)->
      $scope.accounts = accounts
      $("#account_sn").select2({data: {results: commonService.formatSelectDatas accounts, 'name', 'sn'}}).on("change", (e)-> autoNew e.val)
    $http.get("/admin/api/schemas").success (schemas)->
      $scope.schemas = schemas
      $("#process_schema").select2({data: {results: commonService.formatSelectDatas schemas, 'name', null}})
    $http.get("/admin/api/hosts/list_available/APP,COM").success (hosts)->
      $scope.hosts = hosts
      $("#process_host").select2({data: {results: commonService.formatSelectDatas hosts, 'name', null}})

    getHostById = (id)->
      return host for host in $scope.hosts when host.id is parseInt id

    autoNew = (sn)->
      if sn?
        $http.get("/admin/api/processes/auto_new/#{sn}").success (data)->
          process = data
          $scope.schemas.push process.schema
          $("#process_schema").select2({data: {results: commonService.formatSelectDatas $scope.schemas, 'name', null}})
          $("#process_host").val(JSON.stringify(getHostById process.host.id)).trigger("change")
          $("#process_schema").val(JSON.stringify(process.schema)).trigger("change")

    $http.get("/admin/api/accounts/#{$stateParams.accountSn}").success (account)-> $scope.account = account

    $scope.create = ->
      $scope.submited = true
      acc = new Processes process
      acc.$save (response)->
        feedback.success "正在创建 #{process.name} 进程"
        $state.go "processes.view", response
      , (resp)->
        feedback.error "创建 #{process.name} 进程失败", resp
        $scope.submited = false

  ])

  .controller('ProcessViewCtrl', ['$scope', '$interval', '$stateParams', '$filter', '$http', '$location', \
                                  ($scope,   $interval,   $stateParams,   $filter,   $http,   $location)->
      process = $scope.cacheService.find $stateParams.name, true
      $scope.process = process
      console.log "Initialized the Process View controller on: #{JSON.stringify process}"

      TYPES =
        DEPLOY: "deploy"
        START:  "start"
        STOP:   "stop"
      LOG_ANCHORS =
        DEPLOY: "creation_log"
        START:  "start_log"
        STOP:   "stop_log"
      STATUS =
        STARTING: "Starting"
        RUNNING:  "Running"
        STOPPING: "Stopping"
        STOPPED:  "Stopped"

      # active tab
      activeTab = (type)->
        addClass = (id)->
          $("#{id}_li").addClass('active')
          $(id).addClass('active in')
        removeClass = (id)->
          $("#{id}_li").removeClass('active')
          $(id).removeClass('active in')
        if type?
          removeClass("##{LOG_ANCHORS.DEPLOY}")
          removeClass("##{LOG_ANCHORS.START}")
          removeClass("##{LOG_ANCHORS.STOP}")
          switch type
            when TYPES.DEPLOY then addClass("##{LOG_ANCHORS.DEPLOY}")
            when TYPES.START then addClass("##{LOG_ANCHORS.START}")
            when TYPES.STOP then addClass("##{LOG_ANCHORS.STOP}")
          return
        return addClass("##{LOG_ANCHORS.START}") if process.status is STATUS.STARTING or process.status is STATUS.RUNNING
        return addClass("##{LOG_ANCHORS.STOP}") if process.status is STATUS.STOPPING or process.status is STATUS.STOPPED
        return addClass("##{LOG_ANCHORS.DEPLOY}")
      activeTab()

      # toggle access url button
      toggleAccessButton = ->
        return $("#accessButton").attr("disabled", "disabled") if $scope.process.status isnt "Running"
        return $("#accessButton").removeAttr("disabled") if $scope.process.status is "Running"
      toggleAccessButton()

      $scope.path = $location.path(); # tab 切换

      # display configration
      processConfig = process.configuration
      schemaConfig = process.schema.configuration if process.schema?
      process.configuration['debug.port'] +
      process.display =
        status: $filter("formatProcessStatus")(process.status)
        configuration: "http.port: {0}\njmx.port: {1}\nrmi.port: {2}\ndebug.port: {3}".interpolate(
                        processConfig['http.port'], processConfig['jmx.port'], processConfig['rmi.port'], processConfig['debug.port'])
        schema:
          configuration: "用户名：{0}\n密码：{1}\n".interpolate(schemaConfig['user'], schemaConfig['password']) if schemaConfig?

      # access url
      $scope.ipUrl = "#{window.location.protocol}//#{process.host.address}:#{processConfig['http.port']}"
      $scope.domainUrl = "#{window.location.protocol}//#{account.domain}#{window.location.host.replace(/^msc\./,'.')}"

      # toggle action buttons
      toggleButton = (status)->
        show = (element)->
          $(element).removeClass("hidden").addClass("show")
        hide = (element)->
          $(element).removeClass("show").addClass("hidden")
        hide $("#startBtn")
        hide $("#cancelStartingBtn")
        hide $("#stopBtn")
        hide $("#cancelStoppingBtn")
        switch status
          when "stopped"              then show $("#startBtn")
          when "starting"             then show $("#cancelStartingBtn")
          when "running", "abnormal"  then show $("#stopBtn")
          when "stopping"             then show $("#cancelStoppingBtn")

      currentUrl = $location.url(); # judge current url change

      process.creationLog = ""
      process.startLog = ""
      process.stopLog = ""
      # 获取日志信息
      $scope.getLog = (invokeId, type)->
          # 日志滚动条效果
        resolveScroll = (preScrollTop, element)->
          if preScrollTop is element.scrollTop() or element[0].scrollHeight <= element.scrollTop() + element.outerHeight()
            element.scrollTop(element[0].scrollHeight)
            return element.scrollTop()
          return preScrollTop
        process = $scope.process
        offset = 0
        preScrollTop = 0
        $logs = $("#process_" + type + "Log")
        intervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{offset}"
            $http.get(url).success (data, status, headers) ->
              switch type
                when "creation" then $scope.process.creationLog += data.logs
                when "start" then $scope.process.startLog += data.logs
                when "stop" then $scope.process.stopLog += data.logs
              preScrollTop = resolveScroll preScrollTop, $logs
              offset = parseInt(headers("offset"))
              toggleButton($filter("lowercase")(headers("status")))
              $scope.process.display.status = $filter("formatProcessStatus")(headers("status"))
              $scope.process.status = headers("status")
              toggleAccessButton()
              $interval.cancel(intervalId) if offset is -1 or currentUrl isnt $location.url()
        , 1000)

      # 触发获取日志事件
      toggleButton($filter("lowercase")(process.status))
      $scope.getLog(process.configuration.createInvocationId, "creation")
      $scope.getLog(process.configuration.startInvocationId, "start")
      $scope.getLog(process.configuration.stopInvocationId, "stop")

      $scope.start = ->
        $scope.execStart process, $scope.getLog
        activeTab(TYPES.START)
      $scope.stop = ->
        $scope.execStop process, $scope.getLog
        activeTab(TYPES.STOP)
      $scope.cancel = (type)->
        $scope.execCancel process, type

  ])