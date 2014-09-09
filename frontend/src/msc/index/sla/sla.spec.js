/**
 * Created by User on 2014/7/21.
 */
describe('Msc SLA', function () {

    var scope, ctrl, $httpBackend;
    var records = [
      {id: '001', name: '协议1',  resolutionTime: '201407211258', responseTime: '201407211258'},
      {id: '002', name: '协议2',  resolutionTime: '201407211258', responseTime: '201407211258'},
      {id: '003', name: '协议3',  resolutionTime: '201407211258', responseTime: '201407211258'},
      {id: '004', name: '协议4',  resolutionTime: '201407211258', responseTime: '201407211258'},
      {id: '005', name: '协议5',  resolutionTime: '201407211258', responseTime: '201407211258'}
    ];

    beforeEach(module('MscIndex.SLA'));

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('/api/sla').respond(records);

        scope = $rootScope.$new();
        ctrl = $controller(SlaListCtrl, {$scope: scope});
    }));

    it('should create "SLA" model with 5 records fetched from xhr', function () {
        setTimeout(function () {
          $httpBackend.flush();
          expect(scope.datas).toEqual(records);
        }, 1000);
    });

});