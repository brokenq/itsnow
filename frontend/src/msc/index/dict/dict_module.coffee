
angular.module('MscIndex.Dict',
    ['ngTable',
     'ngResource',
     'ngSanitize',
     'dnt.action.service',
     'Lib.Commons',
     'Lib.Utils',
     'Lib.Feedback'])
  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'dict',
      url: '/dicts',
      abstract: true,
      templateUrl: 'dict/index.tpl.jade',
      controller: 'DictCtrl',
      data: {pageTitle: '字典管理', default: 'dict.list'}
    $stateProvider.state 'dict.list',
      url: '/list',
      templateUrl: 'dict/list.tpl.jade'
      controller: 'DictListCtrl',
      data: {pageTitle: '字典列表'}
    $stateProvider.state 'dict.new',
      url: '/new',
      templateUrl: 'dict/new.tpl.jade'
      controller: 'dictNewCtrl',
      data: {pageTitle: '新增字典'}
    $stateProvider.state 'dict.view',
      url: '/{sn}',
      templateUrl: 'dict/view.tpl.jade'
      controller: 'DictViewCtrl',
      data: {pageTitle: '查看SLA'}
    $stateProvider.state 'dict.edit',
      url: '/{id}/edit',
      templateUrl: 'dict/edit.tpl.jade'
      controller: 'DictEditCtrl',
      data: {pageTitle: '编辑字典'}
    $urlRouterProvider.when '/dict', '/dict/list'

.factory('DictService', ['$resource', ($resource) ->
    $resource(" /api/dictionaries/:sn", {sn: "@sn"})
  ])

  .controller('DictCtrl', ['$scope', '$state', 'Feedback', 'CacheService', ($scope, $state, feedback, CacheService) ->
    # frontend controller logic
    console.log("Initialized the Dict controller")


  ])
  .controller('DictListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService','DictService',\
                              ($scope, $location, NgTable, ActionService, commonService,DictService) ->
    console.log("Initialized the Dict list controller")
    Dict = $resource("/api/dictionaries/:sn", {sn: "@sn"})
    $scope.dicts = []
    $scope.selection = {checked: false, items: {}}
    $scope.tableParams = commonService.instanceTable(Dict, $scope.dicts)
    commonService.watchSelection($scope.selection, $scope.dicts, "id")
    $scope.getDictBySn  = (sn)->
      return dict for dict in $scope.dicts when dict.sn is parseInt sn
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.getDictBySn}
    $scope.refresh = ->
      $scope.tableParams.reload()
    $scope.delete = (dict)->
      dict = new Dict(dict)
      dict.$remove ->
      $scope.tableParams.reload()
  ])
  .controller('DictViewCtrl', ['$scope', '$stateParams', \
                             ($scope, $stateParams) ->
  ])
  .controller('DictNewCtrl', ['$scope', '$state', 'Feedback', \
                             ($scope, $state, feedback) ->

  ])
  .controller('DictEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback', \
                             ($scope, $state, $stateParams, feedback) ->
#    sla = $scope.cacheService.find $stateParams.id, true
#    console.log("Initialized the SLA Edit controller on: " + JSON.stringify(sla))
#    $scope.sla = sla
#    $scope.update = ->
#      feedback.success "Updated a SLA " + JSON.stringify(sla)
#      $state.go('slas.view', {id: sla.id})
  ])
