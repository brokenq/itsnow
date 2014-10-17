// List System
angular.module('Service.Dictnew', ['ngTable', 'ngResource'])

    .config(function ($stateProvider) {
        $stateProvider.state('dict_new', {
            url: '/new',
            templateUrl: '/dict/new.tpl.jade',
            data: {pageTitle: '流程字典添加'}
        });
    })

    .factory('DictNewService', ['$resource', function ($resource) {
        return $resource('/api/dict-dictionaries/start', {}, {
            start: {method: 'POST'}
        });
    }
    ])

    .filter('stateFilter', function () {
        var stateFilter = function (input) {
            if(input === '1'){
                return '有效';
            }else{
                return '无效';
            }
        };
        return stateFilter;
    })

    .controller('DictNewCtrl', ['$scope', '$location', '$timeout', '$state', 'DictNewService', function ($scope, $location, $timeout, $state, dictNewService) {
        var options = {
            page: 1,           // show first page
            count: 10           // count per page
        };
    }
    ]);
