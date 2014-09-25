# New account
angular.module('MscIndex.HostNew', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'host_new',
      url: '/hosts/new',
      templateUrl: 'host/new.tpl.jade'
      data: {pageTitle: '新增主机'}

  .controller 'HostNewCtrl', ['$scope', '$location', '$timeout', 'HostService',
    ($scope, $location, $timeout, hostService)->
      # Do nothing now
  ]

