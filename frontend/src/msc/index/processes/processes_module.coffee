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
        succCallback(data["job"]) if succCallback
      , (resp)->
        feedback.error "启动 #{process.name} 进程失败", resp
        errCallback() if errCallback

    $scope.execStop = (process, succCallback, errCallback)->
      acc = new Process process
      acc.$stop (data)->
        feedback.success "已停止 #{process.name} 进程"
        succCallback(data["job"]) if succCallback
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

  .controller('ProcessListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'Feedback', 'CommonService', \
                                  ($scope,   $location,   NgTable,         ActionService,   feedback,   commonService)->
    console.log("Initialized the Process list controller")
    Processes = $scope.services
    args =
      total: 0
      getData: ($defer, params)->
        $location.search params.url() # put params in url
        Processes.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.processes = datas; $scope.cacheService.cache datas

    $scope.selection = {checked: false, items: {}}
    $scope.processesTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    commonService.watchSelection($scope.selection, $scope.cacheService.records, "name")
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}

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
    $scope.hosts = []
    $http.get("/admin/api/hosts/list/type/APP").success (hosts)->
      $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0
    $http.get("/admin/api/hosts/list/type/COM").success (hosts)->
      $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0

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

  .controller('ProcessViewCtrl', ['$scope', '$interval', '$stateParams', '$filter', '$http', \
                                  ($scope,   $interval,   $stateParams,   $filter,   $http)->
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
      $scope.hosts = []
      $http.get("/admin/api/hosts/list/type/APP").success (hosts)->
        $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0
      $http.get("/admin/api/hosts/list/type/COM").success (hosts)->
        $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0

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

      # 获取日志信息
      $scope.getCreateLog = (invokeId)->
        process = $scope.process
        createOffset = 0
        createIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{createOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.creationLog += log
              createOffset = parseInt(headers("offset"))
              toggleButton($filter("lowercase")(headers("status")))
              $interval.cancel(createIntervalId) if createOffset is -1
        , 1000)

      $scope.getStartLog = (invokeId)->
        process = $scope.process
        startOffset = 0
        startIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{startOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.startLog += log
              startOffset = parseInt(headers("offset"))
              toggleButton($filter("lowercase")(headers("status")))
              $interval.cancel(startIntervalId) if startOffset is -1
        , 1000)

      $scope.getStopLog = (invokeId)->
        process = $scope.process
        stopOffset = 0
        stopIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{stopOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.stopLog += log
              stopOffset = parseInt(headers("offset"))
              toggleButton($filter("lowercase")(headers("status")))
              $interval.cancel(stopIntervalId) if stopOffset is -1
        , 1000)

      # 触发获取日志事件
      toggleButton($filter("lowercase")(process.status))
      $scope.getCreateLog(process.configuration.createInvocationId)
      $scope.getStartLog(process.configuration.startInvocationId)
      $scope.getStopLog(process.configuration.stopInvocationId)

      $scope.start = ->
        $scope.execStart process, $scope.getStartLog
      $scope.stop = ->
        $scope.execStop process, $scope.getStopLog
      $scope.cancel = ->
        $scope.execCancel process

  ])