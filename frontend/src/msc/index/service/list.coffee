  # List catalogs
  angular.module('MscIndex.ServiceCatalog', ['ngTable','ngResource','MscIndex.ServiceCatalog.Detail','MscIndex.ServiceCatalog.Item', 'dnt.action.service'])
    .config ($stateProvider)->
      $stateProvider.state 'services',
        url: '/services',
        templateUrl: 'service/list-catalog.tpl.jade'
        data: {pageTitle: '服务管理'}
      $stateProvider.state 'services.catalog',
        url: '/catalog',
        templateUrl: 'service/list-catalog.tpl.jade'
        data: {pageTitle: '服务目录'}
      $stateProvider.state 'services.sla',
        url: '/sla',
        templateUrl: 'service/list.tpl.jade'
        data: {pageTitle: '服务级别管理'}

    .factory('ServiceCatalogService', ['$resource', ($resource) ->
      $resource("/admin/api/public_service_catalogs/:sn",{sn:'@sn'})
    ])
    .factory('ServiceItemService', ['$resource', ($resource) ->
      $resource('/admin/api/public_service_catalogs/:sn/items/:isn',{sn:'@sn',isn:'@isn'})
    ])

  .filter('formatTime', ->
    (time) ->
      date = new Date(time)
      return date.toLocaleString()
  )
    .controller 'CatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'ServiceCatalogService','ServiceItemService', 'ActionService',($scope, $location, $timeout, $state, ngTableParams, serviceCatalogService,serviceItemService,ActionService)->
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
        return catalog for catalog in $scope.catalogs when catalog.sn is sn

      $scope.remove = (catalog)->
        if($scope.selection.types[catalog.sn] == 'catalog')
          feedback = (content) ->
            alert content
          success = ->
            $scope.tableParams.reload()
          failure = (response)->
            feedback response.statusText
          serviceCatalogService.remove({sn: catalog.sn}, success, failure)
        else
          feedback = (content) ->
            alert content
          success = ->
            $scope.tableParams.reload()
          failure = (response)->
            feedback response.statusText
          serviceItemService.remove({sn:$scope.selection.parent[catalog.sn],isn: catalog.sn}, success, failure)

      $scope.create_catalog = (catalog)->
        $state.go('services.catalog.detail',{'sn':catalog.sn,'action':'create'})
      $scope.create_item = (catalog)->
        $state.go('services.catalog.item',{'sn':catalog.sn,'action':'create'})

      $scope.edit = (catalog)->
        if($scope.selection.types[catalog.sn] == 'catalog')
          $state.go('services.catalog.detail',{'sn':catalog.sn,'action':'edit'})
        else
          $state.go('services.catalog.item',{'sn':$scope.selection.parent[catalog.sn],'isn':catalog.sn,'action':'edit'})

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

