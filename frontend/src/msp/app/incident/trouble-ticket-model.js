/**
 * Created by Sin on 2014/8/6.
 */
// incident实体类
// 故障单Module

angular.module('MspIncident.Model', [])

    .factory('MSPTroubleTicketModelService', function () {

        return {
            selectedModel : {
                requestTypeModule : [
                    {name: 'email'},
                    {name: 'phone'},
                    {name: 'web'}
                ],
                areaModule : [
                    {name: '北京'},
                    {name: '上海'},
                    {name: '天津'},
                    {name: '重庆'}
                ],
                serviceCatalogModule : [
                    {name: '天'},
                    {name: '地'},
                    {name: '人'},
                    {name: '畜'}
                ],
                impactModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                categoryModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                urgencyModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                ciModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                priorityModule : [
                    {name: '高'},
                    {name: '中'},
                    {name: '低'}
                ],
                statusModule : [
                    {name: 'New'},
                    {name: 'Assigned'},
                    {name: 'Accepted'},
                    {name: 'Resolving'},
                    {name: 'Resolved'},
                    {name: 'Closed'}
                ],
                closeCodeModule : [
                    {name: '1'},
                    {name: '2'},
                    {name: '3'}
                ]
            }
        };

    });