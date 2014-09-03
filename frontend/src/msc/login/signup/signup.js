/**
 * Signup Module in MSC Login Application.
 */
angular.module( 'MscLogin.Signup', [
  'ui.router',
  'ui.bootstrap',
  'Itsnow.Security'
])

  .config(function( $stateProvider ) {
    $stateProvider.state( 'signup', {
      url: '/signup',
      views: {
        "login": {
          controller: 'SignupCtrl as signup',
          templateUrl: 'signup/signup.tpl.html'
        }
      },
      data:{ pageTitle: '注册' }
    });
  })

  .factory('AccountService', ['$resource', function($resource){
    return $resource('/api/accounts');
  }])

  .controller( 'SignupCtrl', ['$scope', 'AccountService',
    function( $scope, accountService ) {
      var registration = {
        type: "enterprise",
        asUser: true,
        asProvider: false,
        account: {},
        user: {},
        attachments: {/*营业执照(yyzz), 税务登记证(rwdjz), 个人身份证(id_card)*/},
        individual: function(){
          return this.type == 'individual';
        },
        nameLabel : function(){
          if( this.individual() ){
            return "个人姓名";
          }else{
            return "企业名称";
          }
        },
        namePlaceholder: function(){
          if( this.individual() ){
            return "您的真实姓名";
          }else{
            return "企业的工商注册名称";
          }
        },
        typeHelp : function(){
          if( this.individual() ){
            return "个人需要提供身份证复印件";
          }else{
            return "企业账户需要提供两证";
          }
        }
      };
      var enterpriseRole = {asUser: true, asProvider: false};
      $scope.acceptLicense = true;
      $scope.registration = registration;
      $scope.enterpriseRole = enterpriseRole;
      //TODO 1. 改为 directive(A): groupStyle
      //TODO 2. 增加一个根据状态改变tip的directive(E): inputTip
      $scope.inputStyle = function(formName, fieldName){
        var form = $scope[formName];
        var field = form[fieldName];
        if(field.$dirty) {
          return field.$invalid ? 'has-error' : 'has-success';
        }else{
          return 'has-info';
        }
      };
      $scope.$watch('registration.type', function(newValue, oldValue){
        if(registration.individual()){
          enterpriseRole.asUser = registration.asUser;
          enterpriseRole.asProvider = registration.asProvider;
          registration.asUser = false;
          registration.asProvider = true;
        }else{
          registration.asUser = enterpriseRole.asUser;
          registration.asProvider = enterpriseRole.asProvider;
        }
      });
      $scope.submit = function(success,failure){
        //TODO 正则表达式没完全生效
        //  1. domain中的.都没有被检查出来
        //  2. 重复密码没有
        //  3. 电话号码是 区号+7-8位固话 或 11 位手机号码的pattern没校验出来
        //TODO 文件信息没能上传上来
        //submit the registration information to server side by user service
        accountService.save(registration, success, failure);
      };
      $scope.feedback = function(message, title){
        //TODO 给出更合适的反馈方式
        alert(message);
      };
      $scope.$on('$viewContentLoaded', function(e){
        // TODO 本来应该在 form update的时候，修改 button的状态
        // 但研读 fuelux的源码，控制是否可以走到下一步的方法为监听此地事件
        $('#fuelux-wizard').on('actionclicked.fu.wizard', function (e, info) {
          //validate step input, and control the next button status
          if( info.direction == "next"){
            var formController =  $scope['step' + info.step + 'Form'];
            //TODO 第二步的form/field在没输入的时候，没有验证
            if( formController.$invalid || (info.step < 3 && formController.$pristine)){
              e.preventDefault();
              $scope.feedback('输入有误!');
            }
          }
        }).on('finished.fu.wizard', function (e, info) {
          if($scope.acceptLicense){
            $scope.submit(function(){//success callback
              //先显示对话框，告知用户已经注册完成，等待通知
              $scope.feedback("您已经完成注册", "谢谢!");
              //而后将界面重定向到首页，避免用户在原界面中再次注册
              window.location = "/";
            }, function(){ // failure callback
              //TODO 判断错误原因，而后使用 $wizard('selectedItem', {step: x}); 返回特定步骤
              // 并且，需要把服务器端的各种错误原因(account.domain: 如输入的子域名为保留的子域名)
              // 对应到 $scope.registration中
              // 以便更新 相应form的group/field的css/tips
            });
          }else{
            $scope.feedback('必须接受使用协议');
          }
        });
      });
    }])
;
