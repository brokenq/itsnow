angular.module('Lib.Filters', [])

  .filter 'round', ->
    (num)->
      return Math.round num

  .filter 'formatHostStatus', ->
    (status) ->
      return "规划中" if status == 'Planing'
      return "运行中" if status == 'Running'
      return "有故障" if status == 'Abnormal'
      return "已关机" if status == 'Shutdown'

  .filter 'formatHostType', ->
    (type) ->
      return "数据库主机" if type is 'DB'
      return "应用主机" if type is 'APP'
      return "综合主机" if type is 'COM'