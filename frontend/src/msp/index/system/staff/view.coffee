# View staff
angular.module('System.StaffView', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'staff_view',
    url: '/staff/{no}',
    templateUrl: 'system/staff/view.tpl.jade'
    data: {pageTitle: '查看员工'}

.controller 'staffViewCtrl', ['$scope', '$state', '$stateParams', '$http','StaffService' ,
  ($scope, $state, $stateParams, $http, staffService)->
    staffService.get {no:$stateParams.no}, (data)->
      $scope.staff = data
]
