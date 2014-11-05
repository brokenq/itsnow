# View dept
angular.module('System.DeptView', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'dept_view',
    url: '/department/{sn}',
    templateUrl: 'system/department/view.tpl.jade'
    data: {pageTitle: '查看部门'}

.controller 'departmentViewCtrl', ['$scope', '$state', '$stateParams', '$http','DeptService' ,
  ($scope, $state, $stateParams, $http, roleService)->
    roleService.get {sn:$stateParams.sn}, (data)->
      $scope.department = data
]
