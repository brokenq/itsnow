
angular.module('MscIndex.SLAs', ['ngTable', 'ngResource', 'ngSanitize', 'dnt.action.service', 'Lib.Feedback'])
  .config ($stateProvider, $urlRouterProvider)->
    $stateProvider.state 'slas',
      url: '/slas',
      abstract: true,
      templateUrl: 'slas/index.tpl.jade',
      controller: 'SlasCtrl',
      data: {pageTitle: 'SLA管理', default: 'slas.list'}
    $stateProvider.state 'slas.list',
      url: '/list',
      templateUrl: 'slas/list.tpl.jade'
      controller: 'SlaListCtrl',
      data: {pageTitle: 'SLA列表'}
    $stateProvider.state 'slas.new',
      url: '/new',
      templateUrl: 'slas/new.tpl.jade'
      controller: 'SlaNewCtrl',
      data: {pageTitle: '新增SLA'}
    $stateProvider.state 'slas.view',
      url: '/:id',
      templateUrl: 'slas/view.tpl.jade'
      controller: 'SlaViewCtrl',
      data: {pageTitle: '查看SLA'}
    $stateProvider.state 'slas.edit',
      url: '/:id/edit',
      templateUrl: 'slas/edit.tpl.jade'
      controller: 'SlaEditCtrl',
      data: {pageTitle: '编辑SLA'}
    $urlRouterProvider.when '/slas', '/slas/list'


  .controller('SlasCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    console.log("Initialized the SLAs controller")
  ])
  .controller('SlaListCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    $scope.slas = [
      {
        name: 'SLA-One',
        description: 'The first SLA'
      },
      {
        name: 'SLA-Two',
        description: 'The second SLA'
      },
      {
        name: 'SLA-Three',
        description: 'The third SLA'
      },
    ]
    console.log("Initialized the SLA list controller")
  ])
  .controller('SlaNewCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    console.log("Initialized the SLA New controller")
  ])
  .controller('SlaViewCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    console.log("Initialized the SLA View controller")
  ])
  .controller('SlaEditCtrl', ['$scope', ($scope) ->
    # frontend controller logic
    console.log("Initialized the SLA Edit controller")
  ])
