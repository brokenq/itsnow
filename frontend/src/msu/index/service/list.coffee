  # List catalogs
  angular.module('MsuIndex.ServiceCatalog', ['ngTable','ngResource'])
    .config ($stateProvider)->
      $stateProvider.state 'services',
        url: '/services',
        templateUrl: 'service/list-catalog.tpl.jade'
        data: {pageTitle: '服务管理'}
      $stateProvider.state 'services.catalog',
        url: '/catalog',
        templateUrl: 'service/list-catalog.tpl.jade'
        data: {pageTitle: '服务目录'}

    .factory('ServiceCatalogService', ['$resource', ($resource) ->
      $resource("/api/private_service_catalogs")
    ])
  .filter('formatTime', ->
    (time) ->
      date = new Date(time)
      return date.toLocaleString()
  )
    .controller 'CatalogListCtrl',['$scope', '$location', '$timeout', 'ngTableParams', 'ServiceCatalogService',($scope, $location, $timeout, ngTableParams, serviceCatalogService)->
      options =
        page:  1,           # show first page
        count: 10           # count per page
      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()) # put params in url
          serviceCatalogService.query(params.url(), (data, headers) ->
            $timeout(->
              params.total(headers('total'))
              $defer.resolve($scope.catalogs = data)
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
      $scope.checkboxes = { 'checked': false, items: {} }
      # watch for check all checkbox
      $scope.$watch 'checkboxes.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)
      # watch for data checkboxes
      $scope.$watch('checkboxes.items', (values) ->
        return if !$scope.catalogs
        checked = 0
        unchecked = 0
        total = $scope.catalogs.length
        angular.forEach $scope.catalogs, (item)->
          checked   +=  ($scope.checkboxes.items[item.sn]) || 0
          unchecked += (!$scope.checkboxes.items[item.sn]) || 0
        $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
      , true)
    ]

