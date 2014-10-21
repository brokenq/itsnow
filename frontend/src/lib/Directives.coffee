
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
            return unless val?
            return if val == ''
            # GET /public/accounts/check/name/$accountName
            # GET /public/accounts/check/domain/$accountDomain
            # GET /public/users/check/username/$username
            # GET /public/users/check/email/$email
            # GET /public/users/check/phone/$phone
            async.get(attrs.ngUnique + val).success(->
              ctrl.$setValidity('unique', true);
            ).error(->
              ctrl.$setValidity('unique', false)
            )
    }
  ])
