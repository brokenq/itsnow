
angular.module('Lib.Directives', ['Lib.Utils'])
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
  .directive('ngCheck', ['$http', 'Utils', (async, Utils) ->
    {
      require: 'ngModel',
      link: (scope, elem, attrs, ctrl)->
        elem.on 'blur', () ->
          scope.$apply ->
            errType = 'unique'
            errType = attrs.errType if attrs.errType?
            val = elem.val()
            return ctrl.$setValidity(errType, true) if !val? or val is ''
            # GET /public/accounts/check/{0}/name
            # GET /public/accounts/check/domain/$accountDomain
            # GET /public/users/check/username/$username
            # GET /public/users/check/email/$email
            # GET /public/users/check/phone/$phone
            async.get(Utils.stringFormat attrs.ngCheck, val).success(->
              ctrl.$setValidity(errType, true);
            ).error(->
              ctrl.$setValidity(errType, false)
            )
    }
  ])