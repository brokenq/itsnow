angular.module('System.Roles',
  ['ngTable',
   'ngResource',
   'ngSanitize',
   'dnt.action.service',
   'Lib.Commons',
   'Lib.Utils',
   'Lib.Feedback',
   'multi-select'])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'roles',
    url: '/roles',
    abstract: true,
    templateUrl: 'system/roles/index.tpl.jade',
    controller: 'RolesCtrl',
    data: {pageTitle: '角色管理', default: 'roles.list'}
  $stateProvider.state 'roles.list',
    url: '/list',
    templateUrl: 'system/roles/list.tpl.jade'
    controller: 'RoleListCtrl',
    data: {pageTitle: '角色列表'}
  $stateProvider.state 'roles.new',
    url: '/new',
    templateUrl: 'system/roles/new.tpl.jade'
    controller: 'RoleNewCtrl',
    data: {pageTitle: '新增角色'}
  $stateProvider.state 'roles.view',
    url: '/{name}',
    templateUrl: 'system/roles/view.tpl.jade'
    controller: 'RoleViewCtrl',
    data: {pageTitle: '查看角色'}
  $stateProvider.state 'roles.edit',
    url: '/{name}/edit',
    templateUrl: 'system/roles/edit.tpl.jade'
    controller: 'RoleEditCtrl',
    data: {pageTitle: '编辑角色'}
  $urlRouterProvider.when '/roles', '/roles/list'

.factory('RoleService', ['$resource', ($resource) ->
    $resource("/api/roles/:name", {},
      get: { method: 'GET', params: {name: '@name'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {name: '@name'}},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      remove: { method: 'DELETE', params: {name: '@name'}},
      getUsers: { method: 'GET', params: {name: 'users'}, isArray: true}
    )
  ])

# 过滤拼接地点后的最后一个逗号
.filter('colFilter', () ->
  return (input) ->
    if input?
      names = []
      for user in input
        names.push user.name
      names.join()
    else '无'
)

.controller('RolesCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService',
    ($scope, $state, $log, feedback, CacheService) ->
      # frontend controller logic
      $log.log "Initialized the Roles controller"
      $scope.options =
        page: 1, # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService("name")

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false

      # 表单cancel按钮
      $scope.cancel = () ->
        $state.go "roles.list"

      # 获取下拉复选框选中的用户
      selectedUsersFun = (users) ->
        selectedUsers = []
        for user in users
          if user.ticked is true
            myUser = {};
            myUser.name = user.name
            selectedUsers.push myUser
        return selectedUsers

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatRoleFun = (role, users) ->
        aRole = role
        aRole.users = selectedUsersFun(users)
        delete aRole.$promise;
        delete aRole.$resolved;
        return aRole
  ])

.controller('RoleListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'CommonService', 'RoleService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, commonService, roleService, feedback) ->
      $log.log "Initialized the Role list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          roleService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve $scope.roles = data

      $scope.selection = { checked: false, items: {} }
      $scope.rolesTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "name")

      $scope.destroy = (role) ->
        roleService.remove {name: role.name}, () ->
          feedback.success "删除角色#{role.name}成功"
          delete $scope.selection.items[role.name]
          $scope.rolesTable.reload()
        , (resp) ->
          feedback.error("删除角色#{role.name}失败", resp)
  ])

.controller('RoleViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.role = $scope.cacheService.find $stateParams.name, true
    $log.log "Initialized the SLA View controller on: " + JSON.stringify($scope.role)
  ])

.controller('RoleNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'RoleService',
    ($scope, $state, $log, feedback, roleService) ->
      $log.log "Initialized the SLA New controller"

      create = () ->
        $scope.submited = true
        role = $scope.formatRoleFun($scope.role, $scope.users)
        roleService.save role, () ->
          feedback.success "新建角色#{role.name}成功"
          $state.go "roles.list"
        , (resp) ->
          feedback.error("新建角色#{role.name}失败", resp)

      roleService.getUsers (data) ->
        $scope.users = data

      $scope.create = create
  ])

.controller('RoleEditCtrl', ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'RoleService',
    ($scope, $state, $log, $stateParams, feedback, roleService) ->

      $scope.role = $scope.cacheService.find $stateParams.name, true
      $log.log "Initialized the SLA Edit controller on: " + JSON.stringify($scope.role)

      # 编辑页面提交
      update = () ->
        $scope.submited = true
        role = $scope.formatRoleFun($scope.role, $scope.users)
        roleService.update {name: role.name}, role, () ->
          feedback.success "修改角色#{role.name}成功"
          $state.go "roles.list"
        , (resp) ->
          feedback.error("修改角色#{role.name}失败", resp);

      roleService.get {name: $scope.role.name}, (data) ->
        $scope.role = data;
        roleService.getUsers (data) ->
          $scope.users = data
          for user in $scope.users
            for selectedUser in $scope.role.users
              if user.name == selectedUser.name
                user.ticked = true

      $scope.update = update
  ])