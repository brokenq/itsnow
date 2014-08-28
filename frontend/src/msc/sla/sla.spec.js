/**
 * Created by User on 2014/7/21.
 */
describe('user controllers', function () {

    var scope, ctrl, $httpBackend;

    beforeEach(module('ItsNow.SLA'));

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('/api/sla').
            respond([
                {id: '001', name: '协议1', resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '002', name: '协议2',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '003', name: '协议3',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '004', name: '协议4',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '005', name: '协议5',  resolutionTime: '201407211258', responseTime: '201407211258'}
            ]);

        scope = $rootScope.$new();
        ctrl = $controller(SlaListCtrl, {$scope: scope});
    }));

    it('should create "user" model with 5 users fetched from xhr', function () {
        setTimeout(function () {
            $httpBackend.flush();
            expect(scope.datas).toEqual([
                {id: '001', name: '协议1', resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '002', name: '协议2',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '003', name: '协议3',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '004', name: '协议4',  resolutionTime: '201407211258', responseTime: '201407211258'},
                {id: '005', name: '协议5',  resolutionTime: '201407211258', responseTime: '201407211258'}
            ]);
        }, 1000);
    });

});