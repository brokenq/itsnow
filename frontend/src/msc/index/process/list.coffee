# List accounts
angular.module('MscIndex.Process', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'processes',
      url: '/processes',
      templateUrl: 'process/list.tpl.jade'
      data: {pageTitle: '服务进程管理'}

  .factory('ProcessService', ['$resource', ($resource) ->
    $resource("/admin/api/processes/:id", {id: "@id"})
  ])
  .filter('formatProcessStatus', ->
    (status) ->
      return "已关机" if status == 'Stopped'
      return "启动中" if status == 'Starting'
      return "运行中" if status == 'Running'
      return "停止中" if status == 'Stopping'
      return "有故障" if status == 'Abnormal'
  )
  .controller 'ProcessListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'ProcessService',($scope, $location, $timeout, ngTableParams, processService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        processService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.processes = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.processes, (item)->
        $scope.checkboxes.items[item.name] = value if angular.isDefined(item.name)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.processes
      checked = 0
      unchecked = 0
      total = $scope.processes.length
      angular.forEach $scope.processes, (item)->
        checked   +=  ($scope.checkboxes.items[item.name]) || 0
        unchecked += (!$scope.checkboxes.items[item.name]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]

