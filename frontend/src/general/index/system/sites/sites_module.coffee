angular.module('System.Sites', ['multi-select'])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'sites',
    url: '/sites',
    abstract: true,
    templateUrl: 'system/sites/index.tpl.jade',
    controller: 'SitesCtrl',
    data: {pageTitle: '地点管理', default: 'sites.list'}
  $stateProvider.state 'sites.list',
    url: '/list',
    templateUrl: 'system/sites/list.tpl.jade'
    controller: 'SiteListCtrl',
    data: {pageTitle: '地点列表'}
  $stateProvider.state 'sites.new',
    url: '/new',
    templateUrl: 'system/sites/new.tpl.jade'
    controller: 'SiteNewCtrl',
    data: {pageTitle: '新增地点'}
  $stateProvider.state 'sites.view',
    url: '/{sn}',
    templateUrl: 'system/sites/view.tpl.jade'
    controller: 'SiteViewCtrl',
    data: {pageTitle: '查看地点'}
  $stateProvider.state 'sites.edit',
    url: '/{sn}/edit',
    templateUrl: 'system/sites/edit.tpl.jade'
    controller: 'SiteEditCtrl',
    data: {pageTitle: '编辑地点'}
  $urlRouterProvider.when '/sites', '/sites/list'

.factory('SiteService', ['$resource', ($resource) ->
    $resource("/api/sites/:sn", {},
      get: { method: 'GET', params: {sn: '@sn'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {sn: '@sn'}},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      remove: { method: 'DELETE', params: {sn: '@sn'}},
      getWorkTimes: { method: 'GET', params: {name: 'workTime'}, isArray: true}
      getUsers: { method: 'GET', params: {sn: 'users'}, isArray: true}
    )
  ])

.factory('SiteWorkTimeService', ['$resource', ($resource) ->
    $resource("/api/work_times")
  ])

.factory("SiteDictService", ["$resource", ($resource)->
    $resource '/api/dictionaries/:code', {code: '@code'}
  ])

.controller('SitesCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'SiteService',\
    ($scope, $state, $log, feedback, CacheService, siteService) ->
      # frontend controller logic
      $log.log "Initialized the Sites controller"
      $scope.options =
        page: 1   # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        siteService.get {sn: value}

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false

      # 表单cancel按钮
      $scope.cancel = () ->
        $state.go "sites.list"

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatData = (site, dictionary, workTime) ->
        aSite = site
        aSite.dictionary = dictionary
        aSite.workTime = workTime
        delete aSite.$promise
        delete aSite.$resolved
        return aSite
  ])

.controller('SiteListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'SiteService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, siteService, feedback) ->
      $log.log "Initialized the Site list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          siteService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.sitesTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.destroy = (site) ->
        siteService.remove {sn: site.sn}, () ->
          feedback.success "删除地点#{site.name}成功"
          delete $scope.selectionService.items[site.sn]
          $scope.sitesTable.reload()
        , (resp) ->
          feedback.error("删除地点#{site.name}失败", resp)
  ])

.controller('SiteViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.site = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Site View controller on: " + JSON.stringify($scope.site)
  ])

.controller('SiteNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'SiteService', 'SiteDictService', 'SiteWorkTimeService',
    ($scope, $state, $log, feedback, siteService, dictService, workTimeService) ->

      $log.log "Initialized the Site New controller"
      $scope.disabled = false

      dictService.get {code: '001'}, (data) ->
        $scope.dictionaries = data.details

      workTimeService.query (data) ->
        $scope.workTimes = data;

      $scope.create = () ->
        $scope.submited = true
        site = $scope.formatData($scope.site, $scope.dictionary, $scope.workTime)
        siteService.save site, () ->
          feedback.success "新建地点#{site.name}成功"
          $state.go "sites.list"
        , (resp) ->
          feedback.error("新建地点#{site.name}失败", resp)
  ])

.controller('SiteEditCtrl',
  ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'SiteService', 'SiteDictService', 'SiteWorkTimeService',
    ($scope, $state, $log, $stateParams, feedback, siteService, dictService, workTimeService) ->

      $scope.site = $scope.cacheService.find $stateParams.sn, true
      $log.log "Initialized the Site Edit controller on: " + JSON.stringify($scope.site)
      $scope.disabled = true

      promise = siteService.get({sn: $scope.site.sn}).$promise
      promise.then (data) ->
        $scope.site = data
        dictService.get {code: '001'}, (data) ->
          $scope.dictionaries = data.details
          for dictionary in $scope.dictionaries
            if dictionary.sn == $scope.site.dictionary.sn
              $scope.dictionary = dictionary
        workTimeService.query (data) ->
          $scope.workTimes = data
          $scope.workTime = $scope.site.workTime
          for workTime in $scope.workTimes
            if workTime.sn == $scope.site.workTime.sn
              $scope.workTime = workTime

      # 编辑页面提交
      $scope.update = () ->
        $scope.submited = true
        site = $scope.formatData($scope.site, $scope.dictionary, $scope.workTime)
        siteService.update {sn: site.sn}, site, () ->
          feedback.success "修改地点#{site.name}成功"
          $state.go "sites.list"
        , (resp) ->
          feedback.error("修改地点#{site.name}失败", resp);
  ])
