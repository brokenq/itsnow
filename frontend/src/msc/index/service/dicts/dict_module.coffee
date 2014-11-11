angular.module('MscIndex.Dict', [])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'dicts',
    url: '/dicts',
    abstract: true,
    templateUrl: 'service/dicts/index.tpl.jade',
    controller: 'DictCtrl',
    data: {pageTitle: '字典管理', default: 'dicts.list'}
  $stateProvider.state 'dicts.list',
    url: '/list',
    templateUrl: 'service/dicts/list.tpl.jade'
    controller: 'DictListCtrl',
    data: {pageTitle: '字典列表'}
  $stateProvider.state 'dicts.new',
    url: '/new',
    templateUrl: 'service/dicts/new.tpl.jade'
    controller: 'DictNewCtrl',
    data: {pageTitle: '新增字典'}
  $stateProvider.state 'dicts.view',
    url: '/{sn}',
    templateUrl: 'service/dicts/view.tpl.jade'
    controller: 'DictViewCtrl',
    data: {pageTitle: '查看字典'}
  $stateProvider.state 'dicts.edit',
    url: '/{sn}/edit',
    templateUrl: 'service/dicts/edit.tpl.jade'
    controller: 'DictEditCtrl',
    data: {pageTitle: '编辑字典'}
  $urlRouterProvider.when '/dicts', '/dicts/list'
.filter "stateFilter", ->
  stateFilter = (input) ->
    if input is "1"
      "有效"
    else
      "无效"
  stateFilter
.controller('DictCtrl', ['$scope', '$resource', '$state', 'Feedback', 'CacheService',\
                          ($scope,  $resource,  $state,    feedback,   CacheService) ->
      $scope.statedatas = [
        {name: "无效", value: 0},
        {name: "有效", value: 1}
      ]
      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "sn"
      $scope.services = $resource("/api/dictionaries/:sn", {},
        get: { method: 'GET', params: {sn: '@sn'}},
        save: { method: 'POST'},
        update: { method: 'PUT', params: {sn: '@sn'}},
        query: { method: 'GET', isArray: true},
        remove: { method: 'DELETE', params: {sn: '@sn'}}
      )
      $scope.dictdates = {}
      $scope.services.query (dictdates) ->
        $scope.dictdates = dictdates
        return

  ])
.controller('DictListCtrl', ['$scope', '$location', 'ngTableParams', 'ActionService', 'CommonService', 'Feedback',\
                            ($scope, $location, NgTable, ActionService, commonService, feedback) ->
      Dicts = $scope.services
      args =
        total: 0
        getData: ($defer, params)->
          $location.search params.url() # put params in url
          Dicts.query params.url(), (datas, headers) ->
            params.total headers 'total'
            $defer.resolve $scope.dicts = datas;
            $scope.cacheService.cache datas
      $scope.selection = {checked: false, items: {}}
      $scope.dictsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "sn")
      $scope.actionService = new ActionService {watch: $scope.selection.items, mapping: $scope.cacheService.find}
      $scope.destroy = (dict) ->
        $scope.services.remove {sn: dict.sn}, () ->
          feedback.success "删除时间#{dict.sn}成功"
          delete $scope.selection.items[dict.sn]
          $scope.dictsTable.reload()
        , (resp) ->
          feedback.error("删除时间#{dict.sn}失败", resp)
      $scope.reload = ->
        $scope.dictsTable.reload()

  ])
.controller('DictNewCtrl', ['$scope', '$state', 'Feedback',\
    ($scope, $state, feedback) ->
      $scope.autoComeplete = () ->
        for dictdate in $scope.dictdates
          if($scope.dict.code is dictdate.code)
            $scope.dict.name = dictdate.name
            $scope.ngread = true
            break
          else
            $scope.dict.name = ""
            $scope.ngread = false
      $scope.autoValidate=() ->
        for dictdate in $scope.dictdates
          if($scope.dict.code is dictdate.code)
            if($scope.dict.display is dictdate.display)
              $scope.display=""
              feedback.warn("显示名已存在请重新输入！！")
      $scope.create = () ->
        $scope.dict.state = $scope.selectState.value;
        $scope.dict.$promise = `undefined`
        $scope.dict.$resolved = `undefined`
        $scope.services.save $scope.dict, ->
          $state.go "dicts.list"
          return
  ])
.controller('DictEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',\
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