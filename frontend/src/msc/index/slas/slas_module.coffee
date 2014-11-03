
angular.module('MscIndex.SLAs', ['ngTable', 'ngResource', 'ngSanitize', 'dnt.action.service', 'Lib.Utils', 'Lib.Feedback'])
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
    $scope.options =
      page:  1,           # show first page
      count: 10           # count per page
  ])
  .controller('SlaListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService', 'Feedback',\
                              ($scope, $location, NgTable, ActionService, commonService, feedback) ->
    # frontend controller logic
    data = [
      {id: 1, title: 'SLA One', description: 'The first SLA'},
      {id: 2, title: 'SLA Two', description: 'The second SLA'},
      {id: 3, title: 'SLA Three', description: 'The third SLA'},
      {id: 4, title: 'SLA Four', description: 'The 4th SLA'},
      {id: 5, title: 'SLA Five', description: 'The 5th SLA'},
      {id: 6, title: 'SLA Six', description: 'The 6th SLA'},
      {id: 7, title: 'SLA Seven', description: 'The 7th SLA'},
      {id: 8, title: 'SLA Eight', description: 'The 8th SLA'},
      {id: 9, title: 'SLA Nine', description: 'The 9th SLA'},
      {id: 10, title: 'SLA Ten', description: 'The 10th SLA'},
      {id: 11, title: 'SLA Eleven', description: 'The 11th SLA'},
    ]
    $scope.slas = data
    $scope.getSlaById = (id)->
      return sla for sla in data when sla.id == id
    args =
      total: 0
      getData: ($defer, params)->
        $defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));

    $scope.selection = { 'checked': false, items: {} }
    $scope.slasTable = new NgTable(angular.extend($scope.options, $location.search()), args);
    $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getSlaById})
    commonService.watchSelection($scope.selection, $scope.slas, "id")
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
