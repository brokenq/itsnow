###
  ＝＝＝＝＝＝＝＝＝＝＝＝   ActionService   ＝＝＝＝＝＝＝＝＝＝＝＝
  @author: brokenq
  @url: https://github.com/brokenq/actionService.git
  ＝＝＝＝＝＝＝＝＝＝＝＝   Start Guide     ＝＝＝＝＝＝＝＝＝＝＝＝
  ExampleUrl: actionService/app/partials/test/:
                table.jade
                detail.jade
              actionService/app/scripts/test/:
                test.coffee
                detail.coffee
  1.app = angular.module 'module', ['ngTable', 'dnt.action.directive']
    dependency to the 'ngTable' and 'dnt.action.directive'
  2. $scope.ActionService = ActionService.init({scope: $scope, datas: $scope.phones, checkKey: 'age', checkMode: 'checkDatas', mapping: $scope.getPhoneByAge, tableId: '#table'})
    initialize the ActionService and assign to the $scope.ActionService
    you must specify these datas:
      scope: assign the $scope
      datas: the datas of the table
      checkKey: explain with a demo: input(type='checkbox', ng-model="checkDatas.items[phone.age]")
        checkKey is age
        if you change to: input(type='checkbox', ng-model="checkDatas.items[phone.id]")
        checkKey is id
    you can alternative specify these datas:
      checkMode: angular will watch the value change of the checkMode. you can use the default value which is 'checkDatas',
                or you can specify a different string. if you use default value, you can not write this key
      mapping: mapping the function you want to perform when perform the gotoState(state) function, and the mapping function like:
              $scope.getPhoneByAge  = ->
                param = $scope.ActionService.getQueryData()
                return phone for phone in $scope.phones when phone.age is parseInt(param, 10)
              the params you can get from $scope.ActionService.getQueryData(), it's value is one key of the checkDatas.items
      tableId: id of the table, you can use the default valud which is '#table', or you can specify a different id. if you use
              default value, you can not write this key
  3.$scope.checkDatas = $scope.ActionService.getCheckDatas()
    if you use default checkMode, you must assign to the $scope.checkDatas, or you can specify a different checkMode,
    and here assign to the same name, like: $scope.checkboxes = $scope.ActionService.getCheckDatas()  now your checkMode must
    specify the value of 'checkboxes'
  4.div(dnt-service='ActionService')
    use this directive bing to the ActionService data
  5.button(type='button', weighing='1', require-css='approved new', reject-css='msc msp', dnt-fire='ActionService.gotoState("table.detail")') edit
    priority of the attributes: weighing, reject-css, require-css
      weighing: the number of records you must select. it has four kinds of value:
        *: you can select any records or not select
        1: you must select one record
        1+: you must select one record at least
        2: you must select two records
      reject-css: if the class attribute of the selected row contains reject-css, this action won't be performed
      require-css: if the class attribute of the selected row don't contains all of the require-css, this action won't be performed
    dnt-fire: specify the function you want to perform. ActionService provide two functions:
      gotoState(state): redirect to the state page
        state: the state of the page you want to redirect
      perform(callback): perform the callback function you declare in your controller, like:
        button(type='button', weighing='1+', require-css='approved new', reject-css='msc msp', dnt-fire='ActionService.perform(approve)') approve
        $scope.approve = ()->
          alert('approve');

  ＝＝＝＝＝＝＝＝＝＝＝＝     Reference     ＝＝＝＝＝＝＝＝＝＝＝＝
  @function: init | initialize the ActionService
    @param: optionsParam | an json object which keys contains a part of keys of options
    @return ActionService
  @function: gotoState | redirect to the state page
    @param: state
  @function: perform | redirect to the state page
    @param: callback | the callback function
  @function: refresh | refresh the page
