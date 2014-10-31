angular.module('MscIndex.User', ['ngTable','ngResource', 'MscIndex.User.Detail', 'dnt.action.service'])
  .config ($stateProvider)->
    $stateProvider.state 'system.user',
      url: '/user',
      templateUrl: 'system/user/list.tpl.jade'
      data: {pageTitle: '用户管理'}

  .filter('formatUser', ->
    (user) ->
      return user.name if user.name == user.username
      user.name + "(" + user.username + ")"
  )

  .factory('UserService', ['$resource', ($resource) ->
    $resource("/admin/api/users")
  ])

  .controller 'UserListCtrl',['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'UserService', 'ActionService',
  ($scope, $location, $timeout, $state, ngTableParams, userService, ActionService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        userService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.users = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)

    $scope.selection = {checked: false, items: {}}
    $scope.getUserById  = (id)->
      return user for user in $scope.users when user.id is parseInt id
    $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getUserById})
  
    $scope.$watch 'selection.checked', (value)->
      angular.forEach $scope.users, (item)->
        $scope.selection.items[item.id] = value if angular.isDefined(item.id)
    # watch for data checkboxes
    $scope.$watch('selection.items', (values) ->
      return if !$scope.users
      checked = 0
      unchecked = 0
      total = $scope.users.length
      angular.forEach $scope.users, (item)->
        checked   +=  ($scope.selection.items[item.id]) || 0
        unchecked += (!$scope.selection.items[item.id]) || 0
      $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]




