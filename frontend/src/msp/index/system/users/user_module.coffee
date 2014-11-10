
angular.module('Service.User',
    ['ngTable',
     'ngResource',
     'ngSanitize',
     'dnt.action.service',
     'Lib.Commons',
     'Lib.Utils',
     'Lib.Feedback'])
.config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'users',
      url: '/users',
      abstract: true,
      templateUrl: 'service/users/index.tpl.jade',
      controller: 'UserCtrl',
      data: {pageTitle: '字典管理', default: 'users.list'}
    $stateProvider.state 'users.list',
      url: '/list',
      templateUrl: 'service/users/list.tpl.jade'
      controller: 'UserListCtrl',
      data: {pageTitle: '字典列表'}
    $stateProvider.state 'users.new',
      url: '/new',
      templateUrl: 'service/users/new.tpl.jade'
      controller: 'UserNewCtrl',
      data: {pageTitle: '新增字典'}
    $stateProvider.state 'users.view',
      url: '/{sn}',
      templateUrl: 'service/users/view.tpl.jade'
      controller: 'UserViewCtrl',
      data: {pageTitle: '查看字典'}
    $stateProvider.state 'users.edit',
      url: '/{sn}/edit',
      templateUrl: 'service/users/edit.tpl.jade'
      controller: 'UserEditCtrl',
      data: {pageTitle: '编辑字典'}
    $urlRouterProvider.when '/users', '/users/list'
.filter "stateFilter", ->
  stateFilter = (input) ->
    if input is "1"
      "有效"
    else
      "无效"
  stateFilter
.controller('UserCtrl', ['$scope', '$resource','$state', 'Feedback', 'CacheService',\
                        ($scope,    $resource, $state,     feedback, CacheService) ->
      $scope.statedatas=[{name:"无效",value:0},{name:"有效",value:1}]
      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "sn"
      $scope.services = $resource("/api/userionaries/:sn", {},
        get: { method: 'GET', params: {sn: '@sn'}},
        save: { method: 'POST'},
        update: { method: 'PUT', params: {sn: '@sn'}},
        query: { method: 'GET', isArray: true},
        remove: { method: 'DELETE', params: {sn: '@sn'}}
      )
  ])
.controller('UserListCtrl',['$scope', '$location', 'ngTableParams', 'ActionService','CommonService','Feedback',\
                               ($scope,     $location,  NgTable,         ActionService,   commonService,feedback) ->
    Users= $scope.services
    args =
      total: 0
      getData: ($defer, params)->
        $location.search params.url() # put params in url
        Users.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.users = datas; $scope.cacheService.cache datas
    $scope.selection = {checked: false, items: {}}
    $scope.usersTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    commonService.watchSelection($scope.selection, $scope.cacheService.records, "sn")
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}
    $scope.destroy = (user) ->
      $scope.services.remove {sn: user.sn}, () ->
        feedback.success "删除时间#{user.sn}成功"
        $scope.worktimesTable.reload()
      , (resp) ->
        feedback.error("删除时间#{user.sn}失败", resp)
    $scope.reload = ->
      $scope.usersTable.reload()

])
.controller('UserNewCtrl', ['$scope', '$state', 'Feedback',
    ($scope,     $state,   feedback) ->
      $scope.create=() ->
        $scope.user.state = $scope.selectState.value;
        $scope.worktime.$promise = `undefined`
        $scope.worktime.$resolved = `undefined`
        $scope.services.save $scope.user, ->
          $state.go "users.list"
          return
  ])
.controller('UserEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',
    ($scope,    $state,   $stateParams,  feedback) ->
      sn=$stateParams.sn
      $scope.services.get
        sn: sn
      , (data) ->
        $scope.user = data
        if data.state is "1"
          $scope.selectState = $scope.statedatas[1]
        else
          $scope.selectState = $scope.statedatas[0]
        return
      $scope.update=() ->
        $scope.user.state = $scope.selectState.value;
        $scope.user.$promise = `undefined`
        $scope.user.$resolved = `undefined`
        $scope.services.update {sn: sn}, $scope.user, () ->
          feedback.success "修改#{$scope.user.sn}成功"
          $state.go "users.list"
        , (resp) ->
          feedback.error("修改#{$scope.user.sn}失败", resp);



  ])