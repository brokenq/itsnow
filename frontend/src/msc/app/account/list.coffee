# List accounts
angular.module('MscApp.Account', ['ngTable','ngResource'])
  .controller 'AccountListCtrl', ($scope)->
    $scope.accounts = [
      {sn: "MSP-001", domain: "dnt",  name: "DNT"},
      {sn: "MSU-001", domain: "csvw", name: "CSVW"}
    ]