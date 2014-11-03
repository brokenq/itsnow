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

  .controller 'ProcessListCtrl',['$scope', '$location', '$timeout', '$resource', '$state', '$http', 'ngTableParams', 'ActionService', 'Feedback', 'CommonService', \
                                 ($scope,   $location,   $timeout,   $resource,   $state,   $http,   ngTableParams,   ActionService,   Feedback,   commonService)->
    Processes = $resource("/admin/api/processes/:name", {name: "@name"})
    actions =
      start: {method: 'PUT', params: {action: 'start'}}
      stop:  {method: 'PUT', params: {action: 'stop'}}
    Process = $resource("/admin/api/processes/:name/:action", {name: "@name"}, actions)

    $scope.processes = []
    $scope.selection = {checked: false, items: {}}
    $scope.tableParams = commonService.instanceTable Processes, $scope.processes
    commonService.watchSelection $scope.selection, $scope.processes, "name"

    $scope.getProcessByName  = (name)->
      return process for process in $scope.processes when process.name is name
    $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.getProcessByName}

    $scope.refresh = ->
      $scope.tableParams.reload()

    $scope.start = (process)->
      acc = new Process process
      acc.$start ->
        Feedback.success "正在启动#{process.name}"
        $scope.tableParams.reload()
      , (resp)->
        Feedback.error "启动#{process.name}失败", resp

    $scope.stop = (process)->
      acc = new Process process
      acc.$stop ->
        Feedback.success "已停止#{process.name}"
        $scope.tableParams.reload()
      , (resp)->
        Feedback.error "停止#{process.name}失败", resp

    $scope.delete = (process)->
      acc = new Process process
      acc.$remove ->
        $scope.tableParams.reload()

  ]

