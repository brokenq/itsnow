# Edit Host
angular.module('MscIndex.HostEdit', ['ngResource'])
  .config ($stateProvider)->
    $stateProvider.state 'host_edit',
      url: '/hosts/edit/{id}',
      templateUrl: 'host/edit.tpl.jade'
      data: {pageTitle: '修改主机'}

  .controller 'HostEditCtrl', ['$scope', '$location', '$state', '$resource', '$stateParams', 'Feedback'
    ($scope, $location, $state, $resource, $stateParams, Feedback)->
      Hosts = $resource("/admin/api/hosts/:id", {id: "@id"}, {update: {method: "PUT"}})
      Hosts.get({id: $stateParams.id}, (host)->
        $scope.host = host
        host.configuration.msu_version = host.configuration["msu.version"]
        host.configuration.msp_version = host.configuration["msp.version"]
      )
      $scope.types = [{key: "DB", value: "数据库主机"}, {key: "APP", value: "应用主机"}, {key: "COM", value: "综合主机"}]
      $scope.update = ()->
        host = $scope.host
        delete host["$promise"]
        delete host["$resolved"]
        msu_version = host.configuration.msu_version
        msp_version = host.configuration.msp_version
        delete host.configuration.msu_version
        delete host.configuration.msp_version
        host.configuration['msu.version'] = msu_version
        host.configuration['msp.version'] = msp_version
        acc = new Hosts(host)
        acc.$update( ->
          Feedback.success("已更新" + host.name)
          $state.go 'host_view', host
        , (resp)->
          Feedback.error("更新" + host.name + "失败", resp)
        )
  ]

