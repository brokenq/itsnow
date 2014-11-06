angular.module('System.WorkTime',
    ['ngTable',
     'ngResource',
     'ngSanitize',
     'dnt.action.service',
     'Lib.Commons',
     'Lib.Utils',
     'Lib.Feedback'])
  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'worketimes',
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
      url: '/{id}',
      templateUrl: 'system/worktimes/view.tpl.jade'
      controller: 'WorkTimeViewCtrl',
      data: {pageTitle: '查看工作时间'}
    $stateProvider.state 'worktimes.edit',
      url: '/{id}/edit',
      templateUrl: 'system/worktimes/edit.tpl.jade'
      controller: 'WorkTimeEditCtrl',
      data: {pageTitle: '编辑时间'}
    $urlRouterProvider.when '/worktimes', '/worktimes/list'


  .controller('WorkTimeCtrl', ['$scope', '$state', 'Feedback', 'CacheService', \
                           ($scope,   $state,   feedback,   CacheService) ->

  ])
  .controller('WorkTimeListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService',\
                              ($scope,   $location,   NgTable,         ActionService,   commonService) ->

  ])
  .controller('WorkTimeViewCtrl', ['$scope', '$stateParams', ($scope, $stateParams) ->

  ])
  .controller('WorkTimeNewCtrl', ['$scope', '$state', 'Feedback', \
                             ($scope, $state, feedback) ->

  ])
  .controller('WorkTimeEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback', \
                             ($scope, $state, $stateParams, feedback) ->

  ])
