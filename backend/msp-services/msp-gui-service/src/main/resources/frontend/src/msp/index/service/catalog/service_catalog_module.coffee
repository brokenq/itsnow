# List catalogs
angular.module('Service.Catalog', [])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'catalog',
    url: '/services/catalog'
    templateUrl: 'service/catalog/index.tpl.jade'
    abstract: true
    controller: 'CatalogCtrl'
    data: {pageTitle: '服务目录', default: 'catalog.list'}
  $stateProvider.state 'catalog.list',
    url: '/list'
    templateUrl: 'service/catalog/list.tpl.jade'
    controller: 'CatalogListCtrl'
    data: {pageTitle: '服务目录列表'}
  $stateProvider.state 'catalog.new',
    url: '/{sn}/newcatalog'
    templateUrl: 'service/catalog/new_catalog.tpl.jade'
    controller: 'CatalogNewCtrl'
    data: {pageTitle: '新增服务目录'}
  $stateProvider.state 'catalog.newitem',
    url: '/{sn}/newitem'
    templateUrl: 'service/catalog/new_item.tpl.jade'
    controller: 'CatalogItemNewCtrl'
    data: {pageTitle: '新增服务项'}
  $stateProvider.state 'catalog.view',
    url: '/{sn}/view'
    templateUrl: 'service/catalog/view_catalog.tpl.jade'
    controller: 'CatalogViewCtrl'
    data: {pageTitle: '查看服务目录'}
  $stateProvider.state 'catalog.item',
    url: '/{sn}/item/{isn}/view'
    templateUrl: 'service/catalog/view_item.tpl.jade'
    controller: 'ItemViewCtrl'
    data: {pageTitle: '查看服务项'}
  $urlRouterProvider.when '/services/catalog', '/services/catalog/list'
.controller('CatalogCtrl', ['$scope', '$state', '$resource', '$log', 'Feedback',
    ($scope, $state, $resource, $log, feedback) ->
      # frontend controller logic
      $log.log "Initialized the Catalog controller"
      $scope.options =
        page: 1, # show first page
        count: 1000 # count per page
      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可
      $scope.submited = false
      $scope.SC = $resource("/api/public_service_catalogs/:sn/catalogs", {})
      $scope.ServiceItem = $resource("/api/public_service_catalogs/:sn/items/:isn",{})
      $scope.ServiceCatalog = $resource("/api/public_service_catalogs/:sn", {})
      $scope.AccountServiceCatalogAddRemove = $resource("/api/public_service_catalogs/accounts/catalogs/:sn",{sn:'@sn'})
      $scope.AccountServiceCatalog = $resource("/api/public_service_catalogs/accounts")
      $scope.AccountServiceItem = $resource("/api/public_service_catalogs/accounts/items/:isn", {isn: '@isn'})
  ])
.controller('CatalogListCtrl',
  ['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'ActionService', 'Feedback',
    ($scope, $location, $timeout, $state, NgTable, ActionService, feedback)->
      AccountServiceCatalog = $scope.AccountServiceCatalog
      args =
        counts: [], # hide page counts control
        total: 0,
        getData: ($defer, params) ->
          AccountServiceCatalog .query(params.url(), (data, headers) ->
            $timeout(->
              $defer.resolve($scope.catalogs = data)

#              #initial selected item = true
#              AccountServiceItem.query((data)->
#                $scope.serviceItems = data
#                angular.forEach $scope.serviceItems, (serviceItem)->
#                  angular.forEach $scope.catalogs, (item)->
#                    angular.forEach item.items, (child)->
#                      $scope.selection.items[child.sn] = true if child.sn is serviceItem.sn
#                      $scope.selection.parent[child.sn] = item.sn if child.sn?
#              )
            , 500)
          )
      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selection = {checked: false, items: {}, parent: {}}
      $scope.getCatalogBySn = (sn)->
        for catalog in $scope.catalogs
          return catalog if catalog.sn is sn
      $scope.delete = ->
        angular.forEach $scope.catalogs, (catalog)->
          angular.forEach catalog.items, (item)->
            if $scope.selection.items[item.sn]
              $scope.AccountServiceItem.remove({isn: item.sn}, ->
                $scope.tableParams.reload()
                feedback.success("删除公共服务目录项" + item.title + "成功")
              , (resp)->
                feedback.error("删除公共服务目录项" + item.title + "失败", resp)
              )
          if $scope.selection.items[catalog.sn]
            $scope.AccountServiceCatalogAddRemove .remove({sn: catalog.sn}, ->
              $scope.tableParams.reload()
              feedback.success("删除公共服务目录" + catalog.title+ "成功")
            , (resp)->
              feedback.error("删除公共服务目录" + catalog.title+ "失败", resp)
            )
        feedback.success("删除公共服务目录完成！")


      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (catalog)->
          $scope.selection.items[catalog.sn] = value if angular.isDefined(catalog.sn)
          angular.forEach catalog.items, (item)->
            $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
  ])

