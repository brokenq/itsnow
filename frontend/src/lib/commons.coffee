angular.module('Lib.Commons', ['ngTable'])

  .factory('CommonService', ['$rootScope', '$location', 'ngTableParams', \
                             ($rootScope,   $location,   ngTableParams)->
    {
      ### @function selectionWatch: | watch checkbox
          @param selection | json object that have two keys: checked, items
          @param datas | datas of table
          @param key | key of selection items ###
      watchSelection: (selection, datas, key)->
        $rootScope.$watch(selection.checked, ->         # watch for check all checkbox
        angular.forEach datas, (data)->
            selection.items[data[key]] = value if data[key]?
        )

        $rootScope.$watchCollection(()->        # watch for check single checkbox
          return selection.items
        , ->
          return if !datas? or datas.length is 0
          checked = 0
          unchecked = 0
          total = datas.length
          angular.forEach datas, (data)->
            checked += selection.items[data[key]] || 0
            unchecked += !selection.items[data[key]] || 0
          selection.checked = (checked == total) if unchecked is 0 or checked is 0
          $('#select_all').prop('indeterminate', (checked isnt 0 and unchecked isnt 0))
        , true
        )

      ### @function instanceTable: | instantiate ngTableParams
          @param service | the service function to get datas
          @param datas | datas of table
      ###
      instanceTable: (service, datas)->
        options =
          page:  1,           # show first page
          count: 10           # count per page
        args =
          total: 0,
          getData: ($defer, params) ->
            $location.search params.url() # put params in url
            service.query params.url(), (response, headers) ->
              params.total headers 'total'
              $defer.resolve angular.forEach response, (data)->
                  datas.push data
        return new ngTableParams angular.extend(options, $location.search()), args

    }
  ])