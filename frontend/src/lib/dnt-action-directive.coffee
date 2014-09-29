angular.module('dnt.action.directive', [
  'dnt.action.service'
])
	.directive('dntService',[->
		return {
			scope:
        service: '=dntService'
        selectedData: '=dntData'
			transclude: true
			template: '<div ng-transclude></div>'
		}
	])
  .directive('dntFire', ['$parse', ($parse)->
    link = (scope, element, attrs)->
      fn = $parse attrs['dntFire']
      element.on 'click', (e)->
        param =
          weighing: attrs.weighing
          rejectCss: attrs.rejectCss
          requireCss: attrs.requireCss
        scope.actionService.setAttributes(param)
        fn scope, {event: e}
    return {
      link: link
    }
  ])