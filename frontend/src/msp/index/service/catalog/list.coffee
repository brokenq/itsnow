  # List catalogs
  angular.module('Service.Catalog', ['ngTable','ngResource', 'dnt.action.service'])
    .config ($stateProvider)->
      $stateProvider.state 'services',
        url: '/services',
        templateUrl: 'service/catalog/list-catalog.tpl.jade'
        data: {pageTitle: '服务管理'}
      $stateProvider.state 'services.catalog',
        url: '/catalog',
        templateUrl: 'service/catalog/list-catalog.tpl.jade'
        data: {pageTitle: '服务目录'}

    .factory('ServiceCatalogService', ['$resource', ($resource) ->
      $resource("/api/public_service_catalogs/accounts")
    ])

    .controller 'CatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'ServiceCatalogService', 'ActionService',($scope, $location, $timeout, $state, ngTableParams, serviceCatalogService,ActionService)->
      options =
        page:  1,           # show first page
        count: 10           # count per page
      args =
        total: 0,
        getData: ($defer, params) ->
          #$location.search(params.url()) # put params in url
          serviceCatalogService.query(params.url(), (data, headers) ->
            $timeout(->
              #params.total(headers('total'))
              $defer.resolve($scope.catalogs = data)
              angular.forEach $scope.catalogs, (item)->
                $scope.selection.types[item.sn] = 'catalog' if angular.isDefined(item.sn)
                angular.forEach item.items,(child)->
                  $scope.selection.types[child.sn] = 'item' if angular.isDefined(child.sn)
                  $scope.selection.parent[child.sn] = item.sn if angular.isDefined(child.sn)
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)

      $scope.selection = {checked: false, items: {},types:{},parent:{}}

      $scope.getCatalogBySn  = (sn)->
        console.log("sn:"+sn)
        #return catalog for catalog in $scope.catalogs when catalog.sn is sn
        for catalog in $scope.catalogs
          console.log(catalog)
          return catalog if catalog.sn is sn
          for item in catalog.items
            console.log(item)
            return item if item.sn is sn


      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

    # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        console.log('checked:'+value)
        angular.forEach $scope.catalogs, (item)->
          $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
          $scope.selection.types[item.sn] = 'catalog' if angular.isDefined(item.sn)
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)
            $scope.selection.types[child.sn] = 'item' if angular.isDefined(child.sn)

      # watch for data checkboxes
      $scope.$watch('selection.items', (values) ->
        console.log(values)
        console.log($scope.selection)
        return if !$scope.catalogs
        checked = 0
        unchecked = 0
        total = $scope.catalogs.length
        angular.forEach $scope.catalogs, (item)->
          checked   +=  ($scope.selection.items[item.sn]) || 0
          unchecked += (!$scope.selection.items[item.sn]) || 0
          total += item.items.length;
          angular.forEach item.items,(child)->
            checked   +=  ($scope.selection.items[child.sn]) || 0
            unchecked += (!$scope.selection.items[child.sn]) || 0
        $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
      , true)
    ]

