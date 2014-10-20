
angular.module('Lib.Directives', [])
  .directive('pwCheck', [->
    {
      require: 'ngModel',
  		link: (scope, elem, attrs, ctrl) ->
  			firstPassword = '#' + attrs.pwCheck;
  			elem.add(firstPassword).on 'keyup', ->
  				scope.$apply ->
  					v = elem.val() == $(firstPassword).val();
  					ctrl.$setValidity('pwmatch', v);
    }
  ])
  .directive('ngUnique', ['$http', (async) ->
    {
      require: 'ngModel',
      link: (scope, elem, attrs, ctrl)->
        elem.on 'blur', () ->
          scope.$apply ->
            val = elem.val()
            # GET /accounts/check_unique/name/$accountName
            # GET /accounts/check_unique/domain/$accountDomain
            # GET /users/check_unique/username/$username
            # GET /users/check_unique/email/$email
            # GET /users/check_unique/phone/$phone
            async({method: 'GET', url: attrs.ngUnique + "/" + val}).success (data)->
              ctrl.$setValidity('unique', data.status);
    }
  ])