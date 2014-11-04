  # List catalogs
  angular.module('Service.Catalog.Private', ['ngTable','ngResource','Service.Catalog.Private.Detail','Service.Catalog.Private.Item', 'dnt.action.service', 'Lib.Feedback'])

    .factory('PrivateService', ['$resource', ($resource) ->
      $resource("/api/private_service_catalogs/:sn",{sn:'@sn'})
    ])
    .factory('PrivateItemService', ['$resource', ($resource) ->
      $resource('/api/private_service_catalogs/:sn/items/:isn',{sn:'@sn',isn:'@isn'})
    ])

    .controller 'PrivateCatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'PrivateService','PrivateItemService', 'ActionService','Feedback', \
                                            ($scope, $location, $timeout, $state, ngTableParams, privateService,privateItemService,ActionService,feedback)->
      options =
        page:  1,           # show first page
        count: 10           # count per page
      args =
        total: 0,
        getData: ($defer, params) ->
          #$location.search(params.url()) # put params in url
          privateService.query(params.url(), (data, headers) ->
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
        #return catalog for catalog in $scope.catalogs when catalog.sn is sn
        for catalog in $scope.catalogs
          return catalog if catalog.sn is sn
          for item in catalog.items
            return item if item.sn is sn

      $scope.remove = (catalog)->
        if($scope.selection.types[catalog.sn] == 'catalog')
          privateService.remove({sn: catalog.sn},->
            feedback.success('删除服务目录'+catalog.sn+'成功')
            delete $scope.selection.items[catalog.sn]
            $scope.tableParams.reload()
          ,(resp)->
            feedback.error('删除服务目录'+catalog.sn+'失败')
          )
        else
          privateItemService.remove({sn:$scope.selection.parent[catalog.sn],isn: catalog.sn}, ->
            feedback.success('删除服务项'+catalog.sn+'成功')
            delete $scope.selection.items[catalog.sn]
            $scope.tableParams.reload()
          ,(resp)->
            feedback.error('删除服务项'+catalog.sn+'失败')
          )

      $scope.create_catalog = (catalog)->
        $state.go('services.catalog.private.detail',{'sn':catalog.sn,'action':'create'})

      $scope.create_item = (catalog)->
        $state.go('services.catalog.private.item',{'sn':catalog.sn,'action':'create'})

      $scope.edit = (catalog)->
        if($scope.selection.types[catalog.sn] == 'catalog')
          $state.go('services.catalog.private.detail',{'sn':catalog.sn,'action':'edit'})
        else
          $state.go('services.catalog.private.item',{'sn':$scope.selection.parent[catalog.sn],'isn':catalog.sn,'action':'edit'})

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

    # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
          $scope.selection.types[item.sn] = 'catalog' if angular.isDefined(item.sn)
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)
            $scope.selection.types[child.sn] = 'item' if angular.isDefined(child.sn)

      # watch for data checkboxes
      $scope.$watch('selection.items', (values) ->
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

