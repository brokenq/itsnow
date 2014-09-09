# List accounts
angular.module('MscIndex.Account', ['ngTable','ngResource'])
  .controller 'AccountListCtrl', ($scope)->
    $scope.accounts = [
      {sn: "MSP-001", domain: "dnt",  name: "DNT"},
      {sn: "MSU-001", domain: "csvw", name: "CSVW"}
    ]