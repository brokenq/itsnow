angular.module('System.WorkTime',
  ['ngTable',
   'ngResource',
   'ngSanitize',
   'dnt.action.service',
   'Lib.Commons',
   'Lib.Utils',
   'Lib.Feedback'])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'worktimes',
    url: '/worktimes',
    abstract: true,
    templateUrl: 'system/worktimes/index.tpl.jade',
    controller: 'WorkTimeCtrl',
    data: {pageTitle: '工作时间管理', default: 'worktimes.list'}
  $stateProvider.state 'worktimes.list',
    url: '/list',
    templateUrl: 'system/worktimes/list.tpl.jade'
    controller: 'WorkTimeListCtrl',
    data: {pageTitle: '时间列表'}
  $stateProvider.state 'worktimes.new',
    url: '/new',
    templateUrl: 'system/worktimes/new.tpl.jade'
    controller: 'WorkTimeNewCtrl',
    data: {pageTitle: '新增时间'}
  $stateProvider.state 'worktimes.view',
    url: '/{sn}',
    templateUrl: 'system/worktimes/view.tpl.jade'
    controller: 'WorkTimeViewCtrl',
    data: {pageTitle: '查看工作时间'}
  $stateProvider.state 'worktimes.edit',
    url: '/{sn}/edit',
    templateUrl: 'system/worktimes/edit.tpl.jade'
    controller: 'WorkTimeEditCtrl',
    data: {pageTitle: '编辑时间'}
  $urlRouterProvider.when '/worktimes', '/worktimes/list'
.factory('WorkTimeService', ['$resource', ($resource) ->
    $resource("/api/work-times/:sn", {sn: "@sn"})
  ])
.controller('WorkTimeCtrl', ['$scope', '$state', 'Feedback', 'CacheService',
    ($scope, $state, feedback, CacheService) ->
      $scope.workdates = [
        {
          id: "1"
          name: "星期一"
        }
        {
          id: "2"
          name: "星期二"
        }
        {
          id: "3"
          name: "星期三"
        }
        {
          id: "4"
          name: "星期四"
        }
        {
          id: "5"
          name: "星期五"
        }
        {
          id: "6"
          name: "星期六"
        }
        {
          id: "7"
          name: "星期天"
        }
      ]
])
.controller('WorkTimeListCtrl',
  ['$scope', '$location', 'ngTableParams', 'ActionService', 'WorkTimeService', 'CommonService',
    ($scope, $location, NgTable, ActionService, WorkTimeService, commonService) ->
])
.controller('WorkTimeViewCtrl', ['$scope', '$stateParams', ($scope, $stateParams) ->
])
.controller('WorkTimeNewCtrl', ['$scope', '$state', 'Feedback', 'WorkTimeService'
    ($scope, $state, feedback, WorkTimeService) ->
])
.controller('WorkTimeEditCtrl', ['$scope', '$state', '$stateParams', 'WorkTimeService', 'Feedback',
    ($scope, $state, $stateParams, WorkTimeService, feedback) ->
])
