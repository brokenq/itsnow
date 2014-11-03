
angular.module('MscIndex.SLAs',
    ['ngTable',
     'ngResource',
     'ngSanitize',
     'dnt.action.service',
     'Lib.Utils',
     'Lib.Feedback'])
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


  .controller('SlasCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    console.log("Initialized the SLAs controller")
    $scope.options =
      page:  1,           # show first page
      count: 10           # count per page
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
      $scope.cache sla


    # 提供本地缓存功能, 本地缓存的数据可以多于ng table显示的当前页的数据(也就是ListCtrl的数据)
    #
    # TODO 将这个本地缓存抽取成为可以复用的类(CacheService)
    #   甚至可以考虑，是不是将selection service 也由这里负责
    #    好处是selection可以跨页
    #    坏处是selection跨页之后，用户行为的预期变化了(实际选中的数据与用户看到的不一样),需要额外的机制告知用户
    #    这个问题不应该丢给具体开发人员，应该在产品/平台层面统一
    #
    $scope.cache = (data) ->
      if Array.isArray(data)
        $scope.cacheMany(data)
      else
        $scope.cacheOne(data)
    $scope.slas = ->
      $scope.records = [] unless $scope.records
      $scope.records
    $scope.cacheOne = (sla) ->
      $scope.slas().push(sla)
    $scope.cacheMany = (slas) ->
      jQuery.merge($scope.slas(), slas)
      # TODO 针对数据更新，优化此地实现(identify object by id, not object)
      jQuery.unique($scope.slas())

    $scope.getLocalSlaById = (id)->
      return sla for sla in $scope.slas() when sla.id == parseInt(id)
    $scope.getRemoteSlaById = (id)->
      console.log "Hit missing sla with id: " + id
      alert "Not implemented to get sla from remote API"
      {id: id}  #return the simplest object now

    $scope.getSlaById = (id, fetch)->
      local = $scope.getLocalSlaById(id)
      return local if local
      if fetch
        remote = $scope.getRemoteSlaById(id)
        remote || throw new Exception("Can't find the sla local/remote with id = " + id)
      else
        throw new Exception("Can't find the sla local with id = " + id)

  ])
  .controller('SlaListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService',\
                              ($scope, $location, NgTable, ActionService, commonService) ->
    console.log("Initialized the SLA list controller")
    args =
      total: 0
      getData: ($defer, params)->
        data = $scope.mockSlas.slice((params.page() - 1) * params.count(), params.page() * params.count())
        $scope.cache data
        $defer.resolve(data);

    $scope.selection = { 'checked': false, items: {} }
    $scope.slasTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getSlaById})
    commonService.watchSelection($scope.selection, $scope.slas, "id")
  ])
  .controller('SlaViewCtrl', ['$scope', '$stateParams', ($scope, $stateParams) ->
    sla = $scope.getSlaById $stateParams.id, true
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
      feedback.success "Created a sla " + JSON.stringify(sla)
      $state.go('slas.view', {id: sla.id})
    $scope.sla = sla

  ])
  .controller('SlaEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback', \
                             ($scope, $state, $stateParams, feedback) ->
    sla = $scope.getSlaById $stateParams.id, true
    console.log("Initialized the SLA Edit controller on: " + JSON.stringify(sla))
    $scope.sla = sla
    $scope.update = ->
      feedback.success "Updated a sla " + JSON.stringify(sla)
      $state.go('slas.view', {id: sla.id})
  ])
