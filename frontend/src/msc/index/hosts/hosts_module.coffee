angular.module('MscIndex.Hosts', [])

  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'hosts',
      url: '/hosts',
      abstract: true,
      templateUrl: 'hosts/index.tpl.jade',
      controller: 'HostsCtrl',
      data: {pageTitle: '主机管理', default: 'hosts.list'}
    $stateProvider.state 'hosts.list',
      url: '/list',
      templateUrl: 'hosts/list.tpl.jade'
      controller: 'HostListCtrl',
      data: {pageTitle: '主机列表'}
    $stateProvider.state 'hosts.new',
      url: '/new',
      templateUrl: 'hosts/new.tpl.jade'
      controller: 'HostNewCtrl',
      data: {pageTitle: '新增主机'}
    $stateProvider.state 'hosts.view',
      url: '/{id}',
      templateUrl: 'hosts/view.tpl.jade'
      controller: 'HostViewCtrl',
      data: {pageTitle: '查看主机'}
    $stateProvider.state 'hosts.edit',
      url: '/{id}/edit',
      templateUrl: 'hosts/edit.tpl.jade'
      controller: 'HostEditCtrl',
      data: {pageTitle: '编辑主机'}
    $urlRouterProvider.when '/hosts', '/hosts/list'

  .controller('HostsCtrl', ['$scope', '$resource', 'Feedback', 'CacheService', \
                            ($scope,   $resource,   feedback,   CacheService) ->
    console.log("Initialized the Hosts controller")
    $scope.options = {page: 1, count: 10}
    $scope.types = [
      {key: "DB",   value: "数据库主机"},
      {key: "APP",  value: "应用主机"},
      {key: "COM",  value: "综合主机"}
    ]

    $scope.services = $resource("/admin/api/hosts/:id", {id: "@id"}, {update: {method: "PUT"}})
    Hosts = $scope.services
    $scope.cacheService = new CacheService "id", (value)->
      data = {}
      $.ajax
        url:    "/admin/api/hosts/#{value}"
        async:  false
        type:   "GET"
        success: (response)->
          data = response
      return data

    $scope.execDestroy = (host, succCallback, errCallback)->
      acc = new Hosts host
      acc.$remove ->
        feedback.success "已删除 #{host.name} 主机"
        succCallback() if succCallback
      , (resp)->
        feedback.error "删除 #{process.name} 主机失败", resp
        errCallback() if errCallback
  ])

  .controller('HostListCtrl',['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService', 'SelectionService', \
                              ($scope,   $location,   NgTable,         ActionService,   commonService ,  SelectionService  )->
    console.log("Initialized the Host list controller")
    Hosts = $scope.services
    args =
      total: 0
      getData: ($defer, params)->
        $location.search params.url() # put params in url
        Hosts.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.hosts = datas; $scope.cacheService.cache datas

    $scope.hostsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
    $scope.selectionService = new SelectionService($scope.cacheService.records, "id")
    $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}

    $scope.destroy = (host)->
      $scope.execDestroy host, ->
        $scope.hostsTable.reload()

  ])

  .controller('HostNewCtrl', ['$scope', '$state', 'Feedback', \
                              ($scope,   $state,   feedback)->
    console.log("Initialized the Host New controller")
    host =
      type: "COM"
      configuration:
        user: "root"
        msu_version: window.system.version
        msp_version: window.system.version

    $scope.host = host
    Hosts = $scope.services

    $scope.create = ->
      host.configuration['msu.version'] = host.configuration.msu_version
      host.configuration['msp.version'] = host.configuration.msp_version
      delete host.configuration.msu_version
      delete host.configuration.msp_version
      acc = new Hosts host
      acc.$save (resp)->
        feedback.success "正在创建 #{host.name} 主机"
        $scope.cacheService.cache resp
        $state.go "hosts.view", resp
      , (resp)->
        feedback.error "创建 #{host.name} 主机失败", resp
  ])

  .controller('HostViewCtrl', ['$scope', '$stateParams', '$http', '$interval', '$state', \
                               ($scope,   $stateParams,   $http,   $interval,   $state)->
    host = $scope.cacheService.find $stateParams.id, true
    host.configuration.msp_version = host.configuration['msp.version'] if host.configuration['msp.version']?
    host.configuration.msu_version = host.configuration['msu.version'] if host.configuration['msu.version']?
    host.creationLog = ""
    $scope.host = host
    console.log "Initialized the Host View controller on: #{JSON.stringify host}"

    # 获取日志信息
    getCreateLog = (invokeId)->
      createOffset = 0
      preScrollTop = 0
      $creationLog = $("#host_creationLog")
      createIntervalId = $interval(->
        if invokeId?
          url = "/admin/api/hosts/#{host.id}/follow?invocationId=#{invokeId}&offset=#{createOffset}"
          $http.get(url).success (data, status, headers) ->
            host.creationLog += data.logs
            if preScrollTop is $creationLog.scrollTop() or $creationLog[0].scrollHeight <= $creationLog.scrollTop() + $creationLog.outerHeight()
              $creationLog.scrollTop($creationLog[0].scrollHeight)
              preScrollTop = $creationLog.scrollTop()
            createOffset = parseInt headers "offset"
            $interval.cancel(createIntervalId) if createOffset is -1
      , 1000)

    getCreateLog(invokeId) if invokeId = host.configuration.createInvocationId

    $scope.toView = ->
      $state.go 'hosts.list'

  ])

  .controller('HostEditCtrl', ['$scope',  '$state',  '$stateParams', 'Feedback', \
                               ($scope,    $state,    $stateParams,   feedback)->
    host = $scope.cacheService.find $stateParams.id, true
    console.log "Initialized the Host Edit controller on: #{JSON.stringify host}"
    host.configuration.msu_version = host.configuration["msu.version"]
    host.configuration.msp_version = host.configuration["msp.version"]
    $scope.host = host
    Hosts = $scope.services

    $scope.update = ->
      host.configuration['msu.version'] = host.configuration.msu_version
      host.configuration['msp.version'] = host.configuration.msp_version
      delete host["$promise"]
      delete host["$resolved"]
      delete host.configuration.msu_version
      delete host.configuration.msp_version
      acc = new Hosts host
      acc.$update ->
        feedback.success "已更新 #{host.name} 主机"
        $state.go 'hosts.view', host
      , (resp)->
        feedback.error "更新 #{host.name} 主机失败", resp

  ])