###
angular.module('dnt.action.service', [
  'ui.router'
])
  .factory('ActionService', ['$state', ($state)->
    options =
      scope: null # required | assign the $scope of Controller to this
      datas: null # required | the datas of the table
      checkKey: null # required |
      checkModel: 'checkDatas' # not required; default is 'checkDatas' | if you don't specify the checkModel, it'll use the default value, or it'll use the value you specified
      mapping: null # alternative | mapping function: get the object by object's field value
      checkDatas: {'checked': false, items: {}, elements: {}} # not required | the checked datas
      attributes: null # not required | the attributes of the button which you click
      queryData: null # not required
      tableId: '#table' # alternative | bing to the table

    CSS =
      WEIGHING: 'weighing'
      REQUIRE_CSS: 'requireCss'
      REJECT_CSS: 'rejectCss'
    INFO =
      NO_SELECTED: "you don't select any record"
      REJECT_ONE: "this record can't be perform in this action, please check the status"
      REJECT_TWO: "the two records can't be perform in this action, please check the status"
      REJECT_MULTIPLE: "these records can't be perform in this action, please check the status"
      ONLY_ONE: "you can only select one record to perform this action"
      ONLY_TWO: "you must select two records to perform this action"
      ONE_AT_LEAST: "you must select one record at least to perform this action"

    ### @function: init | initialize the ActionService
        @param: optionsParam | an json object which keys contains a part of keys of options
        @return: void ###
    init = (optionsParam)->
      options.scope = optionsParam.scope if optionsParam.scope?
      options.datas = optionsParam.datas if optionsParam.datas?
      options.checkKey = optionsParam.checkKey if optionsParam.checkKey?
      options.mapping = optionsParam.mapping if optionsParam.mapping?
      options.checkModel = optionsParam.checkModel if optionsParam.checkModel?
      options.tableId = optionsParam.tableId if optionsParam.tableId?
      watchAllSelect()
      watchSingleSelect()
      return this

    ### @function: gotoState | redirect to the state page
        @param: state
        @return: void ###
    gotoState = (state)->
      condition = isConditionPass()
      if condition.passed
        options.queryData = condition.datas[0]
        $state.go state, options.scope.$eval(options.mapping)

    ### @function: perform | redirect to the state page
        @param: callback | the callback function
        @return: void ###
    perform = (callback)->
      condition = isConditionPass()
      options.scope.$eval(callback) if condition.passed

    ### @function: refresh | refresh the page
        @return: void ###
    refresh = ()->
      condition = isConditionPass()
      window.location.reload() if condition.passed

    ### @function: watchAllSelect | watch all select
        @return: void ###
    watchAllSelect = ->
      options.scope.$watch "#{options.checkModel}.checked", (value) -> # if value is true, all of the table rows are selected, or none is selected
        angular.forEach options.datas, (item) ->
          options.checkDatas.items[item[options.checkKey]] = value if angular.isDefined(item[options.checkKey])
        for checkbox in $(options.tableId + " tbody [type=checkbox]")
            if value then $(checkbox).attr(checked: 'checked') else $(checkbox).removeAttr('checked')

    ### @function: watchSingleSelect | watch single select
        @return: void ###
    watchSingleSelect = ->
      options.scope.$watch "#{options.checkModel}.items", ->
        return if !options.datas
        checked = 0
        unchecked = 0
        total = options.datas.length
        angular.forEach options.datas, (item)->
          checked   +=  (options.checkDatas.items[item[options.checkKey]]) || 0
          unchecked += (!options.checkDatas.items[item[options.checkKey]]) || 0
        options.checkDatas.checked = (checked == total) if (unchecked == 0) || (checked == 0)

        updateElements()
        angular.element(document.getElementById("select_all")).prop("indeterminate", (checked != 0 && unchecked != 0))
      , true

    ### @function: updateElements | update the selected elements
        @return: void ###
    updateElements = ->
      selectedKeys = []
      selectedKeys.push key for key, isSelected of options.checkDatas.items when isSelected
      options.checkDatas.elements = {}
      if options.checkDatas.checked
        options.checkDatas.elements[selectedKeys[index]] = tr for tr, index in $(options.tableId + ' tbody [checked=checked]').parent().parent()
      else
        options.checkDatas.elements[selectedKeys[index]] = tr for tr, index in $(options.tableId + ' tbody :checked').parent().parent()

    ### @function: isPass | prerequisite which you can perform the action
        @return: an json object; passed: [true: passed; false: not passed]; datas: [the values of the selected records]###
    isConditionPass = ->
      result = {passed: false, datas: []}
      result.datas.push key for key, isSelected of options.checkDatas.items when isSelected
      switch options.attributes[CSS.WEIGHING]
        when '*'
          cssPass = isCssPass(result.datas)
          result.passed = cssPass.passed
          if !result.passed
#            rows = []
#            rows.push($(options.checkDatas.elements[key]).index() + 1) for key, passed of cssPass.datas when !passed
#            alert "[the #{rows} rows] #{INFO.REJECT_MULTIPLE}"
            alert INFO.REJECT_MULTIPLE
        when '1'
          switch result.datas.length
            when 0 then alert INFO.NO_SELECTED
            when 1
              cssPass = isCssPass(result.datas)
              result.passed = cssPass.passed
              alert INFO.REJECT_ONE if !result.passed
            else alert INFO.ONLY_ONE
        when '1+'
          switch result.datas.length
            when 0 then alert INFO.NO_SELECTED
            else
              cssPass = isCssPass(result.datas)
              result.passed = cssPass.passed
              if !result.passed
#                rows = []
#                rows.push($(options.checkDatas.elements[key]).index() + 1) for key, passed of cssPass.datas when !passed
#                alert "[the #{rows} rows] #{INFO.REJECT_MULTIPLE}"
                alert INFO.REJECT_MULTIPLE
        when '2'
          switch result.datas.length
            when 2
              cssPass = isCssPass(result.datas)
              result.passed = cssPass.passed
              alert "#{INFO.REJECT_TWO}" if !result.passed
            else alert INFO.ONLY_TWO
        else

      return result

    ### @function:  isCssPass | is the status correct to perform this action
        @param: keys | the values of the selected records
        @return: an json object; passed: [true: passed; false: not passed]; datas: [the values of the selected records]###
    isCssPass = (keys)->
      result = {passed: true, datas: {}}
      return result if keys.length is 0
      for key in keys
        classes = {}
        classes[css] = css for css in $(options.checkDatas.elements[key]).attr('class').split(/\s+/) # get the class attribute of tr which is selected
        if options.attributes[CSS.REJECT_CSS]?
          for css in String(options.attributes[CSS.REJECT_CSS]).split(/\s+/) # if contains reject css, return false
            if classes[css]? then result.passed = false; break else result.passed = true
        else
          result.passed = true
        if result.passed
          if options.attributes[CSS.REQUIRE_CSS]?
            for css in String(options.attributes[CSS.REQUIRE_CSS]).split(/\s+/) # if not contains all of the require css, return false
              if !classes[css]? then result.passed = false; break else result.passed = true
          else
            result.passed = true
        result.datas[key] = result.passed
      return result

    ### @function: getCheckDatas
        @illustrate: return options.checkDatas
        @return: options.checkDatas ###
    getCheckDatas = ->
      return options.checkDatas

    ### @function: setAttributes
        @illustrate: assigning the value to the options.attributes
        @return: void ###
    setAttributes = (attrs)->
      options.attributes = attrs

    ### @function: getQueryData
        @return: options.queryData ###
    getQueryData = ->
      return options.queryData

    return {
      init: init
      gotoState: gotoState
      perform: perform
      refresh: refresh
      setAttributes: setAttributes
      getCheckDatas: getCheckDatas
      getQueryData: getQueryData
    }
  ])