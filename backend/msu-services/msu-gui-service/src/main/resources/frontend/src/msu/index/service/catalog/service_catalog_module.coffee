  angular.module('Service.Catalog',[])
    .config ($stateProvider,$urlRouterProvider)->
      $stateProvider.state 'catalog',
        url: '/services/catalog'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'CatalogCtrl'
        data: {pageTitle: '服务目录', default: 'catalog.public.list'}
      $stateProvider.state 'catalog.public',
        url: '/public'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'PublicCatalogCtrl'
        data: {pageTitle: '公共服务目录', default: 'catalog.public.list'}
      $stateProvider.state 'catalog.private',
        url: '/private'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'PrivateCatalogCtrl'
        data: {pageTitle: '私有服务目录', default: 'catalog.private.list'}
      $stateProvider.state 'catalog.public.list',
        url: '/list'
        templateUrl: 'service/catalog/list_public.tpl.jade'
        controller: 'PublicCatalogListCtrl'
        data: {pageTitle: '公共服务目录列表'}
      $stateProvider.state 'catalog.private.list',
        url: '/list'
        templateUrl: 'service/catalog/list_private.tpl.jade'
        controller: 'PrivateCatalogListCtrl'
        data: {pageTitle: '私有服务目录列表'}
      $stateProvider.state 'catalog.public.view',
        url: '/{sn}/view'
        templateUrl: 'service/catalog/view_catalog.tpl.jade'
        controller: 'CatalogViewCtrl'
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.public.view_item',
        url: '/{sn}/item/{isn}/view'
        templateUrl: 'service/catalog/view_item.tpl.jade',
        controller: 'ItemViewCtrl'
        data: {pageTitle: '查看服务项'}
      $stateProvider.state 'catalog.private.new',
        url: '/{sn}/new'
        templateUrl: 'service/catalog/new_catalog.tpl.jade'
        controller: 'CatalogNewCtrl'
        data: {pageTitle: '新增服务目录'}
      $stateProvider.state 'catalog.private.edit',
        url: '/{sn}/edit'
        templateUrl: 'service/catalog/edit_catalog.tpl.jade'
        controller: 'CatalogEditCtrl'
        data: {pageTitle: '编辑服务目录'}
      $stateProvider.state 'catalog.private.view',
        url: '/{sn}/view'
        templateUrl: 'service/catalog/view_private_catalog.tpl.jade'
        controller: 'PrivateCatalogViewCtrl'
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.private.item_new',
        url: '/{sn}/item/new'
        templateUrl: 'service/catalog/new_item.tpl.jade'
        controller: 'PrivateItemNewCtrl'
        data: {pageTitle: '新增服务项'}
      $stateProvider.state 'catalog.private.item_edit',
        url: '/{sn}/item/{isn}/edit'
        templateUrl: 'service/catalog/edit_item.tpl.jade'
        controller: 'PrivateItemEditCtrl'
        data: {pageTitle: '编辑服务项'}
      $stateProvider.state 'catalog.private.item_view',
        url: '/{sn}/item/{isn}/view'
        templateUrl: 'service/catalog/view_private_item.tpl.jade'
        controller: 'PrivateItemViewCtrl'
        data: {pageTitle: '查看服务项'}
      $urlRouterProvider.when '/services/catalog', '/services/catalog/public'

    .controller('CatalogCtrl',['$scope', '$state', '$log', 'CacheService','Feedback',
      ($scope, $state, $log, CacheService,feedback) ->
        $log.log "Initial CatalogCtrl"
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.submited = false
    ])

    .controller('PublicCatalogCtrl',['$scope', '$state', '$resource','$log', 'CacheService','Feedback',
      ($scope, $state, $resource,$log, CacheService,feedback) ->
        $log.log "Initial PublicCatalogCtrl"

        $scope.PublicServiceCatalog = $resource("/api/public_service_catalogs/:sn", {})
        $scope.PublicServiceItem = $resource("/api/public_service_catalogs/:sn/items/:isn",{})
        $scope.AccountServiceItem = $resource("/api/public_service_catalogs/accounts/items/:isn",{isn: '@isn'})
    ])

    .controller('PrivateCatalogCtrl',['$scope', '$state', '$resource','$log', 'CacheService','Feedback',
      ($scope, $state, $resource,$log, CacheService,feedback) ->
        $log.log "Initial PrivateCatalogCtrl"
        $scope.PrivateServiceCatalog = $resource("/api/private_service_catalogs/:sn", {},{update: {method: "PUT",params: {sn: '@sn'}}})
        $scope.PrivateServiceItem = $resource("/api/private_service_catalogs/:sn/items/:isn",{},{update:{method:"PUT",params: {sn: '@sn',isn:'@isn'}}})
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.cacheService = new CacheService("sn")

        $scope.checkCallback = (resp)->
          $log.log "check callback"
    ])

    .controller('ItemCtrl',['$scope', '$state', '$log',
      ($scope, $state, $log) ->
        $log.log "Initial ItemCtrl"
        $scope.submited = false
    ])

    .controller('PublicCatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'ActionService','Feedback', \
                                      ($scope, $location, $timeout, $state, NgTable, ActionService,feedback)->
      PublicServiceCatalog = $scope.PublicServiceCatalog
      AccountServiceItem = $scope.AccountServiceItem
      args =
        total: 0,
        getData: ($defer, params) ->
          #$location.search(params.url()) # put params in url
          PublicServiceCatalog.query(params.url(), (data, headers) ->
            $timeout(->
              #params.total(headers('total'))
              $defer.resolve($scope.catalogs = data)

              AccountServiceItem.query((data)->
                $scope.serviceItems = data
                #initial selected item
                angular.forEach $scope.serviceItems,(serviceItem)->
                  angular.forEach $scope.catalogs, (item)->
                    angular.forEach item.items,(child)->
                      $scope.selection.items[child.sn] = true if child.sn == serviceItem.sn
                      $scope.selection.parent[child.sn] = item.sn if angular.isDefined(child.sn)
              )
            , 500)
          )
      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)

      $scope.selection = {checked: false, items: {},parent:{}}

      $scope.getItemBySn  = (sn)->
        for catalog in $scope.catalogs
          for item in catalog.items
            return item if item.sn is sn

      $scope.update = ->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            if $scope.selection.items[child.sn]
              AccountServiceItem.save({isn:child.sn},->
                feedback.success "设置公共服务目录项"+child.sn+"成功"
              ,(resp)->
                feedback.error("设置公共服务目录项"+child.sn+"失败",resp)
              )
            else
              AccountServiceItem.remove({isn:child.sn},->
                feedback.success("取消公共服务目录项"+child.sn+"成功")
              ,(resp)->
                feedback.error("取消公共服务目录项"+child.sn+"失败",resp)
              )
        feedback.success("设置公共服务目录完成！")

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getItemBySn})

      # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogViewCtrl', ['$scope', '$stateParams', '$log',\
        ($scope, $stateParams, $log) ->
      PublicServiceCatalog = $scope.PublicServiceCatalog
      sn = $stateParams.sn
      if sn? and sn isnt ''
        PublicServiceCatalog.get({sn:sn}, (data)->
          $scope.catalog = data
        )
    ])

    .controller('ItemViewCtrl', ['$scope', '$stateParams', '$log',
      ($scope, $stateParams, $log) ->
        PublicServiceItem = $scope.PublicServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        if isn? and sn? and isn isnt '' and sn isnt ''
          PublicServiceItem.get({sn:sn,isn:isn}, (data)->
            $scope.item = data
          )

    ])

    .controller('PrivateCatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams',  'ActionService','CommonService','CacheService','Feedback',
      ($scope, $location, $timeout, $state, NgTable, ActionService,commonService,CacheService,feedback)->
        PrivateServiceCatalog = $scope.PrivateServiceCatalog
        PrivateServiceItem = $scope.PrivateServiceItem
        args =
          total: 0,
          getData: ($defer, params) ->
            #$location.search(params.url()) # put params in url
            PrivateServiceCatalog.query(params.url(), (data, headers) ->
              $timeout(->
                $scope.cacheService.cache data
                $defer.resolve($scope.catalogs = data)
                angular.forEach $scope.catalogs, (catalog)->
                  $scope.selection.types[catalog.sn] = 'catalog' if catalog.sn?
                  angular.forEach catalog.items,(child)->
                    $scope.selection.types[child.sn] = 'item' if child.sn?
                    $scope.selection.parent[child.sn] = catalog.sn if child.sn?
              , 500)
            )
        $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)

        $scope.selection = {checked: false, items: {},types:{},parent:{}}

        $scope.getCatalogBySn  = (sn)->
          #return catalog for catalog in $scope.catalogs when catalog.sn is sn
          for catalog in $scope.catalogs
            return catalog if catalog.sn is sn
            for item in catalog.items
              return item if item.sn is sn
        $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

        $scope.remove = (catalog)->
          if($scope.selection.types[catalog.sn] is 'catalog')
            PrivateServiceCatalog.remove({sn: catalog.sn},->
              feedback.success("删除私有服务目录"+catalog.title+"成功")
              delete $scope.selection.items[catalog.sn]
              $scope.tableParams.reload()
            ,(resp)->
              feedback.error("删除私有服务目录"+catalog.title+"失败",resp)
            )
          else
            PrivateServiceItem.remove({sn:$scope.selection.parent[catalog.sn],isn: catalog.sn}, ->
              feedback.success("删除私有服务项"+catalog.title+"成功")
              delete $scope.selection.items[catalog.sn]
              $scope.tableParams.reload()
            ,(resp)->
              feedback.error("删除私有服务项"+catalog.title+"失败",resp)
            )

        $scope.create_catalog = (catalog)->
          $state.go('catalog.private.new',{'sn':catalog.sn})

        $scope.create_item = (catalog)->
          $state.go('catalog.private.item_new',{'sn':catalog.sn})

        $scope.edit = (value)->
          if($scope.selection.types[value.sn] is 'catalog')
            $state.go('catalog.private.edit',{'sn':value.sn,'action':'edit'})
          else
            $state.go('catalog.private.item_edit',{'sn':$scope.selection.parent[value.sn],'isn':value.sn})

        $scope.view = (item)->
          $state.go('catalog.item_view',{'sn':$scope.selection.parent[item.sn],'isn':item.sn})

        # watch for check all checkbox
        $scope.$watch 'selection.checked', (value)->
          angular.forEach $scope.catalogs, (item)->
            $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
            angular.forEach item.items,(child)->
              $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogNewCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',
      ($scope, $state, $stateParams,feedback) ->
        PrivateServiceCatalog = $scope.PrivateServiceCatalog
        sn = $stateParams.sn
        $scope.catalog = {parentId:null,level:1}
        if sn? and sn isnt ''
          $scope.parentCatalog = $scope.cacheService.find $stateParams.sn,true
          $scope.catalog.parentId = $scope.parentCatalog.id
          $scope.catalog.level = $scope.parentCatalog.level + 1

        $scope.create = ->
          $scope.submited = true
          catalog = new PrivateServiceCatalog $scope.catalog
          catalog.$save(->
            feedback.success("创建私有服务目录"+catalog.title+"成功")
            $state.go('catalog.private.list')
          ,(resp)->
            feedback.error("创建私有服务目录"+catalog.title+"失败",resp)
          )
    ])

    .controller('CatalogEditCtrl', ['$scope', '$state', '$stateParams','Feedback',
      ($scope, $state, $stateParams,feedback) ->
        PrivateServiceCatalog = $scope.PrivateServiceCatalog
        sn = $stateParams.sn
        $scope.catalog = $scope.cacheService.find $stateParams.sn,true

        $scope.update = ->
          catalog = new PrivateServiceCatalog $scope.catalog
          catalog.$update({sn: catalog.sn},->
            feedback.success("更新私有服务目录"+catalog.title+"成功")
            $state.go('catalog.private.view',{sn:catalog.sn})
          ,(resp)->
            feedback.error("更新私有服务目录"+catalog.title+"失败",resp)
          )
    ])

    .controller('PrivateCatalogViewCtrl', ['$scope', '$stateParams', '$log',
      ($scope, $stateParams, $log) ->
        $log.log "Initial PrivateCatalogView Ctrl"
        $scope.catalog = $scope.cacheService.find $stateParams.sn,true
    ])

    .controller('PrivateItemViewCtrl', ['$scope', '$stateParams', '$log',
      ($scope, $stateParams, $log) ->
        $log.log "Initial PrivateItemViewCtrl"
        PrivateServiceItem = $scope.PrivateServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        PrivateServiceItem.get({sn:sn,isn:isn},(data)->
          $scope.item = data
        )
    ])

    .controller('PrivateItemNewCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',
      ($scope, $state, $stateParams,feedback) ->
        PrivateServiceItem = $scope.PrivateServiceItem
        sn = $stateParams.sn
        $scope.item = {catalog:null};

        $scope.item.catalog = $scope.cacheService.find $stateParams.sn,true

        $scope.create = ->
          $scope.submited = true
          serviceItem = new PrivateServiceItem $scope.item
          serviceItem.$save({sn:sn},->
            feedback.success("创建服务项"+serviceItem.title+"成功")
            $state.go('catalog.private.list')
          ,(resp)->
            feedback.error("创建服务项"+serviceItem.title+"失败",resp)
          )
    ])

    .controller('PrivateItemEditCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',
      ($scope, $state, $stateParams,feedback) ->
        PrivateServiceItem = $scope.PrivateServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        PrivateServiceItem.get({sn:sn,isn:isn},(data)->
            $scope.item = data
        )

        $scope.update = ->
          delete $scope.item.$promise;
          delete $scope.item.$resolved;
          serviceItem = new PrivateServiceItem $scope.item
          serviceItem.$update({sn:sn,isn:isn}, ->
            feedback.success("更新服务项"+serviceItem.title+"成功");
            $state.go('catalog.private.item_view',{sn:sn,isn:isn})
          ,(resp)->
            feedback.error("更新服务项"+serviceItem.title+"失败",resp)
          )

    ])
