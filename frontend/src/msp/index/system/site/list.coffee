# List System
angular.module('MspApp.System', ['ngTable','ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'system.role',
      url: '/role',
      templateUrl: 'system/role/list.tpl.jade'
      data: {pageTitle: '角色管理'}
    $stateProvider.state 'system.authority',
      url: '/authority',
      templateUrl: 'system/authority/list.tpl.jade'
      data: {pageTitle: '权限管理'}
    $stateProvider.state 'system.site',
      url: '/site',
      templateUrl: 'system/site/list.tpl.jade'
      data: {pageTitle: '地点管理'}

  .factory('SiteService', ['$resource', ($resource) ->
    $resource("/admin/api/sites")
  ])
  .filter('formatAccountStatus', ->
    (status) ->
      return "待审核" if status == 'New'
      return "已批准" if status == 'Valid'
      return "被拒绝" if status == 'Rejected'
  )
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
            $defer.resolve($scope.accounts = data)
          , 500)
        )
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.checkboxes = { 'checked': false, items: {} }
    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.accounts, (item)->
        $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)
    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.accounts
      checked = 0
      unchecked = 0
      total = $scope.accounts.length
      angular.forEach $scope.accounts, (item)->
        checked   +=  ($scope.checkboxes.items[item.sn]) || 0
        unchecked += (!$scope.checkboxes.items[item.sn]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)
  ]

