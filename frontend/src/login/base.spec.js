/**
 * Created by kadvin on 14-7-2.
 */
describe('Itsnow Login Base', function () {
    describe('loading', function () {
        var ctrl, scope;

        beforeEach(
            module('Itsnow.LoginBase')
        );

        beforeEach(inject(function ($controller, $rootScope) {
          scope = $rootScope.$new();
          ctrl = $controller('LoginCtrl', {$scope: scope});
        }));

        it('should pass a dummy test', function () {
            expect(scope).toBeDefined();
        });
    });
});

