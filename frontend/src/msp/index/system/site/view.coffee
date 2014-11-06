# View site
angular.module('System.SiteView', ['ngResource'])
.config ($stateProvider)->
  $stateProvider.state 'site_view',
    url: '/site/{sn}',
    templateUrl: 'system/site/view.tpl.jade'
    data: {pageTitle: '查看地点'}

.controller 'SiteViewCtrl', ['$scope', '$state', '$stateParams', '$http','SiteService' ,
  ($scope, $state, $stateParams, $http, siteService)->
    siteService.get {sn:$stateParams.sn}, (data)->
      $scope.site = data
]

