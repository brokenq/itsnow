/**
 * Created by kadvin on 14-7-2.
 */
//describe('LoginCtrl', function () {
//    describe('isCurrentUrl', function () {
//        var ctrl, scope, $httpBackend;
//
//        beforeEach(
//            module('ItsNow.Login')
//        );
//
//        beforeEach(inject(function ($controller, $rootScope, _$httpBackend_) {
//            scope = $rootScope.$new();
//            $httpBackend = _$httpBackend_;
//            $httpBackend.expectGET('/security/csrf').respond([
//                {headerName: 'Content-Type: application/json', token: true }
//            ]);
//
//            ctrl = $controller('LoginCtrl2', { $scope: scope });
//        }));
//
//        it('should pass a dummy test', function () {
//            $httpBackend.flush();
//            expect(scope.user).toBeDefined();
//        });
//    });
//});

describe('Testing Login App Run', function () {

    beforeEach(module('ItsNow.Login', function ($provide) {
        return $provide.decorator('csrf', function () {
            return {
                load: function () {
                    return {};
                }
            };
        });
    }));

    var $rootScope;
    beforeEach(inject(function (_$rootScope_) {
        return $rootScope = _$rootScope_;
    }));

    it("defines a value I previously could not test", function () {
        return expect($rootScope.value).toEqual('testing');
    });

});