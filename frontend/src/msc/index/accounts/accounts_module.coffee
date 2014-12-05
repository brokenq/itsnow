angular.module('MscIndex.Accounts', [])
  .config ($stateProvider)->
    $stateProvider.state 'accounts',
      url: '/accounts',
      abstract: true,
      controller: 'AccountsCtrl',
      templateUrl: 'accounts/index.tpl.jade'
      data: {pageTitle: '帐户管理', default: 'accounts.msu_list'}
    $stateProvider.state 'accounts.msu_list',
      url: '^/msu_accounts',
      controller: 'AccountListCtrl',
      templateUrl: 'accounts/list.tpl.jade'
      data: {pageTitle: '企业管理'}
    $stateProvider.state 'accounts.msp_list',
      url: '^/msp_accounts',
      controller: 'AccountListCtrl',
      templateUrl: 'accounts/list.tpl.jade'
      data: {pageTitle: '服务商管理'}
    $stateProvider.state 'accounts.view',
      url: '/{sn}',
      templateUrl: 'accounts/view.tpl.jade'
      data: {pageTitle: '查看帐户'}

  .filter('formatSubDomain', ['$sce', ($sce)->
    (account) ->
      link = window.location.protocol + "//" + account.domain + window.location.host.replace(/^msc\./,'.')
      if account.process? and account.process.status == 'Running'
        $sce.trustAsHtml '<a target="_blank" href="' + link + '">' + link + '</a>'
      else
        link
  ])
  .filter('formatAccountStatus', ->
    (status) ->
      return "待审核" if status == 'New'
      return "已批准" if status == 'Valid'
      return "被拒绝" if status == 'Rejected'
  )
  .filter('formatTime', ->
    (time) ->
      date = new Date(time)
      return date.toLocaleString()
  )
  .filter('processStatusCss', ->
    (status) ->
      switch status
        when 'Stopped' then 'grey'
        when 'Running' then 'green'
        when 'Starting' then 'light-green'
        when 'Stopping' then 'light-orange'
        when 'Abnormal' then 'red'
        else #Unknown
  )
  .controller('AccountsCtrl', ['$scope', '$state', '$resource', '$http', 'Feedback', 'CacheService', \
                              ( $scope,   $state,   $resource,   $http,   feedback,   CacheService) ->
    # frontend controller logic
    console.log("Initialized the Accounts controller")
    $scope.options =
      page:  1,           # show first page
      count: 10           # count per page

    $scope.cacheService = new CacheService "sn", (value)->
      data = {}
      $.ajax
        url:    "/admin/api/accounts/#{value}"
        async:  false
        type:   "GET"
        success: (response)->
          data = response
      return data

    actions =
      approve:
        method: 'PUT'
        params:
          action: 'approve'
      reject:
        method: 'PUT'
        params:
          action: 'reject'
    Account  = $resource("/admin/api/accounts/:sn/:action", {sn: "@sn"}, actions)
    # 之所以把destroy方法放在这里，因为理论上，view/edit页面也可以发起删除操作


    $scope.execApprove = (account, succCallback, errCallback)->
      acc = new Account(account)
      acc.$approve ->
        feedback.success "已批准 #{account.name} "
        succCallback() if succCallback
      , (resp)->
        feedback.error "批准 #{account.name} 失败", resp
        errCallback() if errCallback

    $scope.execReject = (account, succCallback, errCallback)->
      acc = new Account(account)
      acc.$reject ->
        feedback.success "已拒绝 #{account.name} "
        succCallback() if succCallback
      , (resp)->
        feedback.error "拒绝 #{account.name} 失败", resp
        errCallback() if errCallback

    $scope.execDestroy = (account, succCallback, errCallback)->
      acc = new Account(account)
      acc.$remove ->
        feedback.success "已删除 #{account.name} "
        succCallback() if succCallback
      , (resp)->
        feedback.error "删除 #{account.name} 失败", resp
        errCallback() if errCallback
#    $scope.approve = (account) ->
#      acc = new Account(account)
#      acc.$approve(->
#        feedback.success("已批准" + account.name)
#        # TODO 需要考虑结束操作后，是否应该当前纪录从选中的集合中移除
#        # 这个移除的工作，貌似该由Action Service完成？
#        # 包括表格的刷新，在批量操作时，每条记录都会导致表格刷新
#        # 这个问题，也应该是Action Service的perform API增强，支持设定一个成功的callback
#        # 所有的任务都完成后，action service对这个callback执行调用
#        # 或者 action service需要定义一个handler，要求这个handler提供四个方法
#        #  successOne(record), successAll(records), failureOne(record), falureAll(records)
#        $scope.tableParams.reload()
#      , (resp)->
#        feedback.error("批准" + account.name + "失败", resp)
#      )
#    $scope.reject = (account) ->
#      acc = new Account(account)
#      acc.$reject(->
#        $scope.tableParams.reload()
#      , (resp)->
#        feedback.error("拒绝" + account.name + "失败", resp)
#      )
#    $scope.destroy = (account) ->
#      acc = new Account(account)
#      acc.$remove ->
#        $scope.tableParams.reload()

    $scope.autoCreate = (account) ->
      $http.post "/admin/api/processes/auto_create/" + account.sn, "", ->
        $scope.tableParams.reload()

  ])
  .controller('AccountListCtrl', ['$scope', '$location', '$resource', '$timeout', 'ngTableParams', 'ActionService', 'CommonService', 'SelectionService' ,'Feedback',\
                                  ($scope,   $location,   $resource,   $timeout,   NgTable,         ActionService,   commonService,   SelectionService,   feedback) ->
    console.log("Initialized the Account list controller")
    Accounts = $resource("/admin/api/accounts")
    args =
      total: 0
      getData: ($defer, params)->
        $location.search(params.url()) # put params in url
        type = $location.$$path.substr($location.$$path.lastIndexOf('/')+1)
        defaults = params.url()
        if type == 'msu_accounts'
          defaults.type = "msu"
        else if type == 'msp_accounts'
          defaults.type = "msp"
        Accounts.query(defaults, (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.accounts = data)
            $scope.cacheService.cache data
          , 500)
        )
#    actions =
#      approve:
#        method: 'PUT'
#        params:
#          action: 'approve'
#      reject:
#        method: 'PUT'
#        params:
#          action: 'reject'
#    Account  = $resource("/admin/api/accounts/:sn/:action", {sn: "@sn"}, actions)
    $scope.accountsTable = new NgTable(angular.extend($scope.options, $location.search()), args)
    $scope.selectionService = new SelectionService($scope.cacheService.records, "sn")
    $scope.actionService = new ActionService {watch: $scope.selectionService.items, mapping: $scope.cacheService.find}
    $scope.reload = ->
      $scope.accountsTable.reload()
    $scope.approve = (account)->
      $scope.execApprove account, ->
        $scope.selectionService.update($scope.reload)


    $scope.reject = (account)->
      $scope.execReject account, ->
        $scope.selectionService.update($scope.reload)

    $scope.destroy = (account)->
      $scope.execDestroy account, ->
        $scope.selectionService.update($scope.reload)







































#    $scope.approve = (account) ->
#      acc = new Account(account)
#      acc.$approve(->
#        feedback.success("已批准" + account.name)
#        # TODO 需要考虑结束操作后，是否应该当前纪录从选中的集合中移除
#        # 这个移除的工作，貌似该由Action Service完成？
#        # 包括表格的刷新，在批量操作时，每条记录都会导致表格刷新
#        # 这个问题，也应该是Action Service的perform API增强，支持设定一个成功的callback
#        # 所有的任务都完成后，action service对这个callback执行调用
#        # 或者 action service需要定义一个handler，要求这个handler提供四个方法
#        #  successOne(record), successAll(records), failureOne(record), falureAll(records)
#        #$scope.tableParams.reload()
#        $scope.selectionService.update($scope.reload)
#      , (resp)->
#        feedback.error("批准" + account.name + "失败", resp)
#      )

  ])
  .controller('AccountViewCtrl', ['$scope', '$stateParams','$filter', ($scope, $stateParams,$filter) ->
    account = $scope.cacheService.find $stateParams.sn, true
    $scope.account = account
    console.log("Initialized the Account View controller on: " + JSON.stringify(account))
    $scope.account.createdAtFMT = $filter('formatTime')($scope.account.createdAt)
    $scope.account.statusFMT = $filter('formatAccountStatus')($scope.account.status)
  ])

