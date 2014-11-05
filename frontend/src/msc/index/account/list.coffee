# List accounts
angular.module('MscIndex.Account', ['ngTable','ngResource', 'ngSanitize','dnt.action.service', 'Lib.Feedback'])
  .config ($stateProvider)->
    $stateProvider.state 'accounts',
      url: '/accounts',
      templateUrl: 'account/list.tpl.jade'
      data: {pageTitle: '帐户管理'}
    $stateProvider.state 'accounts.msu',
      url: '/msu',
      templateUrl: 'account/list.tpl.jade'
      data: {pageTitle: '企业管理'}
    $stateProvider.state 'accounts.msp',
      url: '/msp',
      templateUrl: 'account/list.tpl.jade'
      data: {pageTitle: '服务商管理'}

  .factory('AccountService', ['$resource', ($resource) ->
    $resource "/admin/api/accounts/:sn",{},
      get: {method:"GET", params:{sn:'@sn'}}
  ])
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
  .controller 'AccountListCtrl',['$scope', '$location', '$timeout', '$resource', '$http', 'ngTableParams', 'ActionService', 'Feedback', \
                                ($scope, $location, $timeout, $resource, $http, ngTableParams, ActionService, Feedback)->
    Accounts = $resource("/admin/api/accounts")
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
    options =
      page:  1,           # show first page
      count: 10           # count per page
    args =
      total: 0,
      getData: ($defer, params) ->
        $location.search(params.url()) # put params in url
        type = $location.$$path.substr($location.$$path.lastIndexOf('/')+1)
        defaults = params.url()
        defaults.type = type if type == 'msu' or type == 'msp'
        Accounts.query(defaults, (data, headers) ->
          $timeout(->
            params.total(headers('total'))
            $defer.resolve($scope.accounts = data)
          , 500)
        )
    $scope.checkboxes = { 'checked': false, items: {} }
    $scope.getAccountBySn  = (sn)->
      return account for account in $scope.accounts when account.sn == sn
    $scope.actionService = new ActionService({watch: $scope.checkboxes.items, mapping: $scope.getAccountBySn})

    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args)
    $scope.$on '$stateChangeSuccess', (e, state)->
      if state.name == 'accounts.msu' or state.name == 'accounts.msp'
        $scope.tableParams.reload()


    $scope.approve = (account) ->
      acc = new Account(account)
      acc.$approve(->
        Feedback.success("已批准" + account.name)
        # TODO 需要考虑结束操作后，是否应该当前纪录从选中的集合中移除
        # 这个移除的工作，貌似该由Action Service完成？
        # 包括表格的刷新，在批量操作时，每条记录都会导致表格刷新
        # 这个问题，也应该是Action Service的perform API增强，支持设定一个成功的callback
        # 所有的任务都完成后，action service对这个callback执行调用
        # 或者 action service需要定义一个handler，要求这个handler提供四个方法
        #  successOne(record), successAll(records), failureOne(record), falureAll(records)
        $scope.tableParams.reload()
      , (resp)->
        Feedback.error("批准" + account.name + "失败", resp)
      )
    $scope.reject = (account) ->
      acc = new Account(account)
      acc.$reject(->
        $scope.tableParams.reload()
      , (resp)->
        Feedback.error("拒绝" + account.name + "失败", resp)
      )
    $scope.destroy = (account) ->
      acc = new Account(account)
      acc.$remove ->
        $scope.tableParams.reload()

    $scope.autoCreate = (account) ->
      $http.post "/admin/api/processes/auto_create/" + account.sn, "", ->
        $scope.tableParams.reload()

    # watch for check all checkbox
    $scope.$watch 'checkboxes.checked', (value)->
      angular.forEach $scope.accounts, (item)->
        $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)

    # watch for data checkboxes
    $scope.$watch('checkboxes.items', (values) ->
      return if !$scope.accounts
      checked = 0
      unchecked = 0
      total = $scope.accounts.length
      angular.forEach $scope.accounts, (item)->
        checked   +=  ($scope.checkboxes.items[item.sn]) || 0
        unchecked += (!$scope.checkboxes.items[item.sn]) || 0
      $scope.checkboxes.checked = (checked == total) if (unchecked == 0) || (checked == 0)
      # grayed checkbox
      angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0));
    , true)

  ]

