# View Process
angular.module('MscIndex.ProcessView', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'process_view',
      url: '/processes/{name}',
      templateUrl: 'process/view.tpl.jade'
      data: {pageTitle: '查看进程'}

  .controller 'ProcessViewCtrl', ['$scope', '$location', '$state', '$resource', '$interval', '$stateParams', '$filter', 'ProcessService', '$http', 'Feedback',
    ($scope, $location, $state, $resource, $interval, $stateParams, $filter,  processService, $http, Feedback)->
      Processes = $resource("/admin/api/processes/:name")
      actions =
        start:
          method: "PUT"
          params:
            action: "start"
        stop:
          method: "PUT"
          params:
            action: "stop"
        cancel:
          method: "PUT"
          params:
            action: "cancel"
      Process = $resource("/admin/api/processes/:name/:action", {name: "@name"}, actions)
      CancelAction = $resource("/admin/api/processes/:name/cancel/:job", {name: "@name", job: "@job"}, {cancel: {method: "PUT"}})
      Processes.get({name: $stateParams.name}, (process)->
        $scope.process = process
        $scope.process.display =
          status: null
          configuration: null
          schema:
            configuration: null
        $scope.process.display.status = $filter("formatProcessStatus")($scope.process.status)
        $scope.process.display.configuration = JSON.stringify $scope.process.configuration
        $scope.process.display.schema.configuration = JSON.stringify $scope.process.schema.configuration
        $scope.process.creationLog = ""
        $scope.process.startLog = ""
        $scope.process.stopLog = ""

        changeButton($filter("lowercase")(process.status))
        getCreateLog(process.configuration.createInvocationId)
        getStartLog(process.configuration.startInvocationId)
        getStopLog(process.configuration.stopInvocationId)
      )

      changeButton = (status)->
        $("#startBtn").removeClass("show").addClass("hidden")
        $("#cancelStartingBtn").removeClass("show").addClass("hidden")
        $("#stopBtn").removeClass("show").addClass("hidden")
        $("#cancelStoppingBtn").removeClass("show").addClass("hidden")
        switch status
          when "stopped" then  $("#startBtn").removeClass("hidden").addClass("show")
          when "starting" then $("#cancelStartingBtn").removeClass("hidden").addClass("show")
          when "running", "abnormal" then $("#stopBtn").removeClass("hidden").addClass("show")
          when "stopping" then $("#cancelStoppingBtn").removeClass("hidden").addClass("show")

      # 获取日志信息
      getCreateLog = (invokeId)->
        process = $scope.process
        createOffset = 0
        createIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{createOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.creationLog += log
              createOffset = parseInt(headers("offset"))
              changeButton($filter("lowercase")(headers("status")))
              $interval.cancel(createIntervalId) if createOffset is -1
        , 1000)

      getStartLog = (invokeId)->
        process = $scope.process
        startOffset = 0
        startIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{startOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.startLog += log
              startOffset = parseInt(headers("offset"))
              changeButton($filter("lowercase")(headers("status")))
              $interval.cancel(startIntervalId) if startOffset is -1
        , 1000)

      getStopLog = (invokeId)->
        process = $scope.process
        stopOffset = 0
        stopIntervalId = $interval(->
          if invokeId?
            url = "/admin/api/processes/#{process.name}/follow/#{invokeId}?offset=#{stopOffset}"
            $http.get(url).success (log, status, headers) ->
              $scope.process.stopLog += log
              stopOffset = parseInt(headers("offset"))
              changeButton($filter("lowercase")(headers("status")))
              $interval.cancel(stopIntervalId) if stopOffset is -1
        , 1000)

      # 操作
      $scope.start = ->
        acc = new Process($scope.process)
        acc.$start((data)->
          Feedback.success("正在启动" + $scope.process.name)
          getStartLog(data["job"])
        , (resp)->
          Feedback.error("启动" + $scope.process.name + "失败", resp)
        )

      $scope.stop = ->
        acc = new Process($scope.process)
        acc.$stop((data)->
          Feedback.success("正在停止" + $scope.process.name)
          getStopLog(data["job"])
        , (resp)->
          Feedback.error("停止" + $scope.process.name + "失败", resp)
        )

      $scope.cancel = ->
        acc = new CancelAction($scope.process)
        acc.$cancel({job: $scope.process.configuration.startInvocationId}, (data)->
          Feedback.success("正在停止" + $scope.process.name)
        , (resp)->
          Feedback.error("停止" + $scope.process.name + "失败", resp)
        )
  ]

