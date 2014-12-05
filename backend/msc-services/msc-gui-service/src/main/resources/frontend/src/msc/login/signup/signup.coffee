#
# Signup Module in MSC Login Application.
#
angular.module( 'MscLogin.Signup', [])
  .config ($stateProvider)->
    $stateProvider.state( 'signup', {
      url: '/signup',
      views: 
        "login": 
          controller: 'SignupCtrl as signup'
          templateUrl: 'signup/signup.tpl.jade'
      ,
      data:{ pageTitle: '注册帐户' }
    });

  .controller('SignupCtrl', ['$scope', '$resource' ,'Feedback', \
                            ( $scope,   $resource,   feedback) ->
    registration =
      type: "Enterprise"
      asUser: true
      asProvider: false
      account: {type: "base"} # Let server side decide real type
      user: {}
      attachments: {} #营业执照(yyzz), 税务登记证(rwdjz), 个人身份证(id_card)
      individual: ->
        this.type == 'Individual'
      serviceRoles: ->
        array = [];
        array.push "服务使用方 " if @asUser
        array.push "服务供应方"  if @asProvider
        array.join(",")
      nameLabel: ->
        if @individual()
          return "个人姓名"
        else
          return "企业名称"
      namePlaceholder: ->
        if @individual()
          return "您的真实姓名"
        else
          return "企业的工商注册名称"
      typeHelp: ->
        if @individual()
          return "个人需要提供身份证复印件"
        else
          return "企业账户需要提供两证"

    enterpriseRole =
      asUser: true
      asProvider: false

    $scope.acceptLicense = true;
    $scope.registration = registration
    $scope.enterpriseRole = enterpriseRole
    #TODO 4. form submit 等于 触发wizard next
    $scope.$watch 'registration.type', (newValue, oldValue)->
      if registration.individual()
        enterpriseRole.asUser = registration.asUser
        enterpriseRole.asProvider = registration.asProvider
        registration.asUser = false
        registration.asProvider = true
      else
        registration.asUser = enterpriseRole.asUser
        registration.asProvider = enterpriseRole.asProvider
    accountService = $resource('/public/accounts')
    $scope.submit = (success,failure)->
      #TODO 文件信息没能上传上来
      #submit the registration information to server side by user service
      accountService.save(registration, success, failure);

    $scope.$on('$viewContentLoaded', (e)->
      # TODO 本来应该在 form update的时候，修改 button的状态
      # 但研读 fuelux的源码，控制是否可以走到下一步的方法为监听此地事件
      $('#fuelux-wizard').on('actionclicked.fu.wizard', (e, info)->
        # validate step input, and control the next button status
        if info.direction == "next"
          formController =  $scope['step' + info.step + 'Form'];
          # TODO 第二步的form/field在没输入的时候，没有验证
          if formController.$invalid || (info.step < 3 && formController.$pristine)
            e.preventDefault();
            feedback.warn('输入有误!');

      ).on('finished.fu.wizard', (e, info)->
        if $scope.acceptLicense
          $scope.submit(-> #success callback
            #先显示对话框，告知用户已经注册完成，等待通知
            feedback.success("您已经完成注册", "谢谢!");
            #而后将界面重定向到首页，避免用户在原界面中再次注册
            window.location = "/";
          ,(response) -> # failure callback
            #response{config-(request), data, status, statusText, headers())
            #TODO 判断错误原因，而后使用 $wizard('selectedItem', {step: x}); 返回特定步骤
            # 并且，需要把服务器端的各种错误原因(account.domain: 如输入的子域名为保留的子域名)
            # 对应到 $scope.registration中
            # 以便更新 相应form的group/field的css/tips
            if response.message? and response.message is "Unexpected end of input"
              feedback.success("您已经完成注册", "谢谢!")
              window.location = "/"
            else feedback.error(response.data, "注册失败")
          )
        else
          feedback.primary('必须接受使用协议')
      )
    )
  ])
