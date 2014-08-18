/**
 * Created by User on 2014/7/23.
 */
//angular module
var menuApp = angular.module('ItsNow.Menu', ['ui.tree']);

//test controller
var menuCtrl = menuApp.controller('menuCtrl', function ($scope) {

    $scope.menuList = [
        {id: '001', title: '用户', url: 'index.user'},
        {id: '002', title: 'SLA', url: 'index.sla'},
        {id: '003', title: '合同', url: 'index.contract'},
        {id: '004', title: '故障单', url: '', subMenu: [
            {id: '004001', title: 'MSU', url: 'index.list-trouble-ticket'/*, subMenu: [
                {id: '004005', title: '查询故障单', url: 'index.list-trouble-ticket'},
                {id: '004001', title: '新建故障单', url: 'index.new-trouble-ticket'},
                {id: '004002', title: '签收故障单', url: 'index.accept-trouble-ticket'},
                {id: '004003', title: '处理故障单', url: 'index.process-trouble-ticket'},
                {id: '004004', title: '关闭故障单', url: 'index.close-trouble-ticket'}
            ]*/},
            {id: '004002', title: 'MSP', url: 'index.msp-list-trouble-ticket'/*, subMenu: [
                {id: '004005', title: '查询故障单', url: 'index.list-trouble-ticket'},
                {id: '004001', title: '新建故障单', url: 'index.new-trouble-ticket'},
                {id: '004002', title: '签收故障单', url: 'index.accept-trouble-ticket'},
                {id: '004003', title: '处理故障单', url: 'index.process-trouble-ticket'},
                {id: '004004', title: '关闭故障单', url: 'index.close-trouble-ticket'}
            ]*/
            }
        ]}
    ];

    $scope.toggle = function(scope) {
        if(scope.collapse){
            scope.expand();
        }else{
            scope.collapse();
        }
    };

});
