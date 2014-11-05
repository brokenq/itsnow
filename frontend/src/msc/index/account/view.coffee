# View account
angular.module('MscIndex.AccountView', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'account_view',
      url: '/accounts/{sn}',
      templateUrl: 'account/view.tpl.jade'
      data:
        pageTitle: '查看帐户'

  .controller 'AccountViewCtrl', ['$scope', '$state', '$stateParams', '$http', 'AccountService' ,
    ($scope, $state, $stateParams, $http, accountService)->
      accountService.get {sn:$stateParams.sn}, (data)->
        $scope.account = data
  ]

