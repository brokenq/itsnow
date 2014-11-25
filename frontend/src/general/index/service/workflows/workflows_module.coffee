angular.module('Service.Workflows', ['multi-select','angularFileUpload','jcs-autoValidate'])

.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'workflows',
    url: '/workflows',
    abstract: true,
    templateUrl: 'service/workflows/index.tpl.jade',
    controller: 'WorkflowsCtrl',
    data: {pageTitle: '流程管理', default: 'workflows.list'}
  $stateProvider.state 'workflows.list',
    url: '/list',
    templateUrl: 'service/workflows/list.tpl.jade'
    controller: 'WorkflowListCtrl',
    data: {pageTitle: '流程列表'}
  $stateProvider.state 'workflows.new',
    url: '/new',
    templateUrl: 'service/workflows/new.tpl.jade'
    controller: 'WorkflowNewCtrl',
    data: {pageTitle: '新增流程'}
  $stateProvider.state 'workflows.view',
    url: '/{sn}',
    templateUrl: 'service/workflows/view.tpl.jade'
    controller: 'WorkflowViewCtrl',
    data: {pageTitle: '查看流程'}
  $stateProvider.state 'workflows.edit',
    url: '/{sn}/edit',
    templateUrl: 'service/workflows/edit.tpl.jade'
    controller: 'WorkflowEditCtrl',
    data: {pageTitle: '编辑流程'}
  $urlRouterProvider.when '/workflows', '/workflows/list'

.factory('WorkflowService', ['$resource', ($resource) ->
    $resource("/api/workflows/:sn", {},
      get: { method: 'GET', params: {sn: '@sn'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {sn: '@sn'}},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      remove: { method: 'DELETE', params: {sn: '@sn'}},
    )
  ])

.factory("WorkflowServiceCatalogService", ["$resource", ($resource)->
    $resource '/api/public_service_catalogs'
  ])

.factory("WorkflowDictService", ["$resource", ($resource)->
    $resource '/api/dictionaries/:code', {code: '@code'}
  ])

.controller('WorkflowsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'WorkflowService',\
    ($scope, $state, $log, feedback, CacheService,workflowService) ->
      # frontend controller logic
      $log.log "Initialized the Workflows controller"
      $scope.options =
        page: 1   # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "sn", (value)->
        workflowService.get {sn: value}

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false

      # 表单cancel按钮
      $scope.cancel = () ->
        $state.go "workflows.list"

      # 获取下拉框选中的服务目录
      selectedServiceItem = (serviceCatalogs) ->
        serviceItem = {}
        for serviceCatalog in serviceCatalogs
          if serviceCatalog.items?
            for item in serviceCatalog.items
              if item.ticked is true
                serviceItem.sn = item.sn
                return serviceItem

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatData = (workflow, dictionary, serviceCatalogs) ->
        aWorkflow = workflow
        aWorkflow.dictionary = dictionary
        aWorkflow.serviceItem = selectedServiceItem(serviceCatalogs)
        aWorkflow.serviceItemType = '0'
        delete aWorkflow.$promise;
        delete aWorkflow.$resolved;
        return aWorkflow
  ])

.controller('WorkflowListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'WorkflowService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, workflowService, feedback) ->
      $log.log "Initialized the Workflow list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          workflowService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve $scope.workflows = data

      $scope.workflowsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.destroy = (workflow) ->
        workflowService.remove workflow, () ->
          feedback.success "删除流程#{workflow.sn}成功"
          delete $scope.selectionService.items[workflow.sn]
          $scope.workflowsTable.reload()
        , (resp) ->
          feedback.error("删除流程#{workflow.sn}失败", resp)
  ])

.controller('WorkflowViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.workflow = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Workflow View controller on: " + JSON.stringify($scope.workflow)
  ])

.controller('WorkflowNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'WorkflowService', 'WorkflowDictService', 'WorkflowServiceCatalogService', '$upload',\
    ($scope, $state, $log, feedback, workflowService, dictService, serviceCatalogService, $upload) ->

      $log.log "Initialized the Workflow New controller"
      $scope.disabled = false

      dictService.get {code: '002'}, (data) ->
        $scope.dictionaries = data.details

      #查询服务目录
      serviceCatalogService.query (data)->
        serviceCatalogs = []
        for serviceCatalog in data
          serviceCatalog.checkboxDisabled = true
          serviceCatalogs.push serviceCatalog
          serviceCatalogs.push item for item in serviceCatalog.items when serviceCatalog.items?
        $scope.serviceCatalogs = serviceCatalogs

      $scope.selectedFiles = []
      $scope.onFileSelect = ($files) ->
        $scope.selectedFiles = $files

      $scope.create = ()->

        upload = $upload.upload({
          url: '/api/workflows/upload'
          method: "POST"
          file: $scope.selectedFiles[0]
        })

        upload.then () ->
          $scope.submited = true
          workflow = $scope.formatData($scope.workflow, $scope.dictionary, $scope.serviceCatalogs)
          workflowService.save workflow, () ->
            feedback.success "新建流程#{workflow.name}成功"
            $state.go "workflows.list"
          , (resp) ->
            feedback.error("新建流程#{workflow.name}失败", resp)
        , (resp) ->
          feedback.error("文件上传失败，请重新创建", resp)
  ])

.controller('WorkflowEditCtrl', ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'WorkflowService', 'WorkflowDictService', 'WorkflowServiceCatalogService',\
    ($scope, $state, $log, $stateParams, feedback, workflowService, dictService, serviceCatalogService) ->

      $scope.workflow = $scope.cacheService.find $stateParams.sn, true
      $log.log "Initialized the Workflow Edit controller on: " + JSON.stringify($scope.workflow)
      $scope.disabled = true

      workflowService.get $scope.workflow, (data) ->
        $scope.workflow = data;

        #加载数据字典
        dictService.get {code: '002'}, (data) ->
          $scope.dictionaries = data.details
          return $scope.dictionary = dict for dict in $scope.dictionaries when dict.sn == $scope.workflow.dictionary.sn

        #加载服务目录
        serviceCatalogService.query (data)->
          serviceCatalogs = []
          for serviceCatalog in data
            serviceCatalog.checkboxDisabled = true
            serviceCatalogs.push serviceCatalog
            if serviceCatalog.items?
              for item in serviceCatalog.items
                if item.sn == $scope.workflow.serviceItem.sn
                  item.ticked = true
                serviceCatalogs.push item
          $scope.serviceCatalogs = serviceCatalogs

      # 编辑页面提交
      $scope.update = () ->
        $scope.submited = true
        workflow = $scope.formatData($scope.workflow, $scope.dictionary, $scope.serviceCatalogs)
        workflowService.update workflow, workflow, () ->
          feedback.success "修改流程#{workflow.name}成功"
          $state.go "workflows.list"
        , (resp) ->
          feedback.error("修改流程#{workflow.name}失败", resp);
  ])
