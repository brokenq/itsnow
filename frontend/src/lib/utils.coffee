angular.module('Lib.Utils', [])
  .factory('Utils', [->
    {
      ### @function: stringFormat | format the string
        @demo: stringFormat("i have {0} {1}", "two", "apples")
          print: i have two apples
        @return: format string###
      stringFormat: ->
        return null if arguments.length is 0
        string = arguments[0]
        for val, i in arguments when i isnt 0
          regx = new RegExp "\\{#{i - 1}\\}", "gm"
          string = string.replace regx, val
        return string
    }
  ])
  .factory('SelectionService', [->
    class SelectionService
      #
      # Options支持的参数：
      #  records: $scope中的对象集合名称,默认即为 records
      #  selection: $scope中的选择集合名称，默认为selection
      #  key: 对象的选择标记字段，默认为id
      #  selectAllId: html中选择input的id，默认为 selectAll
      constructor: ($scope, @options)->
        defaultOptions =
          records: "records"
          selection: "selection"
          key: "id"
          selectAllId : "select_all"
        @options = angular.extend(defaultOptions, @options)
        records = $scope[@options.records]
        selection = $scope[@options.selection]
        key = @options.key
        selectAll = @options.selectAllId
        $scope.$watch @options.selection + '.checked', (value)->
          angular.forEach $scope.accounts, (item)->
            $scope.checkboxes.items[item.sn] = value if angular.isDefined(item.sn)

        # watch for data checkboxes
        $scope.$watch(@options.selection + '.items', (values) ->
          return if !records
          checked = 0
          unchecked = 0
          total = records.length
          angular.forEach records, (item)->
            checked   +=  (selection.items[item[key]]) || 0
            unchecked += (!selection.items[item[key]]) || 0
          selection.checked = (checked == total) if (unchecked == 0) || (checked == 0)
          # grayed checkbox
          angular.element(document.getElementById(selectAll)).prop("indeterminate", (checked != 0 && unchecked != 0));
        , true)
  ])