.controller('CatalogViewCtrl', ['$scope', '$stateParams', '$log',
    ($scope, $stateParams, $log) ->
      ServiceCatalog = $scope.ServiceCatalog
      sn = $stateParams.sn
      if sn? and sn isnt ''
        ServiceCatalog.get({sn: sn}, (data)->
          $scope.catalog = data
        )
  ])

.controller('ItemViewCtrl', ['$scope', '$stateParams', '$log',
    ($scope, $stateParams, $log) ->
      ServiceItem = $scope.ServiceItem
      sn = $stateParams.sn
      isn = $stateParams.isn
      if isn? and isn isnt '' and sn? and sn isnt ''
        ServiceItem.get({sn: sn, isn: isn}, (data)->
          $scope.item = data
        )

  ])
.controller('CatalogNewCtrl',
  ['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'ActionService', 'Feedback', '$stateParams',
    ($scope, $location, $timeout, $state, NgTable, ActionService, feedback, $stateParams)->
      sn = $stateParams.sn
      ServiceCatalog = $scope.ServiceCatalog
      args =
        counts: [], # hide page counts control
        total: 0,
        getData: ($defer, params) ->
          if(sn=="")
            ServiceCatalog.query(params.url(), (data, headers) ->
              $timeout(->
                $defer.resolve($scope.catalogs = data)
              , 500)
            )
          else
            $scope.SC.get({sn: sn}, (data)->
              $timeout(->
                $defer.resolve($scope.catalogs = data.children)
              ,500)
            )
      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selection = {checked: false, items: {}, parent: {}}
      $scope.getCatalogBySn  = (sn)->
        for catalog in $scope.catalogs
          return catalog if catalog.sn is sn
          for item in catalog.items
            return item if item.sn is sn
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})
      $scope.update = ->
        angular.forEach $scope.catalogs, (catalog)->
          angular.forEach catalog.items, (item)->
            if $scope.selection.items[item.sn]
              $scope.AccountServiceItem.save({isn: item.sn}, ->
                $state.go "catalog.list"
                feedback.success("设置公共服务目录项" + item.title + "成功")

              , (resp)->
                feedback.error("设置公共服务目录项" + item.title + "失败", resp)
              )
          if $scope.selection.items[catalog.sn]
            $scope.AccountServiceCatalogAddRemove .save({sn: catalog.sn}, ->
              $state.go "catalog.list"
              feedback.success("设置公共服务目录" + catalog.title+ "成功")
            , (resp)->
              feedback.error("设置公共服务目录" + catalog.title+ "失败", resp)
            )

        feedback.success("设置公共服务目录完成！")

      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (catalog)->
          $scope.selection.items[catalog.sn] = value if angular.isDefined(catalog.sn)
          angular.forEach catalog.items, (item)->
            $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
  ])
.controller('CatalogItemNewCtrl',
  ['$scope', '$location', '$timeout', '$state', 'ngTableParams', 'ActionService', 'Feedback', '$stateParams',
    ($scope, $location, $timeout, $state, NgTable, ActionService, feedback, $stateParams)->
      sn = $stateParams.sn
      ServiceCatalog = $scope.ServiceCatalog
      args =
        counts: [], # hide page counts control
        total: 0,
        getData: ($defer, params) ->
          if(sn=="")
            ServiceCatalog.query(params.url(), (data, headers) ->
              $timeout(->
                $defer.resolve($scope.catalogs = data)
              , 500)
            )
          else
            $scope.SC.get({sn: sn}, (data)->
              $timeout(->
                $defer.resolve($scope.catalogs = data.children)
              ,500)
            )
      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selection = {checked: false, items: {}, parent: {}}
      $scope.getCatalogBySn = (sn)->
        for catalog in $scope.catalogs
          return catalog if catalog.sn is sn
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})
      $scope.update = ->
        angular.forEach $scope.catalogs, (catalog)->
          angular.forEach catalog.items, (item)->
            if $scope.selection.items[item.sn]
              $scope.AccountServiceItem.save({isn: item.sn}, ->
                feedback.success("设置公共服务目录项" + item.title + "成功")
                $state.go "catalog.list"
              , (resp)->
                feedback.error("设置公共服务目录项" + item.title + "失败", resp)
              )

      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (catalog)->
          angular.forEach catalog.items, (item)->
            $scope.selection.items[item.sn] = value if angular.isDefined(item.sn)
  ])