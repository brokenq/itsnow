  # List catalogs
  angular.module('MscIndex.ServiceCatalog', ['ngTable','ngResource','MscIndex.ServiceCatalog.Detail', 'dnt.action.service'])
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

  .filter('formatTime', ->
    (time) ->
      date = new Date(time)
      return date.toLocaleString()
  )
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
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)

      $scope.selection = {checked: false, items: {}}
      $scope.getCatalogBySn  = (sn)->
        return catalog for catalog in $scope.catalogs when catalog.sn = sn

      $scope.remove = (catalog)->
        feedback = (content) ->
          alert content
        success = ->
          $scope.tableParams.reload();
        failure = (response)->
          feedback response.statusText
        serviceCatalogService.remove({sn: catalog.sn}, success, failure)

      $scope.create = (catalog)->
        $state.go('services.catalog.detail',{'sn':catalog.sn,'action':'create'});

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

    # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
          angular.forEach item,(child)->
            $scope.selection.items[child.id] = value if angular.isDefined(child.id)
      # watch for data checkboxes
      $scope.$watch('selection.items', (values) ->
        return if !$scope.catalogs
        checked = 0
        unchecked = 0
        total = $scope.catalogs.length
        angular.forEach $scope.catalogs, (item)->
          checked   +=  ($scope.selection.items[item.sn]) || 0
          unchecked += (!$scope.selection.items[item.sn]) || 0
        $scope.selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
        # grayed checkbox
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
      , true)
    ]

