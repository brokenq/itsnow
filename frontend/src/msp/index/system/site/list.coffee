# List System
angular.module('System.Site', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'system.site',
      url: '/site',
      templateUrl: 'system/site/list.tpl.jade'
      data: {pageTitle: '地点管理'}

  .factory('SiteService', ['$resource', ($resource) ->
    $resource("/api/sites")
  ])

  .controller 'SiteListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'SiteService',($scope, $location, $timeout, ngTableParams, siteService)->
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        siteService.query(params.url(), (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.site = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.site, (item)->
        $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.site
      checked = 0
      unchecked = 0
      total = $scope.site.length
      angular.forEach $scope.site, (item)->
        checked   +=  ($scope.checkboxes.items[item.sn]) || 0
        unchecked += (!$scope.checkboxes.items[item.sn]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]

