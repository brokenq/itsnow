# View role
angular.module('MscIndex.RoleView', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'role_view',
    url: '/role/{name}',
    templateUrl: 'system/role/view.tpl.jade'
    data: {pageTitle: '查看角色'}

.controller 'roleViewCtrl', ['$scope', '$state', '$stateParams', '$http','RoleService' ,
  ($scope, $state, $stateParams, $http, roleService)->
    roleService.get {name:$stateParams.name}, (data)->
      $scope.role = data
]
