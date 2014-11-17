angular.module('MscIndex.Schemas', [])

  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'schemas',
      url: '/schemas',
      abstract: true,
      templateUrl: 'schemas/index.tpl.jade',
      controller: 'SchemasCtrl',
      data: {pageTitle: '数据库管理', default: 'schemas.list'}
    $stateProvider.state 'schemas.list',
      url: '/list',
      templateUrl: 'schemas/list.tpl.jade'
      controller: 'SchemaListCtrl',
      data: {pageTitle: '数据库列表'}
    $stateProvider.state 'schemas.new',
      url: '/new',
      templateUrl: 'schemas/new.tpl.jade'
      controller: 'SchemaNewCtrl',
      data: {pageTitle: '新增数据库'}
    $stateProvider.state 'schemas.view',
      url: '/{id}',
      templateUrl: 'schemas/view.tpl.jade'
      controller: 'SchemaViewCtrl',
      data: {pageTitle: '查看数据库'}
    $stateProvider.state 'schemas.edit',
      url: '/{id}/edit',
      templateUrl: 'schemas/edit.tpl.jade'
      controller: 'SchemaEditCtrl',
      data: {pageTitle: '编辑数据库'}
    $urlRouterProvider.when '/schemas', '/schemas/list'

  .controller('SchemasCtrl', ['$scope', '$resource', 'Feedback', 'CacheService', \
                           ($scope,   $resource,   feedback,   CacheService) ->
    console.log("Initialized the Schemas controller")
    $scope.options = {page: 1, count: 10}
    $scope.services = $resource("/admin/api/schemas/:id", {id: "@id"}, {update: {method: 'PUT'}})
    $scope.cacheService = new CacheService "id", (value)->
      data = {}
      $.ajax
        url:    "/admin/api/schemas/#{value}"
        async:  false
        type:   "GET"
        success: (response)->
          data = response
      return data

    Schemas = $scope.services
    $scope.execDestroy = (schema, succCallback, errCallback)->
      acc = new Schemas schema
      acc.$remove ->
        feedback.success "已删除 #{schema.name} 数据库"
        succCallback() if succCallback
      , (resp)->
        feedback.error "删除 #{schema.name} 数据库失败", resp
        errCallback() if errCallback
  ])

  .controller('SchemaListCtrl',['$scope', '$location', 'ngTableParams', 'ActionService', 'Feedback', 'SelectionService', \
                                ($scope,   $location,   NgTable,         ActionService,   feedback,   SelectionService)->
    console.log("Initialized the Schema list controller")
    Schemas = $scope.services
    args =
      total: 0
      getData: ($defer, params)->
        $location.search params.url() # put params in url
        Schemas.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.schemas = datas; $scope.cacheService.cache datas

    $scope.schemasTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.selectionService = new SelectionService($scope.cacheService.records, "id")
    $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}

    $scope.destroy = (host)->
      $scope.execDestroy host, ->
        $scope.schemasTable.reload()
  ])

  .controller('SchemaNewCtrl', ['$scope', '$state', '$http', 'Feedback', \
                                ($scope,   $state,   $http,   feedback)->
    console.log("Initialized the Schema New controller")
    schema = {}
    $scope.hosts = []
    $http.get('/admin/api/hosts/list/type/DB').success (hosts)-> $scope.hosts = $scope.hosts.concat hosts
    $http.get('/admin/api/hosts/list/type/COM').success (hosts)-> $scope.hosts = $scope.hosts.concat hosts
    $scope.schema = schema

    Schemas = $scope.services
    $scope.create = ->
      acc = new Schemas schema
      acc.$save (data)->
        feedback.success "已创建 #{schema.name} 数据库"
        $state.go "schemas.view", data
      , (resp)->
        feedback.error "创建 #{schema.name} 数据库失败", resp

  ])

  .controller('SchemaViewCtrl', ['$scope', '$stateParams', '$filter', \
                                 ($scope,   $stateParams, $filter)->
    schema = $scope.cacheService.find $stateParams.id, true
    $scope.schema = schema
    console.log "Initialized the Schema View controller on: #{JSON.stringify schema}"
    schema.display =
      process:
        status: $filter("formatProcessStatus")(schema.process.status) if schema.process?
      host:
        status: $filter("formatHostStatus")(schema.host.status) if schema.host?
        processesCount: $filter("round")(schema.host.processesCount/schema.host.capacity*100) if schema.host?
        schemasCount: $filter("round")(schema.host.schemasCount/schema.host.capacity*100) if schema.host?

  ])

  .controller('SchemaEditCtrl', ['$scope',  '$state',  '$stateParams', '$http', 'Feedback', \
                                 ($scope,    $state,    $stateParams,   $http,   feedback)->
    schema = $scope.cacheService.find $stateParams.id, true
    $scope.schema = schema
    console.log "Initialized the Schema Edit controller on: #{JSON.stringify schema}"
    $scope.hosts = []
    $http.get('/admin/api/hosts/list/type/DB').success (hosts)->
      $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0
    $http.get('/admin/api/hosts/list/type/COM').success (hosts)->
      $scope.hosts = $scope.hosts.concat hosts if hosts? and hosts.length > 0
    Schemas = $scope.services

    $scope.update = ->
      delete schema["$promise"]
      delete schema["$resolved"]
      acc = new Schemas schema
      acc.$update ->
        feedback.success "已更新 #{schema.name} 主机"
        $state.go 'schemas.view', schema
      , (resp)->
        feedback.error "更新 #{schema.name} 主机失败", resp

  ])