angular.module('MscIndex.User', ['ngTable','ngResource', 'MscIndex.User.Detail'])
  .config ($stateProvider)->
    $stateProvider.state 'system',
      url: '/system',
      templateUrl: 'user/list.tpl.jade'
      data: {pageTitle: '系统管理'}
    $stateProvider.state 'system.user',
      url: '/user',
      templateUrl: 'user/list.tpl.jade'
      data: {pageTitle: '用户管理'}
    $stateProvider.state 'system.privilege',
      url: '/privilege',
      templateUrl: 'user/list.tpl.jade'
      data: {pageTitle: '权限管理'}

  .factory('UserService', ['$resource', ($resource) ->
    #$resource("/admin/api/users")

    return {
      getUserById: (userId, scope)->
        userId = parseInt(userId)
        user = null
        angular.forEach scope.users, (item)->
          if userId == item.id
            user = item
            return
        return user
      CRUD: ()->
        return $resource("/admin/api/users")
    }
  ])

  .controller 'UserListCtrl',['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'UserService',($scope, $location, $timeout, $state, ngTableParams, userService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        userService.CRUD().query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.users = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.users, (item)->
        $scope.checkboxes.items[item.id] = value if angular.isDefined(item.id)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.users
      checked = 0
      unchecked = 0
      total = $scope.users.length
      angular.forEach $scope.users, (item)->
        checked   +=  ($scope.checkboxes.items[item.id]) || 0
        unchecked += (!$scope.checkboxes.items[item.id]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)

    # edit action
    $scope.edit = ->
      angular.forEach $scope.checkboxes.items, (value, key)->
        if value
          user = userService.getUserById(key, $scope)
          $state.go 'system.user.detail', {userId : user.id} if user?
          return
  ]




