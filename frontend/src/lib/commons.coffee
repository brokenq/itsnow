angular.module('Lib.Commons', ['ngTable'])

  .factory('CommonService', ['$rootScope', '$location', 'ngTableParams', \
                             ($rootScope,   $location,   ngTableParams)->
    {
      ### @function selectionWatch: | watch checkbox
          @param selection | json object that have two keys: checked, items
          @param datas | datas of table
          @param key | key of selection items ###
      watchSelection: (selection, datas, key)->
        $rootScope.$watch( ->
          return selection.checked
        , (value)->         # watch for check all checkbox
          angular.forEach datas, (data)->
              selection.items[data[key]] = value if data[key]?
          )

        $rootScope.$watchCollection( ->        # watch for check single checkbox
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

  .factory('CacheService', [->
    # 提供本地缓存功能, 本地缓存的数据可以多于ng table显示的当前页的数据(也就是ListCtrl的数据)
    #
    #   甚至可以考虑，是不是将selection service 也由这里负责
    #    好处是selection可以跨页
    #    坏处是selection跨页之后，用户行为的预期变化了(实际选中的数据与用户看到的不一样),需要额外的机制告知用户
    #  selection是否跨页这个问题不应该丢给具体开发人员，应该在产品/平台层面统一
    #  当前的结论是：不支持这个高级特性，尚未到usibility这个阶段
    class CacheService

      constructor: (@key, @callback) ->
        @records = []
        # 这两个对外的函数，需要bind实例
        @find = @findImpl.bind(this)
        @cache = @cacheImpl.bind(this)
        @expire = @expireImpl.bind(this)
      findInLocal: (key) ->
        return record for record in @records when (record[@key]).toString() == key.toString()
      findFromRemote: (key) ->
        throw "Can't get record from remote API" unless @callback
        found = @callback(key)
        @cache(found) if found
        found
      findImpl: (key, fetch) ->
        local = @findInLocal(key)
        return local if local
        if fetch
          remote = @findFromRemote(key)
          remote || throw "Can't find the local/remote record with " + @key + " = " + key
        else
          throw "Can't find the local record with " + @key + " = " + key
      cacheImpl: (records)->
        records = [records] unless Array.isArray records
        @expire(records)
        jQuery.merge(@records, records)
      expireImpl: (records)->
        records = [records] unless Array.isArray records
        cache = this
        for record in records
          @records.remove (exist)->
            record[cache.key].toString() == exist[cache.key].toString()


  ])


  .factory('SelectionService', [ '$rootScope', \
                                 ($rootScope)->

    class SelectionService
      constructor: (@datas, @key) ->
        @checked = false
        @items = {}
        @watch()

      watch: ->
        instance = this
        $rootScope.$watch( ->
          return  instance.checked
        , (value)->         # watch for check all checkbox
          angular.forEach instance.datas, (data)->
            instance.items[data[instance.key]] = value if data[instance.key]?
        )

        $rootScope.$watchCollection( ->        # watch for check single checkbox
          return instance.items
        , ->
          return if !instance.datas? or instance.datas.length is 0
          checked = 0
          unchecked = 0
          total = instance.datas.length
          angular.forEach instance.datas, (data)->
            checked += instance.items[data[instance.key]] || 0
            unchecked += !instance.items[data[instance.key]] || 0
          instance.checked = (checked == total) if unchecked is 0 or checked is 0
          $('#select_all').prop('indeterminate', (checked isnt 0 and unchecked isnt 0))
        , true
        )



  ])
