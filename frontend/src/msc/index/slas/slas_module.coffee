
angular.module('MscIndex.SLAs', [])
  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'slas',
      url: '/slas',
      abstract: true,
      templateUrl: 'slas/index.tpl.jade',
      controller: 'SlasCtrl',
      data: {pageTitle: 'SLA管理', default: 'slas.list'}
    $stateProvider.state 'slas.list',
      url: '/list',
      templateUrl: 'slas/list.tpl.jade'
      controller: 'SlaListCtrl',
      data: {pageTitle: 'SLA列表'}
    $stateProvider.state 'slas.new',
      url: '/new',
      templateUrl: 'slas/new.tpl.jade'
      controller: 'SlaNewCtrl',
      data: {pageTitle: '新增SLA'}
    $stateProvider.state 'slas.view',
      url: '/{id}',
      templateUrl: 'slas/view.tpl.jade'
      controller: 'SlaViewCtrl',
      data: {pageTitle: '查看SLA'}
    $stateProvider.state 'slas.edit',
      url: '/{id}/edit',
      templateUrl: 'slas/edit.tpl.jade'
      controller: 'SlaEditCtrl',
      data: {pageTitle: '编辑SLA'}
    $urlRouterProvider.when '/slas', '/slas/list'


  .controller('SlasCtrl', ['$scope', '$state', 'Feedback', 'CacheService', \
                           ($scope,   $state,   feedback,   CacheService) ->
    # frontend controller logic
    console.log("Initialized the SLAs controller")
    $scope.options =
      page:  1,           # show first page
      count: 10           # count per page
    # Below are mock codes, should be replaced by
    $scope.mockSlas = [
        {id: 1,  title: 'SLA One',    description: 'The first SLA'},
        {id: 2,  title: 'SLA Two',    description: 'The second SLA'},
        {id: 3,  title: 'SLA Three',  description: 'The third SLA'},
        {id: 4,  title: 'SLA Four',   description: 'The 4th SLA'},
        {id: 5,  title: 'SLA Five',   description: 'The 5th SLA'},
        {id: 6,  title: 'SLA Six',    description: 'The 6th SLA'},
        {id: 7,  title: 'SLA Seven',  description: 'The 7th SLA'},
        {id: 8,  title: 'SLA Eight',  description: 'The 8th SLA'},
        {id: 9,  title: 'SLA Nine',   description: 'The 9th SLA'},
        {id: 10, title: 'SLA Ten',    description: 'The 10th SLA'},
        {id: 11, title: 'SLA Eleven', description: 'The 11th SLA'},
    ]

    $scope.mockCreate = (sla)->
      $scope.mockSlas.push sla
      $scope.cacheService.cache sla

    $scope.mockDestroy = (sla)->
      $scope.mockSlas.remove sla
      $scope.cacheService.expire sla

    # 之所以把destroy方法放在这里，因为理论上，view/edit页面也可以发起删除操作
    $scope.destroy = (sla)->
      $scope.mockDestroy sla
      feedback.success "Destroyed a SLA " + JSON.stringify(sla)
      $state.go('slas.list')

    $scope.cacheService = new CacheService("id")

  ])
  .controller('SlaListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService',\
                              ($scope,   $location,   NgTable,         ActionService,   commonService) ->
    console.log("Initialized the SLA list controller")
    args =
      total: 0
      getData: ($defer, params)->
        params.total  $scope.mockSlas.length
        data = $scope.mockSlas.slice((params.page() - 1) * params.count(), params.page() * params.count())
        $scope.cacheService.cache data
        $defer.resolve(data);

    $scope.selection = { 'checked': false, items: {} }
    $scope.slasTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
    commonService.watchSelection($scope.selection, $scope.cacheService.records, "id")
  ])
  .controller('SlaViewCtrl', ['$scope', '$stateParams', ($scope, $stateParams) ->
    sla = $scope.cacheService.find $stateParams.id, true
    $scope.sla = sla
    console.log("Initialized the SLA View controller on: " + JSON.stringify(sla))
  ])
  .controller('SlaNewCtrl', ['$scope', '$state', 'Feedback', \
                             ($scope, $state, feedback) ->
    sla = {} # the creating record
    console.log("Initialized the SLA New controller")
    $scope.create = ->
      maxId = $scope.mockSlas[$scope.mockSlas.length - 1].id
      sla.id = maxId + 1
      $scope.mockCreate sla
      feedback.success "Created a SLA " + JSON.stringify(sla)
      $state.go('slas.view', {id: sla.id})
    $scope.sla = sla

  ])
  .controller('SlaEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback', \
                             ($scope, $state, $stateParams, feedback) ->
    sla = $scope.cacheService.find $stateParams.id, true
    console.log("Initialized the SLA Edit controller on: " + JSON.stringify(sla))
    $scope.sla = sla
    $scope.update = ->
      feedback.success "Updated a SLA " + JSON.stringify(sla)
      $state.go('slas.view', {id: sla.id})
  ])
