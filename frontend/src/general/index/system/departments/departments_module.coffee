angular.module('System.Departments', ['multi-select'])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'departments',
    url: '/departments',
    abstract: true,
    templateUrl: 'system/departments/index.tpl.jade',
    controller: 'DepartmentsCtrl',
    data: {pageTitle: '部门管理', default: 'departments.list'}
  $stateProvider.state 'departments.list',
    url: '/list',
    templateUrl: 'system/departments/list.tpl.jade'
    controller: 'DepartmentListCtrl',
    data: {pageTitle: '部门列表'}
  $stateProvider.state 'departments.new',
    url: '/new',
    templateUrl: 'system/departments/new.tpl.jade'
    controller: 'DepartmentNewCtrl',
    data: {pageTitle: '新增部门'}
  $stateProvider.state 'departments.view',
    url: '/{sn}',
    templateUrl: 'system/departments/view.tpl.jade'
    controller: 'DepartmentViewCtrl',
    data: {pageTitle: '查看部门'}
  $stateProvider.state 'departments.edit',
    url: '/{sn}/edit',
    templateUrl: 'system/departments/edit.tpl.jade'
    controller: 'DepartmentEditCtrl',
    data: {pageTitle: '编辑部门'}
  $urlRouterProvider.when '/departments', '/departments/list'

.factory('DepartmentService', ['$resource', ($resource) ->
    $resource("/api/departments/:sn", {},
      get: { method: 'GET', params: {sn: '@sn'}},
      checkChild: { method: 'GET', params: {sn: 'check_child', id: '@id'}, isArray: true},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {sn: '@sn'}},
      query: {method: 'GET', params: {isTree: '@isTree', keyword: '@keyword'}, isArray: true},
      remove: { method: 'DELETE', params: {sn: '@sn'}}
    )
  ])

.factory('DepartmentSiteService', ['$resource', ($resource) ->
    $resource("/api/sites")
  ])

# 过滤拼接地点后的最后一个逗号
.filter('deptFilter', () ->
  (input) ->
    if input?
      names = []
      names.push site.name for site in input
      names.join()
    else '无'
)

.controller('DepartmentsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService',
    ($scope, $state, $log, feedback, CacheService) ->
      # frontend controller logic
      $log.log "Initialized the Departments controller"
      $scope.options =
        page: 1    # show first page
        count: 100 # count per page

      $scope.cacheService = new CacheService("sn")

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false

      # 表单cancel按钮
      $scope.cancel = () ->
        $state.go "departments.list"

      # 获取下拉复选框选中的用户
      selectedSiteFun = (sites) ->
        selectedSites = []
        for site in sites when sites?
          if site.ticked is true
            mySite = {}
            mySite.sn = site.sn
            selectedSites.push mySite
        return selectedSites

      # 获取下拉复选框选中的用户
      selectedParentDeptIdFun = (parentDepartments) ->
        delete parentDepartments.$promise
        delete parentDepartments.$resolved
        for parent in parentDepartments
          return parent.id if parent.ticked is true

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatData = (department, sites, parentDept) ->
        aDepartment = department
        aDepartment.sites = selectedSiteFun sites
        aDepartment.parentId = selectedParentDeptIdFun parentDept
        delete aDepartment.$promise;
        delete aDepartment.$resolved;
        return aDepartment
  ])

.controller('DepartmentListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'DepartmentService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, departmentService, feedback) ->
      $log.log "Initialized the Department list controller"

      args =
        counts: [], # hide page counts control
        total: 0,
        getData: ($defer, params) ->
          $location.search(params.url()); # put params in url
          departmentService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.departmentsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.destroy = (department) ->
        departmentService.remove department, () ->
          feedback.success "删除部门#{department.name}成功"
          delete $scope.selection.items[department.sn]
          $scope.departmentsTable.reload()
        , (resp) ->
          feedback.error("删除部门#{department.name}失败", resp)
  ])

.controller('DepartmentViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.department = $scope.cacheService.find $stateParams.sn, true
    $log.log "Initialized the Department View controller on: " + JSON.stringify($scope.department)
  ])

.controller('DepartmentNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'DepartmentService', 'DepartmentSiteService',
    ($scope, $state, $log, feedback, departmentService, siteService) ->

      $log.log "Initialized the Department New controller"
      $scope.disabled = false

      siteService.query (data) ->
        $scope.sites = data

      departmentService.query {isTree: true}, (data) ->
        $scope.parentDepartments = data

      $scope.create = () ->
        $scope.submited = true
        department = $scope.formatData($scope.department, $scope.sites, $scope.parentDepartments)
        departmentService.save department, () ->
          feedback.success "新建部门#{department.name}成功"
          $state.go "departments.list"
        , (resp) ->
          feedback.error("新建部门#{department.name}失败", resp)
  ])

.controller('DepartmentEditCtrl',
  ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'DepartmentService', 'DepartmentSiteService',
    ($scope, $state, $log, $stateParams, feedback, departmentService, siteService) ->

      $scope.department = $scope.cacheService.find $stateParams.sn, true
      $log.log "Initialized the Department Edit controller on: " + JSON.stringify($scope.department)
      $scope.disabled = true

      siteService.query (data) ->
        $scope.sites = data
        for site in $scope.sites
          for dept_site in $scope.department.sites
            if site.sn == dept_site.sn
              site.ticked = true

      departmentService.query {isTree: true}, (data) ->
        $scope.parentDepartments = data
        for parentDept, index in $scope.parentDepartments
          if parentDept.id == $scope.department.parentId
            parentDept.ticked = true
          if parentDept.id == $scope.department.id
            $scope.parentDepartments.splice(index, 1)

      # 编辑页面提交
      $scope.update = () ->
        $scope.submited = true
        department = $scope.formatData($scope.department, $scope.sites, $scope.parentDepartments)

        departmentService.update(department, department, () ->
          feedback.success "修改部门#{department.name}成功"
          $state.go "departments.list"
        , (resp) ->
          feedback.error("修改部门#{department.name}失败", resp)
        )
  ])
