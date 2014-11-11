  # List catalogs
  angular.module('Service.Catalog',
    ['ngTable',
     'ngResource',
     'Lib.Commons',
     'Lib.Utils',
     'dnt.action.service',
     'Lib.Feedback'])
    .config ($stateProvider,$urlRouterProvider)->
      $stateProvider.state 'catalog',
        url: '/services/catalog',
        templateUrl: 'service/catalog/index.tpl.jade',
        abstract:true,
        controller: 'CatalogCtrl',
        data: {pageTitle: '服务目录', default: 'catalog.public.list'}
      $stateProvider.state 'catalog.public',
        url: '/public',
        templateUrl: 'service/catalog/index.tpl.jade',
        abstract:true,
        controller: 'PublicCatalogCtrl',
        data: {pageTitle: '公共服务目录', default: 'catalog.public.list'}
      $stateProvider.state 'catalog.private',
        url: '/private',
        templateUrl: 'service/catalog/index.tpl.jade',
        abstract:true,
        controller: 'PrivateCatalogCtrl',
        data: {pageTitle: '私有服务目录', default: 'catalog.private.list'}
      $stateProvider.state 'catalog.public.list',
        url: '/list',
        templateUrl: 'service/catalog/list_public.tpl.jade'
        controller: 'PublicCatalogListCtrl',
        data: {pageTitle: '公共服务目录列表'}
      $stateProvider.state 'catalog.private.list',
        url: '/list',
        templateUrl: 'service/catalog/list_private.tpl.jade'
        controller: 'PrivateCatalogListCtrl',
        data: {pageTitle: '私有服务目录列表'}
      $stateProvider.state 'catalog.public.view',
        url: '/{sn}/view',
        templateUrl: 'service/catalog/view_catalog.tpl.jade'
        controller: 'CatalogViewCtrl',
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.public.view_item',
        url: '/{sn}/item/{isn}/view',
        templateUrl: 'service/catalog/view_item.tpl.jade',
        controller: 'ItemViewCtrl',
        data: {pageTitle: '查看服务项'}
      $stateProvider.state 'catalog.private.new',
        url: '/{sn}/new',
        templateUrl: 'service/catalog/new_catalog.tpl.jade'
        controller: 'CatalogNewCtrl',
        data: {pageTitle: '新增服务目录'}
      $stateProvider.state 'catalog.private.edit',
        url: '/{sn}/edit',
        templateUrl: 'service/catalog/edit_catalog.tpl.jade'
        controller: 'CatalogEditCtrl',
        data: {pageTitle: '编辑服务目录'}
      $stateProvider.state 'catalog.private.view',
        url: '/{sn}/view',
        templateUrl: 'service/catalog/view_private_catalog.tpl.jade'
        controller: 'PrivateCatalogViewCtrl',
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.private.item',
        url: '/{sn}/item',
        templateUrl: 'service/catalog/index.tpl.jade',
        abstract:true,
        controller: 'ItemCtrl',
        data: {pageTitle: '服务项', default: 'catalog.private.item.view'}
      $stateProvider.state 'catalog.private.item.new',
        url: '/new',
        templateUrl: 'service/catalog/new_item.tpl.jade'
        controller: 'PrivateItemNewCtrl',
        data: {pageTitle: '新增服务项'}
      $stateProvider.state 'catalog.private.item.edit',
        url: '/{isn}/edit',
        templateUrl: 'service/catalog/edit_item.tpl.jade'
        controller: 'PrivateItemEditCtrl',
        data: {pageTitle: '编辑服务项'}
      $stateProvider.state 'catalog.private.item.view',
        url: '/{isn}/view',
        templateUrl: 'service/catalog/view_private_item.tpl.jade'
        controller: 'PrivateItemViewCtrl',
        data: {pageTitle: '查看服务项'}
      $urlRouterProvider.when '/services/catalog', '/services/catalog/public'

    .factory('PublicCatalogService', ['$resource', ($resource) ->
      $resource("/api/public_service_catalogs/:sn",{},
        get: { method: 'GET', params: {sn: '@sn'}},
        query: { method: 'GET', params: {sn: '@sn'}, isArray: true},
      )
    ])

    .factory('PublicItemService', ['$resource', ($resource) ->
      $resource('/api/public_service_catalogs/:sn/items/:isn',{},
        get: { method: 'GET', params: {sn: '@sn',isn:'@isn'}}
      )
    ])

    .factory('ServiceItemService', ['$resource', ($resource) ->
      $resource("/api/public_service_catalogs/accounts")
    ])
    .factory('PublicServiceItemService', ['$resource', ($resource) ->
      $resource("/api/public_service_catalogs/accounts/items/:isn",{isn: '@isn'})
    ])

    .factory('PrivateCatalogService', ['$resource', ($resource) ->
      $resource("/api/private_service_catalogs/:sn",{},
        get: { method: 'GET', params: {sn: '@sn'}},
        save: { method: 'POST'},
        update: { method: 'PUT', params: {sn: '@sn'}},
        query: { method: 'GET', params: {sn: '@sn'}, isArray: true},
        remove: { method: 'DELETE', params: {sn: '@sn'}}
      )
    ])
    .factory('PrivateItemService', ['$resource', ($resource) ->
      $resource('/api/private_service_catalogs/:sn/items/:isn',{},
        get: { method: 'GET', params: {sn: '@sn',isn:'@isn'}},
        update:{method:'PUT', params: {sn: '@sn',isn:'@isn'}},
        query: { method: 'GET', params: {sn: '@sn'}, isArray: true},
        save:{method:'POST', params: {sn: '@sn'}},
        remove:{method:'DELETE', params: {sn: '@sn',isn:'@isn'}}
      )
    ])

    .controller('CatalogCtrl',['$scope', '$state', '$log', 'CacheService','Feedback',
      ($scope, $state, $log, CacheService,feedback) ->

    ])

    .controller('PublicCatalogCtrl',['$scope', '$state', '$log', 'CacheService','Feedback',
      ($scope, $state, $log, CacheService,feedback) ->
        # frontend controller logic
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.submited = false

    ])

    .controller('PrivateCatalogCtrl',['$scope', '$state', '$log', 'CacheService','Feedback',
      ($scope, $state, $log, CacheService,feedback) ->
        # frontend controller logic
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.cacheService = new CacheService("sn")
        $scope.submited = false
    ])

    .controller('ItemCtrl',['$scope', '$state', '$log',
      ($scope, $state, $log) ->
        # frontend controller logic
        $scope.submited = false
    ])

    .controller('PublicCatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'PublicCatalogService','ServiceItemService','PublicServiceItemService', 'ActionService','Feedback', \
                                      ($scope, $location, $timeout, $state, ngTableParams, publicCatalogService,serviceItemService,publicServiceItemService,ActionService,feedback)->
      args =
        total: 0,
        getData: ($defer, params) ->
          #$location.search(params.url()) # put params in url
          publicCatalogService.query(params.url(), (data, headers) ->
            $timeout(->
              #params.total(headers('total'))
              $defer.resolve($scope.catalogs = data)

              serviceItemService.query((data)->
                $scope.serviceItems = data
                angular.forEach $scope.serviceItems,(serviceItem)->
                  angular.forEach $scope.catalogs, (item)->
                    angular.forEach item.items,(child)->
                      $scope.selection.items[child.sn] = true if child.sn == serviceItem.sn
              )
              angular.forEach $scope.catalogs, (item)->
                if angular.isDefined(item.sn)
                  $scope.selection.types[item.sn] = 'catalog'
                  #$scope.selection.items[item.sn] = true
                angular.forEach item.items,(child)->
                  $scope.selection.types[child.sn] = 'item' if angular.isDefined(child.sn)
                  $scope.selection.parent[child.sn] = item.sn if angular.isDefined(child.sn)
            , 500)
          )
      $scope.tableParams = new ngTableParams(angular.extend($scope.options, $location.search()), args)

      $scope.selection = {checked: false, items: {},types:{},parent:{}}

      $scope.getCatalogBySn  = (sn)->
        #return catalog for catalog in $scope.catalogs when catalog.sn is sn
        for catalog in $scope.catalogs
          #return catalog if catalog.sn is sn
          for item in catalog.items
            return item if item.sn is sn

      $scope.update = ->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            if $scope.selection.items[child.sn]
              publicServiceItemService.save({isn:child.sn},->
                feedback.success('设置公共服务目录项'+child.sn+'成功')
              ,(resp)->
                feedback.error('设置公共服务目录项'+child.sn+'失败',resp)
              )
            else
              publicServiceItemService.remove({isn:child.sn},->
                feedback.success('取消公共服务目录项'+child.sn+'成功')
              ,(resp)->
                feedback.error('取消公共服务目录项'+child.sn+'失败',resp)
              )
        feedback.success('设置公共服务目录完成！')

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

      # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogViewCtrl', ['$scope', '$stateParams','PublicCatalogService', '$log',\
        ($scope, $stateParams,publicCatalogService, $log) ->
      $scope.sn = $stateParams.sn
      if(angular.isDefined($scope.sn) && !($scope.sn is ''))
        publicCatalogService.get({sn:$scope.sn}, (data)->
          $scope.catalog = data
        )
    ])

    .controller('ItemViewCtrl', ['$scope', '$stateParams','PublicItemService', '$log',
      ($scope, $stateParams, publicItemService,$log) ->
        $scope.sn = $stateParams.sn
        $scope.isn = $stateParams.isn
        if(angular.isDefined($scope.isn) && !($scope.isn is ''))
          publicItemService.get({sn:$scope.sn,isn:$scope.isn}, (data)->
            $scope.item = data
          )

    ])

    .controller('PrivateCatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'PrivateCatalogService','PrivateItemService', 'ActionService','CommonService','CacheService','Feedback',
      ($scope, $location, $timeout, $state, ngTableParams, privateCatalogService,privateItemService,ActionService,commonService,CacheService,feedback)->
        args =
          total: 0,
          getData: ($defer, params) ->
            #$location.search(params.url()) # put params in url
            privateCatalogService.query(params.url(), (data, headers) ->
              $timeout(->
                #params.total(headers('total'))
                $scope.cacheService.cache data
                $defer.resolve($scope.catalogs = data)
                angular.forEach $scope.catalogs, (catalog)->
                  $scope.selection.types[catalog.sn] = 'catalog' if angular.isDefined(catalog.sn)
                  angular.forEach catalog.items,(child)->
                    $scope.selection.types[child.sn] = 'item' if angular.isDefined(child.sn)
                    $scope.selection.parent[child.sn] = catalog.sn if angular.isDefined(child.sn)
              , 500)
            )
        $scope.tableParams = new ngTableParams(angular.extend($scope.options, $location.search()), args)

        $scope.selection = {checked: false, items: {},types:{},parent:{}}

        $scope.getCatalogBySn  = (sn)->
          #return catalog for catalog in $scope.catalogs when catalog.sn is sn
          for catalog in $scope.catalogs
            return catalog if catalog.sn is sn
            for item in catalog.items
              return item if item.sn is sn
        $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

        $scope.remove = (catalog)->
          if($scope.selection.types[catalog.sn] == 'catalog')
            privateCatalogService.remove({sn: catalog.sn},->
              feedback.success('删除私有服务目录'+catalog.sn+'成功')
              delete $scope.selection.items[catalog.sn]
              $scope.tableParams.reload()
            ,(resp)->
              feedback.error('删除私有服务目录'+catalog.sn+'失败',resp)
            )
          else
            privateItemService.remove({sn:$scope.selection.parent[catalog.sn],isn: catalog.sn}, ->
              feedback.success('删除私有服务项'+catalog.sn+'成功')
              delete $scope.selection.items[catalog.sn]
              $scope.tableParams.reload()
            ,(resp)->
              feedback.error('删除私有服务项'+catalog.sn+'失败',resp)
            )

        $scope.create_catalog = (catalog)->
          $state.go('catalog.private.new',{'sn':catalog.sn})

        $scope.create_item = (catalog)->
          $state.go('catalog.private.item.new',{'sn':catalog.sn})

        $scope.edit = (value)->
          if($scope.selection.types[value.sn] == 'catalog')
            catalog = value
            $state.go('catalog.private.edit',{'sn':catalog.sn,'action':'edit'})
          else
            item = value
            $state.go('catalog.private.item.edit',{'sn':$scope.selection.parent[item.sn],'isn':item.sn})

        $scope.view = (item)->
          $state.go('catalog.item.view',{'sn':$scope.selection.parent[item.sn],'isn':item.sn})

        # watch for check all checkbox
        $scope.$watch 'selection.checked', (value)->
          angular.forEach $scope.catalogs, (item)->
            $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
            angular.forEach item.items,(child)->
              $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogNewCtrl', ['$scope', '$state', '$stateParams' ,'PrivateCatalogService','Feedback',
      ($scope, $state, $stateParams,privateCatalogService,feedback) ->
        $scope.sn = $stateParams.sn
        $scope.catalog = {parentId:null,level:1}
        if(angular.isDefined($scope.sn) && $scope.sn && !($scope.sn is ''))
          $scope.parentCatalog = $scope.cacheService.find $stateParams.sn,true
          $scope.catalog.parentId = $scope.parentCatalog.id
          $scope.catalog.level = $scope.parentCatalog.level + 1

        $scope.create = ->
          $scope.submited = true
          catalog = $scope.catalog
          privateCatalogService.save(catalog,->
            feedback.success('创建私有服务目录'+catalog.sn+'成功')
            $state.go('catalog.private.list')
          ,(resp)->
            feedback.error('创建私有服务目录'+catalog.sn+'失败',resp)
          )
    ])

    .controller('CatalogEditCtrl', ['$scope', '$state', '$stateParams' ,'PrivateCatalogService','Feedback',
      ($scope, $state, $stateParams,privateCatalogService,feedback) ->
        $scope.sn = $stateParams.sn
        $scope.catalog = $scope.cacheService.find $stateParams.sn,true
        $scope.update = ->
          privateCatalogService.update({sn: $scope.catalog.sn},$scope.catalog,->
            feedback.success('更新私有服务目录'+$scope.catalog.sn+'成功')
            $state.go('catalog.private.view',{sn:$scope.catalog.sn})
          ,(resp)->
            feedback.error('更新私有服务目录'+$scope.catalog.sn+'失败',resp)
          )
    ])

    .controller('PrivateCatalogViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
      $scope.catalog = $scope.cacheService.find $stateParams.sn,true
    ])

    .controller('PrivateItemViewCtrl', ['$scope', '$stateParams','PrivateItemService', '$log',
      ($scope, $stateParams, privateItemService,$log) ->
        $scope.sn = $stateParams.sn
        $scope.isn = $stateParams.isn
        if(angular.isDefined($scope.isn) && !($scope.isn is ''))
          privateItemService.get({sn:$scope.sn,isn:$scope.isn}, (data)->
            $scope.item = data
          )

    ])

    .controller('PrivateItemNewCtrl', ['$scope', '$state', '$stateParams' ,'PrivateItemService','Feedback',
      ($scope, $state, $stateParams,itemService,feedback) ->
        $scope.sn = $stateParams.sn
        $scope.item = {catalog:null};

        $scope.item.catalog = $scope.cacheService.find $stateParams.sn,true

        $scope.create = ->
          $scope.submited = true
          itemService.save({sn:$scope.sn},$scope.item,->
            feedback.success('创建服务项'+$scope.item.sn+'成功')
            $state.go('catalog.private.list')
          ,(resp)->
            feedback.error('创建服务项'+$scope.item.sn+'失败',resp)
          )
    ])

    .controller('PrivateItemEditCtrl', ['$scope', '$state', '$stateParams' ,'PrivateItemService','Feedback',
      ($scope, $state, $stateParams,itemService,feedback) ->
        $scope.sn = $stateParams.sn
        $scope.isn = $stateParams.isn
        itemService.get({sn:$scope.sn,isn:$scope.isn}, (data)->
          $scope.item = data
        )

        $scope.update = ->
          delete $scope.item.$promise;
          delete $scope.item.$resolved;
          itemService.update({sn: $scope.sn,isn:$scope.isn},$scope.item, ->
            feedback.success('更新服务项'+$scope.sn+'成功');
            $state.go('catalog.private.item.view',{sn:$scope.sn,isn:$scope.isn})
          ,(resp)->
            feedback.error('更新服务项'+$scope.sn+'失败',resp)
          )

    ])

