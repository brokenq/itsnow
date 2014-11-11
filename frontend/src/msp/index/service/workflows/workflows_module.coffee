angular.module('Service.Workflows', ['multi-select','angularFileUpload'])
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
    $resource("/api/msp_workflows/:sn", {},
      get: { method: 'GET', params: {sn: '@sn'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {sn: '@sn'}},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      remove: { method: 'DELETE', params: {sn: '@sn'}},
    )
  ])

.factory("ContractServiceCatalogService", ["$resource", ($resource)->
    $resource '/api/public_service_catalogs/:sn', {sn: '@sn'},
      query: {method: 'GET', params: {keyword: '@keyword'}, isArray: true}
  ])

.controller('WorkflowsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService',\
    ($scope, $state, $log, feedback, CacheService) ->
      # frontend controller logic
      $log.log "Initialized the Workflows controller"
      $scope.options =
        page: 1, # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService("sn")

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
                serviceItem.id = item.id
                return serviceItem

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatData = (workflow, dictionary, serviceCatalogs) ->
        aWorkflow = workflow
        aWorkflow.dictionary = dictionary
        aWorkflow.serviceItem = selectedServiceItem(serviceCatalogs)
        delete aWorkflow.$promise;
        delete aWorkflow.$resolved;
        return aWorkflow
  ])

.controller('WorkflowListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'CommonService', 'WorkflowService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, commonService, workflowService, feedback) ->
      $log.log "Initialized the Workflow list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          workflowService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve $scope.workflows = data

      $scope.selection = { checked: false, items: {} }
      $scope.workflowsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
      $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
      commonService.watchSelection($scope.selection, $scope.cacheService.records, "sn")

      $scope.destroy = (workflow) ->
        workflowService.remove {sn: workflow.sn}, () ->
          feedback.success "删除流程#{workflow.sn}成功"
          delete $scope.selection.items[workflow.sn]
          $scope.workflowsTable.reload()
        , (resp) ->
          feedback.error("删除流程#{workflow.sn}失败", resp)
  ])

.controller('WorkflowViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.workflow = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Workflow View controller on: " + JSON.stringify($scope.workflow)
  ])

.controller('WorkflowNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'WorkflowService', 'DictService', 'ContractServiceCatalogService', '$upload',\
    ($scope, $state, $log, feedback, workflowService, dictService, serviceCatalogService, $upload) ->

      $log.log "Initialized the Workflow New controller"

      dictService.list {code: 'inc002'}, (data) ->
        $scope.dictionaries = data

      #查询服务目录
      serviceCatalogService.query (data)->
        serviceCatalogs = []
#        closeGroup = {}
#        closeGroup.multiSelectGroup = false
        for serviceCatalog in data
#          serviceCatalog.multiSelectGroup = true
          serviceCatalog.checkboxDisabled = true
          serviceCatalogs.push serviceCatalog
          serviceCatalogs.push item for item in serviceCatalog.items when serviceCatalog.items?
#          serviceCatalogs.push closeGroup
        $scope.serviceCatalogs = serviceCatalogs

      $scope.selectedFiles = []
      $scope.onFileSelect = ($files) ->
        $scope.selectedFiles = $files

      $scope.create = ()->

        if $scope.selectedFiles.length<=0
          feedback.warn("未选择文件！")
          return
        if $scope.selectedFiles[0].name.indexOf('.bpmn20.xml') < 0
          feedback.warn("上传文件格式错误！")
          return
        if $scope.selectedFiles[0].size>1048576
          feedback.warn("上传文件大小超过最大限制(1M)！")
          return

        upload = $upload.upload({
          url: '/api/msp_workflows/upload'
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

.controller('WorkflowEditCtrl', ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'WorkflowService', 'DictService', 'ContractServiceCatalogService',\
    ($scope, $state, $log, $stateParams, feedback, workflowService, dictService, serviceCatalogService) ->

      $scope.workflow = $scope.cacheService.find $stateParams.sn, true
      $log.log "Initialized the Workflow Edit controller on: " + JSON.stringify($scope.workflow)

      workflowService.get {sn: $scope.workflow.sn}, (data) ->
        $scope.workflow = data;

        #加载数据字典
        dictService.list {code: 'inc002'}, (data) ->
          $scope.dictionaries = data
          for dictionary in $scope.dictionaries
            for selectedDict in $scope.workflow.dictionary
              if dictionary.sn == selectedDict.sn
                dictionary.ticked = true

        #加载服务目录
        serviceCatalogService.query (data)->
          serviceCatalogs = []
          closeGroup = {}
          for serviceCatalog in data
            serviceCatalog.multiSelectGroup = true
            serviceCatalogs.push serviceCatalog
            if serviceCatalog.items?
              for item in serviceCatalog.items
                item.ticked = true for selectedServiceItem in $scope.workflow.serviceItem when item.sn == selectedServiceItem.sn
                serviceCatalogs.push item
            closeGroup.multiSelectGroup = false
            serviceCatalogs.push closeGroup
          $scope.serviceCatalogs = serviceCatalogs

      # 编辑页面提交
      $scope.update = () ->
        $scope.submited = true
        workflow = $scope.formatData($scope.workflow, $scope.users)
        workflowService.update {sn: workflow.sn}, workflow, () ->
          feedback.success "修改流程#{workflow.sn}成功"
          $state.go "workflows.list"
        , (resp) ->
          feedback.error("修改流程#{workflow.sn}失败", resp);
  ])
