/**
 * Created by User on 2014/7/23.
 */
//angular module
var menuApp = angular.module('menuApp', ['ui.tree']);

//test controller
var menuCtrl = menuApp.controller('menuCtrl', function ($scope) {

    $scope.menuList = [
        {id: '001', title: '用户', url: 'index.user', subMenu: ''},
        {id: '002', title: '任务单', url: 'task'},
        {id: '003', title: 'UI Elements', url: 'ui-elements', subMenu: [
            {id: '003001', title: 'Elements', url: 'ui-elements.elements'},
            {id: '003002', title: 'treeview', url: 'ui-elements.treeview'}
        ]},
        {id: '004', title: 'Tables', url: 'tables', subMenu: [
            {id: '004001', title: 'Simple & Dynamic', url: 'tables.simple'},
            {id: '004002', title: 'jqGrid plugin', url: 'tables.jqGrid_plugin'}
        ]},
        {id: '005', title: '日历', url: 'calendar'}
    ];

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

});
