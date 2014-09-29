angular.module('MscIndex.User', ['ngTable','ngResource', 'MscIndex.User.Detail', 'dnt.action.directive'])
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
    $resource("/admin/api/users")
  ])

  .controller 'UserListCtrl',['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'UserService', 'ActionService', ($scope, $location, $timeout, $state, ngTableParams, userService, actionService)->
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
            $scope.getUserById  = ->
                param = $scope.actionService.getQueryData()
                return user for user in $scope.users when user.id is parseInt(param, 10)
            $scope.actionService = actionService.init({scope: $scope, datas: $scope.users, checkKey: "id", mapping: $scope.getUserById, tableId: '#table'})
            $scope.checkDatas = $scope.actionService.getCheckDatas()
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
  ]




