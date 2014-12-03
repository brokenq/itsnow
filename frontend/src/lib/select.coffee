angular.module('Lib.Select', [])
  .factory('SelectService',[ ->
    class SelectService

      selectOptions = {}
      ###
          options contains:
            elementId: element ID
            datas:
            displayField:
            customId:
      ###
      constructor: (@options)->

      initSingleSelect: ->
        init()

      initMultipleSelect: ->
        @selectOptions.multiple = true
        init()

      init: ->
        $("#{@options.elementId}").select2(@selectOptions);

      wrapselectOptions: ->
        @selectOptions.data = {results: formatDatas @options.datas, @options.displayField, @options.customId}

      formatDatas: (srcDatas, displayField, customId)->
        datas = []
        for data in srcDatas
          id = if customId? then data[customId] else JSON.stringify(data)
          datas.push {id: id, text: "#{data[displayField]}"}
        return datas

  ])