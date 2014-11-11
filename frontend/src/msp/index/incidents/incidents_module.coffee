  # List catalogs
  angular.module('MspIndex.Incidents',
    ['ngTable',
     'ngResource',
     'Lib.Commons',
     'Lib.Utils',
     'dnt.action.service',
     'Lib.Feedback'])
    .config ($stateProvider,$urlRouterProvider)->
      $stateProvider.state 'incidents',
        url: '/incidents',
        templateUrl: 'incidents/index.tpl.jade',
        abstract:true,
        controller: 'IncidentCtrl',
        data: {pageTitle: '故障管理', default: 'incidents.opened'}
      $stateProvider.state 'incidents.opened',
        url: '/opened',
        templateUrl: 'incidents/opened.tpl.jade',
        controller: 'OpenedListCtrl',
        data: {pageTitle: '我的故障单'}
      $stateProvider.state 'incidents.closed',
        url: '/closed',
        templateUrl: 'incidents/closed.tpl.jade',
        controller: 'ClosedListCtrl',
        data: {pageTitle: '已关闭故障单'}
      $stateProvider.state 'incidents.create',
        url: '/create',
        templateUrl: 'incidents/new.tpl.jade',
        controller: 'IncidentCreateCtrl',
        data: {pageTitle: '新建故障单'}
      $stateProvider.state 'incidents.view',
        url: '/{mspInstanceId}/view',
        templateUrl: 'incidents/view.tpl.jade',
        controller: 'IncidentViewCtrl',
        data: {pageTitle: '查看故障单'}
      $stateProvider.state 'incidents.action',
        url:'/{mspInstanceId}/action',
        templateUrl:'incidents/action.tpl.jade',
        controller:'IncidentProcessCtrl',
        data: {pageTitle: '处理故障单'}
      $urlRouterProvider.when '/incidents', '/incidents/opened'


    .factory('IncidentService', ['$resource', ($resource) ->
      $resource("/api/msp-incidents/:mspInstanceId",{},
        get: { method: 'GET', params: {mspInstanceId:'@mspInstanceId'}},
        query: { method: 'GET', isArray: true})
    ])

    .factory('ClosedIncidentService', ['$resource', ($resource) ->
      $resource("/api/msp-incidents/closed")
    ])

    #新建故障工单
    .factory('IncidentStartService', ['$resource', ($resource) ->
      $resource("/api/msp-incidents/start",{},
        start: {method: 'POST'})
    ])

    #签收/分析/处理/关闭故障工单
    .factory('IncidentActionService', ['$resource',($resource) ->
      $resource('/api/msp-incidents/:mspInstanceId/:taskId/complete', {},
        complete: {method: 'PUT',params:{mspInstanceId:'@mspInstanceId',taskId:'@taskId'}})
    ])

    #查询字典数据
    .factory('DictionariesService', ['$resource', ($resource) ->
      $resource("/api/dictionaries/code/:code",{code:'@code'},
        query: { method: 'GET', isArray: true})
    ])

    .filter('formatIncidentStatus', ()->
      return (status) ->
        return "新建" if status is 'New'
        return "已分配" if status is 'Assigned'
        return "已签收" if status is 'Accepted'
        return "处理中" if status is 'Resolving'
        return "已解决" if status is 'Resolved'
        return "已关闭" if status is 'Closed'
    )
    .filter('formatTime', () ->
      return (time) ->
        date = new Date(time)
        return date.toLocaleString();
    )

    .controller('IncidentCtrl',['$scope', '$state', '$log', 'Feedback','DictionariesService','CacheService',
      ($scope, $state, $log, feedback,dictService,CacheService) ->
        # frontend controller logic
        $log.log "Initialized the Incident controller"
        $scope.options =
          page: 1, # show first page
          count: 10 # count per page
        # 提交按钮是否已经执行了提交操作，false为未执行，则按钮可用
        $scope.cacheService = new CacheService("mspInstanceId")
        $scope.submited = false

        dictService.query({code:'inc002'},(data)->
          $scope.impacts = data
        )
        dictService.query({code:'inc001'},(data)->
          $scope.priorities = data
        )
        dictService.query({code:'inc004'},(data)->
          $scope.urgencies = data
        )
        dictService.query({code:'inc005'},(data)->
          $scope.requestTypes = data
        )

    ])

  .controller('OpenedListCtrl',
    ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'CommonService', 'IncidentService', 'Feedback',
      ($scope, $location, $log, NgTable, ActionService, commonService, incidentService, feedback) ->
        $log.log "Initialized the Incident Opened list controller"

        args =
          total: 0,
          getData: ($defer, params) ->
            $location.search(params.url()); # put params in url
            incidentService.query params.url(), (data, headers) ->
              params.total headers('total')
              $scope.cacheService.cache data
              $defer.resolve $scope.roles = data

        $scope.selection = { checked: false, items: {} }
        $scope.incidentsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
        $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
        commonService.watchSelection($scope.selection, $scope.cacheService.records, "id")

    ])

  .controller('ClosedListCtrl',
    ['$scope', '$location', '$log', 'ngTableParams', 'ActionService', 'CommonService', 'ClosedIncidentService', 'Feedback',
      ($scope, $location, $log, NgTable, ActionService, commonService, closedIncidentService, feedback) ->
        $log.log "Initialized the Incident Closed list controller"

        args =
          total: 0,
          getData: ($defer, params) ->
            $location.search(params.url()); # put params in url
            closedIncidentService.query params.url(), (data, headers) ->
              params.total headers('total')
              $scope.cacheService.cache data
              $defer.resolve $scope.roles = data

        $scope.selection = { checked: false, items: {} }
        $scope.incidentsTable = new NgTable(angular.extend($scope.options, $location.search()), args);
        $scope.actionService = new ActionService({watch: $scope.selection.items, mapping: $scope.cacheService.find})
        commonService.watchSelection($scope.selection, $scope.cacheService.records, "mspInstanceId")

    ])

    .controller('IncidentCreateCtrl',['$scope', '$state', '$log','IncidentStartService', 'Feedback','CacheService',
      ($scope, $state, $log,incidentStartService, feedback,CacheService) ->
        $log.log "Initialized the Incident Create controller"
        $scope.incident = {number:'INC001',createdBy:'admin'}
        $scope.buttonLabel = "创建"
        $scope.create = ->
          $scope.submited = true
          catalog = $scope.catalog
          incidentStartService.save($scope.incident,->
            feedback.success('创建故障单成功')
            $state.go('incidents.opened')
          ,(resp)->
            feedback.error('创建故障单失败',resp)
          )
    ])

    .controller('IncidentViewCtrl',['$scope', '$state','$stateParams', '$log', 'Feedback','CacheService',
      ($scope, $state,$stateParams, $log, feedback,CacheService) ->
        $log.log "Initialized the Incident View controller"
        $scope.incident = $scope.cacheService.find $stateParams.mspInstanceId,true
    ])

    .controller('IncidentProcessCtrl',['$scope', '$state','$stateParams', '$log', 'Feedback','IncidentService','IncidentActionService',
      ($scope, $state,$stateParams, $log, feedback,incidentService,incidentActionService) ->
        $log.log "Initialized the Incident Process controller"
        $scope.incident = $scope.cacheService.find $stateParams.mspInstanceId,true
        $scope.buttonLabel = "提交"

        $scope.getActionButton = (status) ->
          if(status is 'Assigned')
            return '分配'
          else if(status is 'Accepted')
            return '签收'
          else if(status is 'Resolving')
            return '分析'
          else if(status is 'Resolved')
            return '解决'
          else if(status is 'Closed')
            return '关闭'
          else
            return '新建'

        incidentService.get({mspInstanceId:$stateParams.mspInstanceId}, (data)->
          $scope.tasks = data
          for task in data.tasksList
            if(angular.isDefined(task.taskId))
              $scope.taskId = task.taskId
              $scope.taskName = task.taskName
              $scope.taskAction = task.taskDescription
              console.warn('id:'+$scope.taskId+' name:'+$scope.taskName+' action:'+$scope.taskAction)
              $scope.buttonLabel = $scope.getActionButton $scope.taskAction
        ,(resp)->
          feedback.error("查询任务"+$stateParams.mspInstanceId+"失败",resp)
        )

        $scope.action = (incident)->
          incidentActionService.complete({mspInstanceId:$scope.incident.mspInstanceId,taskId:$scope.taskId},$scope.incident,->
            feedback.success($scope.buttonLabel+"成功")
            $state.go("incidents.view",{mspInstanceId:$scope.incident.mspInstanceId})
          ,(resp)->
            feedback.error($scope.buttonLabel+"失败",resp)
          )
    ])

