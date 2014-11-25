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
      url: '/new',
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
        feedback.success "已停止 #{process.name} 进程"
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
      acc = new CancelAction process
      acc.$cancel {job: invokeId}, ->
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
          $defer.resolve $scope.processes = datas; $scope.cacheService.cache datas

    $scope.processesTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.selectionService = new SelectionService($scope.cacheService.records, "id")
    $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}

    $scope.start = (process)->
      $scope.execStart process, ->
        $scope.processesTable.reload()
    $scope.stop = (process)->
      $scope.execStop process, ->
        $scope.processesTable.reload()
    $scope.cancel = (process)->
      $scope.execCancel process, ->
        $scope.processesTable.reload()
    $scope.destroy = (process)->
      $scope.execDestroy process, ->
        $scope.processesTable.reload()

  ])

  .controller('ProcessNewCtrl', ['$scope', '$state', '$http', '$resource', 'Feedback', \
                                 ($scope,   $state,   $http,   $resource,   feedback)->
    console.log("Initialized the Process New controller")
    process = {}
    $scope.process = process
    Processes = $resource("/admin/api/processes")

    $http.get("/admin/api/accounts/list_no_process").success (accounts)-> $scope.accounts = accounts
    $http.get("/admin/api/schemas").success (schemas)-> $scope.schemas = schemas
    $http.get("/admin/api/hosts/list_available/APP,COM").success (hosts)-> $scope.hosts = hosts

    getHostById = (id)->
      return host for host in $scope.hosts when host.id is parseInt id
    $scope.$watch 'account.id', (account)->
      if account?
        $http.get("/admin/api/processes/auto_new/#{account.sn}").success (data)->
          process = data
          $scope.schemas.push process.schema
          $scope.process.schema = process.schema
          $scope.process.host = getHostById process.host.id

    $scope.create = ->
      acc = new Processes process
      acc.$save (response)->
        feedback.success "正在创建 #{process.name} 进程"
        $state.go "processes.view", response
      , (resp)->
        feedback.error "创建 #{process.name} 进程失败", resp
  ])

  .controller('ProcessViewCtrl', ['$scope', '$interval', '$stateParams', '$filter', '$http', '$location', \
                                  ($scope,   $interval,   $stateParams,   $filter,   $http,   $location)->
      process = $scope.cacheService.find $stateParams.name, true
      $scope.process = process
      console.log "Initialized the Process View controller on: #{JSON.stringify process}"

      process.display =
        status: $filter("formatProcessStatus")(process.status)
        configuration: JSON.stringify process.configuration
        schema:
          configuration: JSON.stringify process.schema.configuration if process.schema?
      process.creationLog = ""
      process.startLog = ""
      process.stopLog = ""

      $http.get("/admin/api/accounts/list_no_process").success (accounts)-> $scope.accounts = accounts
      $http.get("/admin/api/schemas").success (schemas)-> $scope.schemas = schemas
      $http.get("/admin/api/hosts/list_available/APP,COM").success (hosts)-> $scope.hosts = hosts

      toggleButton = (status)->
        $("#startBtn").removeClass("show").addClass("hidden")
        $("#cancelStartingBtn").removeClass("show").addClass("hidden")
        $("#stopBtn").removeClass("show").addClass("hidden")
        $("#cancelStoppingBtn").removeClass("show").addClass("hidden")
        switch status
          when "stopped" then  $("#startBtn").removeClass("hidden").addClass("show")
          when "starting" then $("#cancelStartingBtn").removeClass("hidden").addClass("show")
          when "running", "abnormal" then $("#stopBtn").removeClass("hidden").addClass("show")
          when "stopping" then $("#cancelStoppingBtn").removeClass("hidden").addClass("show")

      currentUrl = $location.url();
      $scope.path = $location.path()
      # 获取日志信息
      $scope.getLog = (invokeId, type)->
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
              $interval.cancel(intervalId) if offset is -1 or currentUrl isnt $location.url()
        , 1000)

      # 日志滚动条效果
      resolveScroll = (preScrollTop, element)->
        if preScrollTop is element.scrollTop() or element[0].scrollHeight <= element.scrollTop() + element.outerHeight()
          element.scrollTop(element[0].scrollHeight)
          return element.scrollTop()
        return preScrollTop

      # 触发获取日志事件
      toggleButton($filter("lowercase")(process.status))
      $scope.getLog(process.configuration.createInvocationId, "creation")
      $scope.getLog(process.configuration.startInvocationId, "start")
      $scope.getLog(process.configuration.stopInvocationId, "stop")

      $scope.start = ->
        $scope.execStart process, $scope.getLog
      $scope.stop = ->
        $scope.execStop process, $scope.getLog
      $scope.cancel = ->
        $scope.execCancel process

  ])