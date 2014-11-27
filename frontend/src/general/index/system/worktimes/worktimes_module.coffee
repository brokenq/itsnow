angular.module('System.WorkTime',[])
.config ($stateProvider, $urlRouterProvider)->
  $stateProvider.state 'worktimes',
    url: '/worktimes',
    abstract: true,
    templateUrl: 'system/worktimes/index.tpl.jade',
    controller: 'WorkTimeCtrl',
    data: {pageTitle: '工作时间管理', default: 'worktimes.list'}
  $stateProvider.state 'worktimes.list',
    url: '/list',
    templateUrl: 'system/worktimes/list.tpl.jade'
    controller: 'WorkTimeListCtrl',
    data: {pageTitle: '时间列表'}
  $stateProvider.state 'worktimes.new',
    url: '/new',
    templateUrl: 'system/worktimes/new.tpl.jade'
    controller: 'WorkTimeNewCtrl',
    data: {pageTitle: '新增时间'}
  $stateProvider.state 'worktimes.view',
    url: '/{sn}',
    templateUrl: 'system/worktimes/view.tpl.jade'
    controller: 'WorkTimeViewCtrl',
    data: {pageTitle: '查看工作时间'}
  $stateProvider.state 'worktimes.edit',
    url: '/{sn}/edit',
    templateUrl: 'system/worktimes/edit.tpl.jade'
    controller: 'WorkTimeEditCtrl',
    data: {pageTitle: '编辑时间'}
  $urlRouterProvider.when '/worktimes', '/worktimes/list'
.controller('WorkTimeCtrl', ['$scope', '$resource','$state', 'Feedback', 'CacheService',\
    ($scope,$resource, $state, feedback, CacheService) ->
      $scope.init = ()->
        $scope.workdates= [
            { id: "1" ,name: "星期一", checked:false}
            { id: "2" ,name: "星期二", checked:false}
            { id: "3" ,name: "星期三", checked:false}
            { id: "4" ,name: "星期四", checked:false}
            { id: "5" ,name: "星期五", checked:false}
            { id: "6" ,name: "星期六", checked:false}
            { id: "7" ,name: "星期日", checked:false}
        ]
      $scope.init()
      $scope.toggleCheckboxesParent=(workdate) ->
        workdate.checked=!(workdate.checked)
      $scope.getWorkNames=(workdates) ->
        ids =[]
        names =[]
        for workdate in workdates
          if workdate.checked is true
            ids.push workdate.id
            names.push workdate.name
        return names.join()

      $scope.options = {page: 1, count: 10}
      $scope.cacheService = new CacheService "sn", (value)->
        data = {}
        $.ajax
          url: "/api/work_times/#{value}"
          async: false
          type: "GET"
          success: (response)->
            data = response
        return data
      $scope.services = $resource("/api/work_times/:sn", {},
        get: { method: 'GET', params: {sn: '@sn'}},
        save: { method: 'POST'},
        update: { method: 'PUT', params: {sn: '@sn'}},
        query: { method: 'GET', isArray: true},
        remove: { method: 'DELETE', params: {sn: '@sn'}}
      )

      $scope.Destroy = (worktime,succCallback, errCallback) ->
        $scope.services.remove {sn: worktime.sn}, () ->
          feedback.success "删除时间#{worktime.name}成功"
          succCallback() if succCallback
        , (resp) ->
          errCallback() if errCallback
          feedback.error("删除时间#{worktime.name}失败", resp)

  ])
.controller('WorkTimeListCtrl',['$scope', '$location', 'ngTableParams', 'ActionService','SelectionService','Feedback',\
                               ($scope,     $location,  NgTable,         ActionService,   SelectionService,feedback) ->
     console.log("Initialized the worktime list controller")
     args =
       total: 0
       getData: ($defer, params)->
         $location.search params.url() # put params in url
         $scope.services.query params.url(), (datas, headers) ->
          params.total headers 'total'
          $defer.resolve $scope.worktimes = datas; $scope.cacheService.cache datas
     $scope.worktimesTable = new NgTable(angular.extend($scope.options, $location.search()), args);
     $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
     $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}
     $scope.reload = ->
       $scope.worktimesTable.reload()
     $scope.destroy = (worktime)->
       $scope.Destroy worktime, ->
         delete $scope.selectionService.items[worktime.sn]
         $scope.reload()
])

.controller('WorkTimeNewCtrl', ['$scope', '$state', 'Feedback',\
                               ($scope,     $state,   feedback) ->
    $scope.init()
    $scope.toggleCheckboxes=(workdate)->
      $scope.toggleCheckboxesParent(workdate)
    $scope.create=() ->
      $scope.worktime.name=$scope.getWorkNames($scope.workdates)
      $scope.init()
      $scope.services.save $scope.worktime, ->
        feedback.success "新建工作日成功"
        $state.go "worktimes.list"
      , (resp) ->
        feedback.error("新建工作日失败", resp)
])
.controller('WorkTimeEditCtrl', ['$scope', '$state', '$stateParams', 'Feedback',\
                                ($scope,    $state,   $stateParams,  feedback) ->
      $scope.selectWorkDates=[]
      sn=$stateParams.sn
      $scope.worktime = $scope.cacheService.find sn, true
      $scope.selectWorkDates=$scope.worktime.name.split(",")
      for workdate in $scope.workdates
        if($scope.selectWorkDates.indexOf(workdate.name)>-1)
          workdate.checked=true
      $scope.toggleCheckboxes=(workdate)->
        $scope.toggleCheckboxesParent(workdate)
      $scope.update=() ->
        $scope.worktime.name=$scope.getWorkNames($scope.workdates)
        $scope.init()
        $scope.worktime.$promise = `undefined`
        $scope.worktime.$resolved = `undefined`
        $scope.services.update {sn: sn}, $scope.worktime, () ->
          feedback.success "修改工作日成功"
          $state.go "worktimes.list"
        , (resp) ->
          feedback.error("修改工作日失败", resp);

])
.controller('WorkTimeViewCtrl', ['$scope', '$state', '$stateParams', 'Feedback',
    ($scope,    $state,   $stateParams,  feedback) ->
      $scope.selectWorkDates=[]
      sn=$stateParams.sn
      $scope.worktime = $scope.cacheService.find sn, true
      $scope.selectWorkDates=$scope.worktime.name.split(",")
      for workdate in $scope.workdates
        if($scope.selectWorkDates.indexOf(workdate.name)>-1)
          workdate.checked=true
  ])

