# List accounts
angular.module('MscIndex.Process', ['ngTable','ngResource', 'dnt.action.service'])
  .config ($stateProvider)->
    $stateProvider.state 'processes',
      url: '/processes',
      templateUrl: 'process/list.tpl.jade'
      data: {pageTitle: '服务进程管理'}

  .factory('ProcessService', ['$resource', ($resource) ->
    $resource("/admin/api/processes/:name")
  ])

  .controller 'ProcessListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'ProcessService', '$state', '$http', 'ActionService'
  ($scope, $location, $timeout, ngTableParams, processService, $state, $http, ActionService)->
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

    $scope.selection = {checked: false, items: {}}
    $scope.getProcessByName  = (name)->
      return process for process in $scope.processes when process.name is name
    $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getProcessByName})

    # watch for check all checkbox
    $scope.$watch 'selection.checked', (value)->
      angular.forEach $scope.processes, (item)->
        $scope.selection.items[item.name] = value if angular.isDefined(item.name)
    # watch for data selection
    $scope.$watch('selection.items', (values) ->
      return if !$scope.processes
      checked = 0
      unchecked = 0
      total = $scope.processes.length
      angular.forEach $scope.processes, (item)->
        checked   +=  ($scope.selection.items[item.name]) || 0
        unchecked += (!$scope.selection.items[item.name]) || 0
      $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)


    $scope.feedback = (content) ->
      alert content
    $scope.success = ->
      $scope.tableParams.reload()
    $scope.failure = (response)->
      feedback response.statusText

    $scope.refresh = ->
      $scope.tableParams.reload()
    $scope.start = (process)->
      $http.put "/admin/api/processes/#{process.name}/start", $scope.success, $scope.failure
    $scope.stop = (process)->
      $http.put "/admin/api/processes/#{process.name}/stop", $scope.success, $scope.failure
    $scope.delete = (process)->
      processService.delete process, $scope.success, $scope.failure

    $scope.viewProcess = (processName)->
      $state.go 'process_view', {name: processName}
  ]

