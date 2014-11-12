angular.module('Service.Dict', [])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'dicts',
    url: '/dicts',
    abstract: true,
    templateUrl: 'service/dicts/index.tpl.jade',
    controller: 'DictsCtrl',
    data: {pageTitle: '字典管理', default: 'dicts.list'}
  $stateProvider.state 'dicts.list',
    url: '/list',
    templateUrl: 'service/dicts/list.tpl.jade'
    controller: 'DictsListCtrl',
    data: {pageTitle: '字典列表'}
  $stateProvider.state 'dicts.new',
    url: '/new',
    templateUrl: 'service/dicts/new.tpl.jade'
    controller: 'DictsNewCtrl',
    data: {pageTitle: '新增字典'}
  $stateProvider.state 'dicts.view',
    url: '/{sn}',
    templateUrl: 'service/dicts/view.tpl.jade'
    controller: 'DictsViewCtrl',
    data: {pageTitle: '查看字典'}
  $stateProvider.state 'dicts.edit',
    url: '/{sn}/edit',
    templateUrl: 'service/dicts/edit.tpl.jade'
    controller: 'DictsEditCtrl',
    data: {pageTitle: '编辑字典'}
  $urlRouterProvider.when '/dicts', '/dicts/list'
.filter('stateFilter', () ->
  (input) ->
    return "有效" if input is "1"
    return "无效"
)
.controller('DictsCtrl', ['$scope', '$resource', '$state', 'Feedback', 'CacheService',
    ($scope,  $resource,  $state,    feedback,   CacheService) ->
      $scope.statedatas = [
        {name: "无效", value: 0},
        {name: "有效", value: 1}
      ]
      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "sn"
      $scope.services = $resource("/api/dictionaries/:sn", {sn:'@sn'},
        save: { method: 'POST',params:{sn:''}},
        update: { method: 'PUT', params: {sn: 'sn'}}
      )
      $scope.Destroy = (dict,succCallback, errCallback) ->
        $scope.services.remove {sn: dict.sn}, () ->
          feedback.success "删除时间#{dict.name}成功"
          succCallback() if succCallback
        , (resp) ->
          errCallback() if errCallback
          feedback.error("删除时间#{dict.name}失败", resp)
  ])
.controller('DictsListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService', 'Feedback',
    ($scope, $location, NgTable, ActionService, commonService, feedback) ->

      args =
        total: 0
        getData: ($defer, params)->
          $location.search params.url() # put params in url
          $scope.services.query params.url(), (datas, headers) ->
            params.total headers 'total'
            $defer.resolve $scope.dicts = datas;
            $scope.cacheService.cache datas
      $scope.selection = {checked: false, items: {}}
      $scope.dictsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "sn")
      $scope.reload = ->
        $scope.dictsTable.reload()
      $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}
      $scope.destroy = (dict)->
        $scope.Destroy dict, ->
          delete $scope.selection.items[dict.sn]
          $scope.reload()
  ])
.controller('DictsNewCtrl', ['$scope', '$state', 'Feedback',
    ($scope, $state, feedback) ->
      $scope.create = () ->
        $scope.dict.state = $scope.selectState.value;
        $scope.services.save $scope.dict, ->
          $state.go "dicts.list"
          return
  ])
.controller('DictsEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',
    ($scope,   $state,    $stateParams,   feedback) ->
      sn = $stateParams.sn
      $scope.services.get
        sn: sn
      , (data) ->
        $scope.dict = data
        if data.state is "1"
          $scope.selectState = $scope.statedatas[1]
        else
          $scope.selectState = $scope.statedatas[0]
        return
      $scope.update = () ->
        $scope.dict.state = $scope.selectState.value;
        $scope.dict.$promise = `undefined`
        $scope.dict.$resolved = `undefined`
        $scope.services.update {sn: sn}, $scope.dict, () ->
          feedback.success "修改#{$scope.dict.sn}成功"
          $state.go "dicts.list"
        , (resp) ->
          feedback.error("修改#{$scope.dict.sn}失败", resp);
  ])