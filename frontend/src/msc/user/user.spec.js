/**
 * Created by User on 2014/7/21.
 */
describe('user controllers', function () {

    var scope, ctrl, $httpBackend;

    beforeEach(module('ItsNow.User'));

    beforeEach(inject(function (_$httpBackend_, $rootScope, $controller) {
        $httpBackend = _$httpBackend_;
        $httpBackend.expectGET('/api/users').
            respond([
                {id: '001', name: 'Moroni', nickname: 'Mor', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '002', name: 'Tiancum', nickname: 'Tia', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '003', name: 'Jacob', nickname: 'Jac', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '004', name: 'Nephi', nickname: 'Ne', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '005', name: 'Enos', nickname: 'En', createdAt: '201407211258', updatedAt: '201407211258'}
            ]);

        scope = $rootScope.$new();
        ctrl = $controller(UserListCtrl, {$scope: scope});
    }));

    it('should create "user" model with 5 users fetched from xhr', function () {
        setTimeout(function () {
            $httpBackend.flush();
            expect(scope.users).toEqual([
                {id: '001', name: 'Moroni', nickname: 'Mor', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '002', name: 'Tiancum', nickname: 'Tia', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '003', name: 'Jacob', nickname: 'Jac', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '004', name: 'Nephi', nickname: 'Ne', createdAt: '201407211258', updatedAt: '201407211258'},
                {id: '005', name: 'Enos', nickname: 'En', createdAt: '201407211258', updatedAt: '201407211258'}
            ]);
        }, 1000);
    });

});