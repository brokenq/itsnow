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
        data: {pageTitle: '服务目录', default: 'catalog.list'}
      $stateProvider.state 'catalog.list',
        url: '/list',
        templateUrl: 'service/catalog/list.tpl.jade'
        controller: 'CatalogListCtrl',
        data: {pageTitle: '服务目录列表'}
      $stateProvider.state 'catalog.view',
        url: '/{sn}/view',
        templateUrl: 'service/catalog/view_catalog.tpl.jade'
        controller: 'CatalogViewCtrl',
        data: {pageTitle: '查看服务目录'}
      $stateProvider.state 'catalog.item',
        url: '/{sn}/item/{isn}/view',
        templateUrl: 'service/catalog/view_item.tpl.jade',
        controller: 'ItemViewCtrl',
        data: {pageTitle: '查看服务项'}
      $urlRouterProvider.when '/services/catalog', '/services/catalog/list'

    .factory('CatalogService', ['$resource', ($resource) ->
      $resource("/api/public_service_catalogs/:sn",{},
        get: { method: 'GET', params: {sn: '@sn'}},
        query: { method: 'GET', params: {sn: '@sn'}, isArray: true},
      )
    ])

    .factory('ItemService', ['$resource', ($resource) ->
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

    .filter 'formatTime', () ->
      (time) ->
        date = new Date(time)
        date.toLocaleString()

    .controller('CatalogCtrl',['$scope', '$state', '$log', 'Feedback',
      ($scope, $state, $log, feedback) ->
        # frontend controller logic
        $log.log "Initialized the Catalog controller"
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.submited = false

    ])
    .controller('CatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'CatalogService','ServiceItemService','PublicServiceItemService', 'ActionService','Feedback', \
                                      ($scope, $location, $timeout, $state, ngTableParams, catalogService,serviceItemService,publicServiceItemService,ActionService,feedback)->
      args =
        total: 0,
        getData: ($defer, params) ->
          #$location.search(params.url()) # put params in url
          catalogService.query(params.url(), (data, headers) ->
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

    .controller('CatalogViewCtrl', ['$scope', '$stateParams','CatalogService', '$log',\
        ($scope, $stateParams,catalogService, $log) ->
      console.warn('CatalogViewCtrl')
      $scope.sn = $stateParams.sn
      if(angular.isDefined($scope.sn) && !($scope.sn is ''))
        catalogService.get({sn:$scope.sn}, (data)->
          $scope.catalog = data
        )
    ])

    .controller('ItemViewCtrl', ['$scope', '$stateParams','ItemService', '$log',
      ($scope, $stateParams, itemService,$log) ->
        $scope.sn = $stateParams.sn
        $scope.isn = $stateParams.isn
        if(angular.isDefined($scope.isn) && !($scope.isn is ''))
          itemService.get({sn:$scope.sn,isn:$scope.isn}, (data)->
            $scope.item = data
          )

    ])

