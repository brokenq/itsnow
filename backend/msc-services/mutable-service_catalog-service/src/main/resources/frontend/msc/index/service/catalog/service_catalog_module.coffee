  # List catalogs
  angular.module('MscIndex.ServiceCatalog',
    ['ngTable',
     'ngResource',
     'Lib.Commons',
     'Lib.Utils',
     'dnt.action.service',
     'Lib.Feedback'])
    .config ($stateProvider, $urlRouterProvider)->
      $stateProvider.state 'catalog',
        url: '/services/catalog'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'CatalogsCtrl'
        data: {pageTitle: '服务目录', default: 'catalog.list'}
      $stateProvider.state 'catalog.list',
        url: '/list'
        templateUrl: 'service/catalog/list.tpl.jade'
        controller: 'CatalogListCtrl'
        data: {pageTitle: '服务目录列表'}
      $stateProvider.state 'catalog.new',
        url: '/{sn}/new'
        templateUrl: 'service/catalog/new_catalog.tpl.jade'
        controller: 'CatalogNewCtrl'
        data: {pageTitle: '新增服务目录'}
      $stateProvider.state 'catalog.edit',
        url: '/{sn}/edit'
        templateUrl: 'service/catalog/edit_catalog.tpl.jade'
        controller: 'CatalogEditCtrl'
        data: {pageTitle: '编辑服务目录'}
      $stateProvider.state 'catalog.view',
        url: '/{sn}/view'
        templateUrl: 'service/catalog/view_catalog.tpl.jade'
        controller: 'CatalogViewCtrl'
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.item',
        url: '/{sn}/item'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'ItemListCtrl'
        data: {pageTitle: '服务项', default: 'catalog.item.view'}
      $stateProvider.state 'catalog.item.new',
        url: '/new'
        templateUrl: 'service/catalog/new_item.tpl.jade'
        controller: 'ItemNewCtrl'
        data: {pageTitle: '新增服务项'}
      $stateProvider.state 'catalog.item.edit',
        url: '/{isn}/edit'
        templateUrl: 'service/catalog/edit_item.tpl.jade'
        controller: 'ItemEditCtrl'
        data: {pageTitle: '编辑服务项'}
      $stateProvider.state 'catalog.item.view',
        url: '/{isn}/view'
        templateUrl: 'service/catalog/view_item.tpl.jade'
        controller: 'ItemViewCtrl'
        data: {pageTitle: '查看服务项'}
      $urlRouterProvider.when '/services/catalog', '/services/catalog/list'

    .controller('CatalogsCtrl',['$scope', '$state','$resource', '$log', 'Feedback', 'CacheService',
      ($scope, $state, $resource, $log, feedback, CacheService) ->
        # frontend controller logic
        $log.log "Initialized the Catalogs controller"
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        $scope.cacheService = new CacheService("sn")
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.submited = false
        $scope.ServiceCatalog = $resource("/admin/api/public_service_catalogs/:sn", {},{update: {method: "PUT",params: {sn: '@sn'}}})
        $scope.ServiceItem = $resource("/admin/api/public_service_catalogs/:sn/items/:isn",{},{update:{method:"PUT",params: {sn: '@sn',isn:'@isn'}}})
    ])

    .controller('CatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'ActionService','CommonService','CacheService','Feedback',\
                                    ($scope, $location, $timeout, $state, NgTable,ActionService,commonService,CacheService,feedback)->
      ServiceCatalog = $scope.ServiceCatalog
      ServiceItem = $scope.ServiceItem
      args =
        total: 0,
        getData: ($defer, params) ->
          ServiceCatalog.query (data, headers) ->
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

      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)

      $scope.selection = {checked: false, items: {},types:{},parent:{}}

      $scope.getCatalogBySn  = (sn)->
        for catalog in $scope.catalogs
          return catalog if catalog.sn is sn
          for item in catalog.items
            return item if item.sn is sn

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

      $scope.destroy = (catalog)->
        if($scope.selection.types[catalog.sn] is 'catalog')
          ServiceCatalog.remove({sn: catalog.sn},->
            feedback.success "删除服务目录成功"
            delete $scope.selection.items[catalog.sn]
            $scope.tableParams.reload()
          ,(resp)->
            feedback.error "删除服务目录失败",resp
          )
        else
          ServiceItem.remove({sn:$scope.selection.parent[catalog.sn],isn: catalog.sn}, ->
            feedback.success "删除服务项成功"
            delete $scope.selection.items[catalog.sn]
            $scope.tableParams.reload()
          ,(resp)->
            feedback.error "删除服务项失败",resp
          )

      $scope.edit = (obj)->
        if $scope.selection.types[obj.sn] is 'catalog'
          $state.go('catalog.edit',{sn:obj.sn})
        else
          $state.go('catalog.item.edit',{sn:$scope.selection.parent[obj.sn],isn:obj.sn})

      $scope.view = (item)->
        $state.go('catalog.item.view',{sn:$scope.selection.parent[item.sn],isn:item.sn})

      # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
      $scope.catalog = $scope.cacheService.find $stateParams.sn,true
      $log.log "Initialized the Catalog View controller on: " + JSON.stringify($scope.catalog)
    ])

    .controller('CatalogNewCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',\
      ($scope, $state, $stateParams,feedback) ->
        ServiceCatalog = $scope.ServiceCatalog
        sn = $stateParams.sn
        $scope.catalog = {parentId:null,level:1}
        if sn? and sn isnt ''
          $scope.parentCatalog = $scope.cacheService.find $stateParams.sn,true
          $scope.catalog.parentId = $scope.parentCatalog.id
          $scope.catalog.level = $scope.parentCatalog.level + 1

        $scope.create = ->
          $scope.submited = true
          catalog = new ServiceCatalog $scope.catalog
          catalog.$save(->
            feedback.success "创建服务目录#{catalog.title}成功"
            $state.go('catalog.list')
          ,(resp)->
            feedback.error "创建服务目录#{catalog.title}失败",resp
          )
    ])

    .controller('CatalogEditCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',
      ($scope, $state, $stateParams,feedback) ->
        ServiceCatalog = $scope.ServiceCatalog
        sn = $stateParams.sn
        $scope.catalog = $scope.cacheService.find $stateParams.sn,true

        $scope.update = ->
          catalog = new ServiceCatalog $scope.catalog
          catalog.$update {sn: $scope.catalog.sn},->
            feedback.success "更新服务目录#{catalog.title}成功"
            $state.go 'catalog.view',{sn:$scope.catalog.sn}
          ,(resp)->
            feedback.error "更新服务目录#{catalog.title}失败",resp
    ])

    .controller('ItemListCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->

    ])

    .controller('ItemEditCtrl', ['$scope', '$state', '$stateParams' ,'Feedback',
      ($scope, $state, $stateParams,feedback) ->
        ServiceItem = $scope.ServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        ServiceItem.get({sn:sn,isn:isn}, (data)->
          $scope.item = data
        )

        $scope.update = ->
          delete $scope.item.$promise;
          delete $scope.item.$resolved;
          serviceItem = new ServiceItem $scope.item
          serviceItem.$update {sn: sn,isn:isn}, ->
            feedback.success "更新服务项#{serviceItem.title}成功"
            $state.go('catalog.item.view',{sn:sn,isn:isn})
          ,(resp)->
            feedback.error "更新服务项#{serviceItem.title}失败",resp

    ])

    .controller('ItemViewCtrl', ['$scope', '$stateParams', '$log',
      ($scope, $stateParams,$log) ->
        ServiceItem = $scope.ServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        if isn? and sn?
          ServiceItem.get({sn:sn,isn:isn}, (data)->
            $scope.item = data
          )
    ])

    .controller('ItemNewCtrl', ['$scope', '$state', '$stateParams','Feedback',
      ($scope, $state, $stateParams,feedback) ->
        ServiceItem = $scope.ServiceItem
        sn = $stateParams.sn
        $scope.item = {catalog:null};

        $scope.item.catalog = $scope.cacheService.find $stateParams.sn,true

        $scope.create = ->
          $scope.submited = true
          item = $scope.item
          ServiceItem.save {sn:sn},item,->
            feedback.success "创建服务项#{item.title}成功"
            $state.go('catalog.list')
          ,(resp)->
            feedback.error "创建服务项#{item.title}失败",resp

    ])