# List My opened Incidents
angular.module('MsuIndex.Incident', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'incidents',
      url: '/incidents',
      templateUrl: 'incident/list-opened.tpl.jade'
      data: {pageTitle: '故障单管理'}
    $stateProvider.state 'incidents.opened',
      url: '/opened',
      templateUrl: 'incident/list-opened.tpl.jade'
      data: {pageTitle: '我的故障单'}
    $stateProvider.state 'incidents.closed',
      url: '/closed',
      templateUrl: 'incident/list-opened.tpl.jade'
      data: {pageTitle: '已关闭故障单'}
    $stateProvider.state 'incidents.create',
      url: '/create',
      templateUrl: 'incident/incident-create.tpl.jade'
      data: {pageTitle: '新建故障单'}
  .factory('IncidentService', ['$resource', ($resource) ->
    $resource("/api/msu-incidents")
  ])
  .filter('formatIncidentStatus', ->
    (status) ->
      return "新建" if status == 'New'
      return "已分配" if status == 'Assigned'
      return "已签收" if status == 'Accepted'
      return "处理中" if status == 'Resolving'
      return "已解决" if status == 'Resolved'
      return "已关闭" if status == 'Closed'
  )
  .filter('formatTime', ->
    (time) ->
      date = new Date(time)
      return date.toLocaleString()
  )
  .controller('IncidentListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'IncidentService',($scope, $location, $timeout, ngTableParams, incidentService)->
    options =
      page:  1,           # show first page
      size: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        incidentService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.incidents = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.incidents, (item)->
        $scope.checkboxes.items[item.id] = value if angular.isDefined(item.id)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.incidents
      checked = 0
      unchecked = 0
      total = $scope.incidents.length
      angular.forEach $scope.incidents, (item)->
        checked   +=  ($scope.checkboxes.items[item.id]) || 0
        unchecked += (!$scope.checkboxes.items[item.id]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ])

