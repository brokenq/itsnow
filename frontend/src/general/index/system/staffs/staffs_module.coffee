angular.module('System.Staffs', ['multi-select'])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'staffs',
    url: '/staffs',
    abstract: true,
    templateUrl: 'system/staffs/index.tpl.jade',
    controller: 'StaffsCtrl',
    data: {pageTitle: '员工管理', default: 'staffs.list'}
  $stateProvider.state 'staffs.list',
    url: '/list',
    templateUrl: 'system/staffs/list.tpl.jade'
    controller: 'StaffListCtrl',
    data: {pageTitle: '员工列表'}
  $stateProvider.state 'staffs.new',
    url: '/new',
    templateUrl: 'system/staffs/new.tpl.jade'
    controller: 'StaffNewCtrl',
    data: {pageTitle: '新增员工'}
  $stateProvider.state 'staffs.view',
    url: '/{no}',
    templateUrl: 'system/staffs/view.tpl.jade'
    controller: 'StaffViewCtrl',
    data: {pageTitle: '查看员工'}
  $stateProvider.state 'staffs.edit',
    url: '/{no}/edit',
    templateUrl: 'system/staffs/edit.tpl.jade'
    controller: 'StaffEditCtrl',
    data: {pageTitle: '编辑员工'}
  $urlRouterProvider.when '/staffs', '/staffs/list'

.factory('StaffService', ['$resource', ($resource) ->
    $resource("/api/staffs/:no", {},
      query: { method: 'GET', params: {keyword: '@keyword'}, isArray: true},
      get: { method: 'GET', params: {no: '@no'}},
      save: { method: 'POST'},
      update: { method: 'PUT', params: {no: '@no'}},
      remove: { method: 'DELETE', params: {no: '@no'}}
    )
  ])

.factory('StaffSiteService', ['$resource', ($resource) ->
    $resource("/api/sites")
  ])

.factory('StaffDepartmentService', ['$resource', ($resource) ->
    $resource("/api/departments")
  ])

.filter('staffStatusFilter', () ->
  (input) ->
    if input is 'Normal'
      "在职"
    else if input is 'Quit'
      "离职"
    else
      "无"
)

.controller('StaffsCtrl', ['$scope', '$state', '$log', 'Feedback', 'CacheService', 'StaffService',\
    ($scope, $state, $log, feedback, CacheService, staffService) ->
      # frontend controller logic
      $log.log "Initialized the Staffs controller"
      $scope.options =
        page: 1, # show first page
        count: 10 # count per page

      $scope.cacheService = new CacheService "no", (value)->
        staffService.get {no: value}

      # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
      $scope.submited = false

      # 表单cancel按钮
      $scope.cancel = () ->
        $state.go "staffs.list"

      # 去除不必要的对象属性，用于HTTP提交
      $scope.formatData = (staff, site, department, staff_user) ->
        aStaff = staff
        aStaff.status = staff.status.id
        aStaff.site = site
        aStaff.department = department
        staff.user = staff_user
        delete aStaff.$promise
        delete aStaff.$resolved
        return aStaff
  ])

.controller('StaffListCtrl',
  ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'SelectionService', 'StaffService', 'Feedback',
    ($scope, $location, $log, NgTable, ActionService, SelectionService, staffService, feedback) ->
      $log.log "Initialized the Staff list controller"

      args =
        total: 0,
        getData: ($defer, params) ->
          $location.search params.url() # put params in url
          staffService.query params.url(), (data, headers) ->
            params.total headers('total')
            $scope.cacheService.cache data
            $defer.resolve data

      $scope.staffsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
      $scope.selectionService = new SelectionService($scope.cacheService.records, "no")
      $scope.actionService = new ActionService({watch: $scope.selectionService.items, mapping: $scope.cacheService.find})

      $scope.destroy = (staff) ->
        staffService.remove {no: staff.no}, () ->
          feedback.success "删除员工#{staff.name}成功"
          delete $scope.selectionService.items[staff.no]
          $scope.staffsTable.reload()
        , (resp) ->
          feedback.error("删除员工#{staff.name}失败", resp)
  ])

.controller('StaffViewCtrl', ['$scope', '$stateParams', '$log', ($scope, $stateParams, $log) ->
    $scope.staff = $scope.cacheService.find $stateParams.no, true
    $log.log "Initialized the Staff View controller on: " + JSON.stringify($scope.staff)
  ])

.controller('StaffNewCtrl', ['$scope', '$state', '$log', 'Feedback', 'StaffService', 'StaffSiteService', 'StaffDepartmentService',
    ($scope, $state, $log, feedback, staffService, siteService, departmentService) ->

      $log.log "Initialized the Staff New controller"
      $scope.disabled = false

      siteService.query (data) ->
        $scope.sites = data

      departmentService.query (data) ->
        $scope.departments = data

      $scope.create = () ->
        $scope.submited = true
        staff = $scope.formatData($scope.staff, $scope.site, $scope.department, $scope.staff_user)
        staffService.save staff, () ->
          feedback.success "新建员工#{staff.name}成功"
          $state.go "staffs.list"
        , (resp) ->
          feedback.error("新建员工#{staff.name}失败", resp)
  ])

.controller('StaffEditCtrl',
  ['$scope', '$state', '$log', '$stateParams', 'Feedback', 'StaffService', 'StaffSiteService', 'StaffDepartmentService',
    ($scope, $state, $log, $stateParams, feedback, staffService, siteService, departmentService) ->

      $scope.staff = $scope.cacheService.find $stateParams.no, true
      $log.log "Initialized the Staff Edit controller on: " + JSON.stringify($scope.staff)
      $scope.disabled = true

      $scope.statusList = [{id:"Normal",name:"在职"},{id:"Quit",name:"离职"}]

      staffService.get({no: $scope.staff.no}).$promise
      .then (data) ->
        $scope.staff = data
        return siteService.query().$promise
      .then (data) ->
        $scope.sites = data
        $scope.site = site for site in $scope.sites when site.sn is $scope.staff.site.sn
        return departmentService.query().$promise
      .then (data) ->
        $scope.departments = data
        $scope.department = department for department in $scope.departments when department.sn is $scope.staff.department.sn
        $scope.staff.status = status for status in $scope.statusList when status.id is $scope.staff.status

      # 编辑页面提交
      $scope.update = () ->
        $scope.submited = true

        staff = $scope.formatData($scope.staff, $scope.site, $scope.department, $scope.staff_user)
        staffService.update {no: staff.no}, staff, () ->
          feedback.success "修改员工#{staff.name}成功"
          $state.go "staffs.list"
        , (resp) ->
          feedback.error("修改员工#{staff.name}失败", resp);
  ])