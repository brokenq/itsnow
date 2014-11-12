angular.module('System.User',[])
.config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'users',
      url: '/users',
      abstract: true,
      templateUrl: 'system/users/index.tpl.jade',
      controller: 'UserCtrl',
      data: {pageTitle: '用户管理', default: 'users.list'}
    $stateProvider.state 'users.list',
      url: '/list',
      templateUrl: 'system/users/list.tpl.jade'
      controller: 'UserListCtrl',
      data: {pageTitle: '用户列表'}
    $stateProvider.state 'users.new',
      url: '/new',
      templateUrl: 'system/users/new.tpl.jade'
      controller: 'UserNewCtrl',
      data: {pageTitle: '新增用户'}
    $stateProvider.state 'users.view',
      url: '/{username}',
      templateUrl: 'system/users/view.tpl.jade'
      controller: 'UserViewCtrl',
      data: {pageTitle: '查看用户'}
    $stateProvider.state 'users.edit',
      url: '/{username}/edit',
      templateUrl: 'system/users/edit.tpl.jade'
      controller: 'UserEditCtrl',
      data: {pageTitle: '编辑用户'}
    $urlRouterProvider.when '/users', '/users/list'
.filter('formatUser', ->
  (user) ->
    return user.name if user.name == user.username
    user.name + "(" + user.username + ")"
)
.controller('UserCtrl', ['$scope', '$resource','$state', 'Feedback', 'CacheService',\
                        ($scope,    $resource, $state,     feedback, CacheService) ->
      $scope.statedatas=[{name:"启用",state:true},{name:"停用",state:false}]
      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "username"
      $scope.services = $resource("/admin/api/users/:username", {},
        get: { method: 'GET', params: {username: '@username'}},
        save: { method: 'POST'},
        update: { method: 'PUT', params: {username: '@username'}},
        query: { method: 'GET', isArray: true},
        remove: { method: 'DELETE', params: {username: '@username'}}
      )
  ])
.controller('UserListCtrl',['$scope', '$location', 'ngTableParams', 'ActionService','CommonService','Feedback',\
                           ($scope, $location,  NgTable,         ActionService,   commonService,feedback) ->
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
    commonService.watchSelection($scope.selection, $scope.cacheService.records, "username")
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}
    $scope.destroy = (user) ->
      $scope.services.remove {username: user.username}, () ->
        feedback.success "删除用户#{user.username}成功"
        delete $scope.selection.items[user.username]
        $scope.usersTable.reload()
      , (resp) ->
        feedback.error("删除用户#{user.username}失败", resp)
    $scope.reload = ->
      $scope.usersTable.reload()

])
.controller('UserNewCtrl', ['$scope', '$state', 'Feedback',\
                           ($scope,     $state,   feedback) ->
      $scope.createview=true
      $scope.updateview=false
      $scope.ngdis=false
      $scope.create=() ->
        $scope.cuser.$promise = `undefined`
        $scope.cuser.$resolved = `undefined`
        $scope.services.save $scope.cuser, ->
          $state.go "users.list"
          return
  ])
.controller('UserEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',
    ($scope,    $state,   $stateParams,  feedback) ->
      $scope.createview=false
      $scope.updateview=true
      $scope.ngdis=true
      username=$stateParams.username
      $scope.services.get
        username: username
      , (data) ->
        $scope.cuser = data
        if data.enabled
          $scope.selectState = $scope.statedatas[0]
        else
          $scope.selectState = $scope.statedatas[1]
        return
      $scope.update=() ->
        $scope.cuser.enabled= $scope.selectState.state;
        $scope.cuser.$promise = `undefined`
        $scope.cuser.$resolved = `undefined`
        $scope.services.update {username: username}, $scope.cuser, () ->
          feedback.success "修改#{$scope.cuser.username}成功"
          $state.go "users.list"
        , (resp) ->
          feedback.error("修改#{$scope.cuser.username}失败", resp);
  ])