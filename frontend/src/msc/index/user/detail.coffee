angular.module('MscIndex.User.Detail', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'system.user.detail',
    url: '/detail/{id}',
    templateUrl: 'user/detail.tpl.jade'
    data: {pageTitle: '用户信息'}


#.factory('UserDetailService', ['$resouce', '$stateParams', ($resource, $stateParams)->
#$resource('admin/api/users/' + $stateParams.username)
#])

.controller 'DetailCtrl', ['$scope', '$stateParams', ($scope, $stateParams)->
  $scope.userId = $stateParams.id;
]