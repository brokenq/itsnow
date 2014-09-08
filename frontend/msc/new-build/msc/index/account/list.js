angular.module('MscApp.Account', ['ngTable', 'ngResource']).controller('AccountListCtrl', function($scope) {
  return $scope.accounts = [
    {
      sn: "MSP-001",
      domain: "dnt",
      name: "DNT"
    }, {
      sn: "MSU-001",
      domain: "csvw",
      name: "CSVW"
    }
  ];
});
