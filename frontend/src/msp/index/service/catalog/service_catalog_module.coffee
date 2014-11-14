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
        url: '/services/catalog'
        templateUrl: 'service/catalog/index.tpl.jade'
        abstract:true
        controller: 'CatalogCtrl'
        data: {pageTitle: '服务目录', default: 'catalog.list'}
      $stateProvider.state 'catalog.list',
        url: '/list'
        templateUrl: 'service/catalog/list.tpl.jade'
        controller: 'CatalogListCtrl'
        data: {pageTitle: '服务目录列表'}
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

    .controller('CatalogCtrl',['$scope', '$state','$resource', '$log', 'Feedback',
      ($scope, $state,$resource, $log, feedback) ->
        # frontend controller logic
        $log.log "Initialized the Catalog controller"
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.submited = false
        $scope.ServiceCatalog = $resource("/api/public_service_catalogs/:sn", {})
        $scope.ServiceItem = $resource("/api/public_service_catalogs/:sn/items/:isn",{})
        $scope.AccountServiceItem = $resource("/api/public_service_catalogs/accounts/items/:isn",{isn: '@isn'})
    ])

    .controller('CatalogListCtrl',['$scope', '$location', '$timeout', '$state','ngTableParams', 'ActionService','Feedback', \
                                      ($scope, $location, $timeout, $state, NgTable, ActionService,feedback)->
      ServiceCatalog = $scope.ServiceCatalog
      ServiceItem = $scope.ServiceItem
      AccountServiceItem = $scope.AccountServiceItem
      args =
        total: 0,
        getData: ($defer, params) ->
          ServiceCatalog.query(params.url(), (data, headers) ->
            $timeout(->
              $defer.resolve($scope.catalogs = data)

              #initial selected item = true
              AccountServiceItem.query((data)->
                $scope.serviceItems = data
                angular.forEach $scope.serviceItems,(serviceItem)->
                  angular.forEach $scope.catalogs, (item)->
                    angular.forEach item.items,(child)->
                      $scope.selection.items[child.sn] = true if child.sn is serviceItem.sn
                      $scope.selection.parent[child.sn] = item.sn if child.sn?
              )
            , 500)
          )
      $scope.tableParams = new NgTable(angular.extend($scope.options, $location.search()), args)

      $scope.selection = {checked: false, items: {},parent:{}}

      $scope.getCatalogBySn  = (sn)->
        for catalog in $scope.catalogs
          for item in catalog.items
            return item if item.sn is sn

      $scope.update = ->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            if $scope.selection.items[child.sn]
              AccountServiceItem.save({isn:child.sn},->
                feedback.success("设置公共服务目录项"+child.sn+"成功")
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

      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.getCatalogBySn})

      # watch for check all checkbox
      $scope.$watch 'selection.checked', (value)->
        angular.forEach $scope.catalogs, (item)->
          angular.forEach item.items,(child)->
            $scope.selection.items[child.sn] = value if angular.isDefined(child.sn)

    ])

    .controller('CatalogViewCtrl', ['$scope', '$stateParams', '$log',\
        ($scope, $stateParams, $log) ->
      ServiceCatalog = $scope.ServiceCatalog
      sn = $stateParams.sn
      if sn? and sn isnt ''
        ServiceCatalog.get({sn:sn}, (data)->
          $scope.catalog = data
        )
    ])

    .controller('ItemViewCtrl', ['$scope', '$stateParams', '$log',
      ($scope, $stateParams, $log) ->
        ServiceItem = $scope.ServiceItem
        sn = $stateParams.sn
        isn = $stateParams.isn
        if isn? and isn isnt '' and sn? and sn isnt ''
          ServiceItem.get({sn:sn,isn:isn}, (data)->
            $scope.item = data
          )

    ])

