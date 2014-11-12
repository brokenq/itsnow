angular.module('System.Group', [])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'groups',
    url: '/groups',
    abstract: true,
    templateUrl: 'system/groups/index.tpl.jade',
    controller: 'GroupsCtrl',
    data: {pageTitle: '组管理', default: 'groups.list'}
  $stateProvider.state 'groups.list',
    url: '/list',
    templateUrl: 'system/groups/list.tpl.jade'
    controller: 'GroupsListCtrl',
    data: {pageTitle: '组列表'}
  $stateProvider.state 'groups.new',
    url: '/new',
    templateUrl: 'system/groups/new.tpl.jade'
    controller: 'GroupsNewCtrl',
    data: {pageTitle: '新增组'}
  $stateProvider.state 'groups.view',
    url: '/{name}',
    templateUrl: 'system/groups/view.tpl.jade'
    controller: 'GroupsViewCtrl',
    data: {pageTitle: '查看组'}
  $stateProvider.state 'groups.edit',
    url: '/{name}/edit',
    templateUrl: 'system/groups/edit.tpl.jade'
    controller: 'GroupsEditCtrl',
    data: {pageTitle: '编辑组'}
  $urlRouterProvider.when '/groups', '/groups/list'
.factory('GroupService', ['$resource', ($resource) ->
    $resource("/api/groups/:name", {},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      get: { method: 'GET', params: {name: '@name'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {name: '@name'}},
      remove: { method: 'DELETE', params: {name: '@name'}}
    )
  ])
.controller('GroupsCtrl', ['$scope', '$resource', '$state', 'Feedback', 'CacheService',
    ($scope,  $resource,  $state,    feedback,   CacheService) ->
      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "name"
      $scope.services = $resource("/api/groups/:name", {name:'@name'},
        save: { method: 'POST',params:{name:''}},
        update: { method: 'PUT', params: {name: 'name'}}
      )
      $scope.Destroy = (group,succCallback, errCallback) ->
        $scope.services.remove {name: group.name}, () ->
          feedback.success "删除#{group.name}成功"
          succCallback() if succCallback
        , (resp) ->
          errCallback() if errCallback
          feedback.error("删除#{group.name}失败", resp)
  ])
.controller('GroupsListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService', 'Feedback',
    ($scope, $location, NgTable, ActionService, commonService, feedback) ->
      args =
        total: 0
        getData: ($defer, params)->
          $location.search params.url() # put params in url
          $scope.services.query params.url(), (datas, headers) ->
            params.total headers 'total'
            $defer.resolve $scope.groups = datas;
            $scope.cacheService.cache datas
      $scope.selection = {checked: false, items: {}}
      $scope.groupsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "name")
      $scope.reload = ->
        $scope.groupsTable.reload()
      $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}
      $scope.destroy = (group)->
        $scope.Destroy group, ->
          delete $scope.selection.items[group.name]
          $scope.reload()
  ])
.controller('GroupsNewCtrl', ['$scope', '$state', 'Feedback',
    ($scope, $state, feedback) ->
      $scope.create = () ->
        $scope.services.save $scope.group, ->
          $state.go "groups.list"
          return
  ])
.controller('GroupsEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',
    ($scope,   $state,    $stateParams,   feedback) ->
      name = $stateParams.name
      $scope.services.get
        name: name
      , (data) ->
        $scope.group = data
      $scope.update = () ->
        $scope.group.$promise = `undefined`
        $scope.group.$resolved = `undefined`
        $scope.services.update {name: name}, $scope.group, () ->
          feedback.success "修改#{$scope.group.name}成功"
          $state.go "groups.list"
        , (resp) ->
          feedback.error("修改#{$scope.group.name}失败", resp);
  ])