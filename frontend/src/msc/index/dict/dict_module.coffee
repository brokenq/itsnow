
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
      url: '/dict',
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
.filter "stateFilter", ->
  stateFilter = (input) ->
    if input is "1"
      "有效"
    else
      "无效"
  stateFilter
.controller('DictCtrl', () ->
)
.controller('DictListCtrl', ['$scope', '$location','ActionService', 'CommonService','DictService',"Feedback",\
                              ($scope, $location, ActionService, commonService,DictService,feedback) ->
    console.log("Initialized the Dict list controller")
    $scope.dicts = []
    $scope.selection = {checked: false, items: {}}
    $scope.dictTable = commonService.instanceTable(DictService, $scope.dicts)
    commonService.watchSelection($scope.selection, $scope.dicts, "sn")
    $scope.getDictBySn  = (sn)->
      return dict for dict in $scope.dicts when dict.sn is sn
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.getDictBySn}
    $scope.refresh = ->
      $scope.tableParams.reload()
    $scope.destroy= (dict)->
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

])
