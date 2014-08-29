/**
 * Created by Sin on 2014/8/6.
 */
// Service
angular.module('Msp.Incident.Service', [])

    // 故障工单列表
    .factory('MSPListTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/?', {}, {
            list: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 根据关键字搜索故障单信息
    .factory('MSPSearchTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/?key=:key', {key:'@key'}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }])

    // 查询故障单对应的当前任务信息
    .factory('MSPQueryTroubleTicketTaskResource', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:mspInstanceId', {mspInstanceId:'@mspInstanceId'}, {
            query: {
                method: 'GET'
            }
        });
    }])

    // 查询故障单对应的当前任务信息
    .factory('MSPQueryTroubleTicketTaskService', ['MSPQueryTroubleTicketTaskResource', function (MSPQueryTroubleTicketTaskResource) {
        return{
            query: function (mspInstanceId) {
                return MSPQueryTroubleTicketTaskResource.query({mspInstanceId: mspInstanceId}).$promise;
            }
        };
    }])

    // 新建故障工单
    .factory('MSPNewTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/start', {}, {
            start: {method: 'POST'}
        });
    }])

    // 签收故障工单
    .factory('MSPAcceptTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 分析故障工单
    .factory('MSPAnalysisTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:taskId/complete?can_process=:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 处理故障工单
    .factory('MSPProcessTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:taskId/complete?:flag', null, {
            complete: {method: 'PUT'}
        });
    }])

    // 关闭故障工单
    .factory('MSPCloseTroubleTicketService', ['$resource', function ($resource) {
        return $resource('/api/msp-incidents/:taskId/complete', null, {
            complete: {method: 'PUT'}
        });
    }])

;