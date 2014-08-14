/**
 * Created by Sin on 2014/8/6.
 */
// Service
angular.module('ItsNow.TroubleTicket.Service', [])

    // 故障工单列表
    .factory('ListTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/?', {}, {
            list: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 根据关键字搜索故障单信息
    .factory('SearchTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/?key=:key', {key:'@key'}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 查询故障单对应的当前任务信息
    .factory('QueryTroubleTicketTaskResource', ['$resource', function ($resource) {
        return $resource('/api/incidents/:instanceId', {instanceId:'@instanceId'}, {
            query: {
                method: 'GET'
            }
        });
    }])

    // 查询故障单对应的当前任务信息
    .factory('QueryTroubleTicketTaskService', ['QueryTroubleTicketTaskResource', function (QueryTroubleTicketTaskResource) {
        return{
            query: function (instanceId) {
                return QueryTroubleTicketTaskResource.query({instanceId: instanceId}).$promise;
            }
        };
    }])

    // 新建故障工单
    .factory('NewTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/start', {}, {
            start: {method: 'POST'}
        });
    }])

    // 签收故障工单
    .factory('AcceptTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 分析故障工单
    .factory('AnalysisTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete?can_process=:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 处理故障工单
    .factory('ProcessTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete?:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 关闭故障工单
    .factory('CloseTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

;