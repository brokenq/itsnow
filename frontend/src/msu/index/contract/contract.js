angular.module('MsuIndex.Contract', ['ngTable', 'ngResource']).config(function($stateProvider) {
  $stateProvider.state('contracts', {
    url: '/contracts',
    templateUrl: 'contract/list.tpl.jade',
    data: {
      pageTitle: '合同管理'
    }
  })
  .state('contracts.contract', {
    url: '/contract',
    templateUrl: 'contract/list.tpl.jade',
    data: {
      pageTitle: '我的合同'
    }
  });
})
  .factory('ContractService', [
  '$resource', function($resource) {
    return $resource("/api/contracts");
  }])
  .factory('RejectService', ['$resource', function ($resource) {
        return $resource('/api/contracts/:sn/reject', {sn:'@sn'}, {
            reject: {method: 'PUT'}
        });
    }])
  .factory('ApproveService', ['$resource', function ($resource) {
        return $resource('/api/contracts/:sn/approve', {sn:'@sn'}, {
            approve: {method: 'PUT'}
        });
    }])
  .filter('formatContractStatus', function() {
  return function(status) {
    if (status === 'Draft') {
      return "邀约";
    }
    if (status === 'Approved') {
      return "已批准";
    }
    if (status === 'Purposed') {
      return "应约";
    }
    if (status === 'Rejected') {
      return "已拒绝";
    }
  };
})
  .filter('formatTime', function() {
  return function(time) {
    var date;
    date = new Date(time);
    return date.toLocaleString();
  };
})
  .controller('ContractListCtrl', [
  '$scope', '$location','$state', '$timeout', 'ngTableParams', 'ContractService','ApproveService','RejectService', function($scope, $location,$state, $timeout, ngTableParams, contractService,approveService,rejectService) {
    var args, options;
    options = {
      page: 1,
      count: 10
    };
    args = {
      total: 0,
      getData: function($defer, params) {
        $location.search(params.url());
        return contractService.query(params.url(), function(data, headers) {
          return $timeout(function() {
            params.total(headers('total'));
            return $defer.resolve($scope.contracts = data);
          }, 500);
        });
      }
    };

    $scope.process =  function(cid) {
        $state.go('contract-create');
    }
    $scope.tableParams = new ngTableParams(angular.extend(options, $location.search()), args);
    $scope.checkboxes = {
      'checked': false,
      items: {}
    };
    $scope.$watch('checkboxes.checked', function(value) {
      return angular.forEach($scope.contracts, function(item) {
        if (angular.isDefined(item.sn)) {
          return $scope.checkboxes.items[item.sn] = value;
        }
      });
    });
    $scope.$watch('checkboxes.items', function(values) {
      var checked, total, unchecked;
      if (!$scope.contracts) {
        return;
      }
      checked = 0;
      unchecked = 0;
      total = $scope.contracts.length;
      angular.forEach($scope.contracts, function(item) {
        checked += $scope.checkboxes.items[item.sn] || 0;
        return unchecked += (!$scope.checkboxes.items[item.sn]) || 0;
      });
      if ((unchecked === 0) || (checked === 0)) {
        $scope.checkboxes.checked = checked === total;
      }
      return angular.element(document.getElementById("select_all")).prop("indeterminate", checked !== 0 && unchecked !== 0);
    }, true);

    $scope.approve = function() {
        return angular.forEach($scope.checkboxes.items, function(value, key) {
            if (value) {
                approveService.approve({sn:key});
                alert('批准完成');
                $location.path('/contracts/contract');
                    //$state.go('contracts.contract');

            }
        });
    };
    $scope.reject = function() {
        return angular.forEach($scope.checkboxes.items, function(value, key) {
            if (value) {
                rejectService.reject({sn:key});
                alert('拒绝完成');
                $state.go('contracts.contract');
            }
         });
    };
  }
])
;
