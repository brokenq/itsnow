# New account
angular.module('MscIndex.ProcessNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'process_new',
      url: '/processes/new',
      templateUrl: 'process/new.tpl.jade'
      data: {pageTitle: '分配服务进程'}

  .controller 'ProcessNewCtrl', ['$scope', '$location', '$timeout', 'ProcessService',
    ($scope, $location, $timeout, processService)->
      # Do nothing now
  ]

