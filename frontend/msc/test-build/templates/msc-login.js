angular.module('MscLogin.Templates', ['signup/signup.tpl.html']);

angular.module("signup/signup.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("signup/signup.tpl.html",
    "<div id=\"signup-box\" class=\"signup-box visible widget-box no-border\">\n" +
    "<div class=\"widget-body\">\n" +
    "<div class=\"widget-main fuelux\">\n" +
    "<h4 class=\"header green lighter bigger\">\n" +
    "  <i class=\"icon-group blue\"></i>\n" +
    "  注册帐户\n" +
    "</h4>\n" +
    "\n" +
    "<div id=\"fuelux-wizard\" class=\"row-fluid wizard\"\n" +
    "     data-initialize=\"wizard\" data-adjust=\"false\">\n" +
    "  <ul class=\"steps\">\n" +
    "    <li data-step=\"1\" class=\"active\">\n" +
    "      <span class=\"step\">1</span>\n" +
    "      <span class=\"title\">基本信息</span>\n" +
    "    </li>\n" +
    "\n" +
    "    <li data-step=\"2\">\n" +
    "      <span class=\"step\">2</span>\n" +
    "      <span class=\"title\">管理员信息</span>\n" +
    "    </li>\n" +
    "\n" +
    "    <li data-step=\"3\">\n" +
    "      <span class=\"step\">3</span>\n" +
    "      <span class=\"title\">凭证信息</span>\n" +
    "    </li>\n" +
    "\n" +
    "    <li data-step=\"4\">\n" +
    "      <span class=\"step\">4</span>\n" +
    "      <span class=\"title\">完成注册</span>\n" +
    "    </li>\n" +
    "  </ul>\n" +
    "\n" +
    "  <hr/>\n" +
    "\n" +
    "  <div id=\"step-container\" class=\"step-content row-fluid position-relative\">\n" +
    "\n" +
    "  <div id=\"step1\" class=\"step-pane active\" data-step=\"1\">\n" +
    "\n" +
    "    <form name=\"step1Form\" novalidate class=\"form-horizontal\" role=\"form\">\n" +
    "      <div class=\"form-group has-info\">\n" +
    "        <label class=\"col-xs-12 col-sm-3 control-label no-padding-right\"> 帐户类型 </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5 radio\">\n" +
    "          <label>\n" +
    "            <input id=\"registration_type_enterprise\"\n" +
    "                   name=\"registration.type\"\n" +
    "                   ng-model=\"registration.type\"\n" +
    "                   value=\"Enterprise\" type=\"radio\" class=\"ace\"/>\n" +
    "            <span class=\"lbl\">企业</span>\n" +
    "          </label>\n" +
    "          <label>\n" +
    "            <input id=\"registration_type_individual\"\n" +
    "                   name=\"registration.type\"\n" +
    "                   ng-model=\"registration.type\"\n" +
    "                   value=\"Individual\" type=\"radio\" class=\"ace\"/>\n" +
    "            <span class=\"lbl\">个人</span>\n" +
    "          </label>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          {{registration.typeHelp()}}\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group has-info\">\n" +
    "        <label class=\"col-xs-12 col-sm-3 control-label no-padding-right\"> 角色选择 </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5 checkbox\">\n" +
    "          <label>\n" +
    "            <input id=\"registration_as_user\"\n" +
    "                   name=\"registration.asUser\"\n" +
    "                   ng-model=\"registration.asUser\"\n" +
    "                   ng-disabled=\"registration.individual()\"\n" +
    "                   value=\"user\" type=\"checkbox\" class=\"ace\"/>\n" +
    "            <span class=\"lbl\">服务使用方</span>\n" +
    "          </label>\n" +
    "          <label>\n" +
    "            <input id=\"registration_as_provider\"\n" +
    "                   name=\"registration.asProvider\"\n" +
    "                   ng-model=\"registration.asProvider\"\n" +
    "                   ng-disabled=\"registration.individual()\"\n" +
    "                   value=\"provider\" type=\"checkbox\" class=\"ace\"/>\n" +
    "            <span class=\"lbl\">服务供应方</span>\n" +
    "          </label>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          选择您需要在itsnow平台完成的工作\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step1Form','registration.account.name')\">\n" +
    "        <label class=\"col-xs-12 col-sm-3 control-label no-padding-right\"> {{registration.nameLabel()}} </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5 checkbox\">\n" +
    "          <span class=\"block input-icon input-icon-right\">\n" +
    "            <input id=\"registration_account_name\"\n" +
    "                   name=\"registration.account.name\"\n" +
    "                   ng-model=\"registration.account.name\"\n" +
    "                   ng-required\n" +
    "                   ng-minlength=\"2\"\n" +
    "                   ng-maxlength=\"50\"\n" +
    "                   type=\"text\" class=\"form-control\" placeholder=\"{{registration.namePlaceholder()}}\"/>\n" +
    "            <i class=\"icon-group\"></i>\n" +
    "          </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          请设置{{registration.nameLabel()}}\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step1Form','registration.account.domain')\">\n" +
    "        <label class=\"col-xs-12 col-sm-3 control-label no-padding-right\"> 自定义域名 </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "          <div class=\"input-group\">\n" +
    "            <div class=\"input-group-addon\">http://</div>\n" +
    "            <input id=\"registration_account_domain\"\n" +
    "                   name=\"registration.account.domain\"\n" +
    "                   ng-model=\"registration.account.domain\"\n" +
    "                   ng-required=\"true\"\n" +
    "                   ng-minlength=\"2\"\n" +
    "                   ng-maxlength=\"10\"\n" +
    "                   ng-pattern=\"/[\\w|\\d|_]+/\"\n" +
    "                   type=\"text\" class=\"span2\" placeholder=\"2-10位\"/>\n" +
    "\n" +
    "            <div class=\"input-group-addon\">.itsnow.com</div>\n" +
    "          </div>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          仅由英文,数字，下划线组合\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "\n" +
    "  </div><!-- #step 1 -->\n" +
    "\n" +
    "  <div id=\"step2\" class=\"step-pane\" data-step=\"2\">\n" +
    "\n" +
    "    <form name=\"step2Form\" novalidate\n" +
    "          ng-model-options=\"{ updateOn: 'mousedown blur' }\"\n" +
    "          class=\"form-horizontal\" role=\"form\">\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step2Form','registration.user.username')\">\n" +
    "        <label for=\"registration_user_username\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          用户名\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_user_username\"\n" +
    "                 name=\"registration.user.username\"\n" +
    "                 ng-model=\"registration.user.username\"\n" +
    "                 ng-required\n" +
    "                 ng-minlength=\"4\"\n" +
    "                 ng-maxlength=\"20\"\n" +
    "                 ng-pattern=\"/[\\w|\\d|_|.]+/\"\n" +
    "                 type=\"text\" class=\"form-control\" placeholder=\"用户名\"/>\n" +
    "          <i class=\"icon-user\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          4-20位，英文，数字，下划线，点号组合\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step2Form','registration.user.email')\">\n" +
    "        <label for=\"registration_user_email\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          电子邮箱\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_user_email\"\n" +
    "                 name=\"registration.user.email\"\n" +
    "                 ng-model=\"registration.user.email\"\n" +
    "                 ng-required\n" +
    "                 type=\"email\" class=\"form-control\" placeholder=\"someone@company.com\"/>\n" +
    "          <i class=\"icon-envelope\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          用于找回密码，接收通知等\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\" ng-class=\"inputStyle('step2Form','registration.user.phone')\">\n" +
    "        <label for=\"registration_user_phone\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          电话号码\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_user_phone\"\n" +
    "                 name=\"registration.user.phone\"\n" +
    "                 ng-model=\"registration.user.phone\"\n" +
    "                 ng-required ng-pattern=\"/[\\d|-]{8,11}/\"\n" +
    "                 type=\"text\" class=\"form-control\" placeholder=\"13912345678\"/>\n" +
    "          <i class=\"icon-headphones\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          帐号审批通过后，会通过该电话与您联系\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\" ng-class=\"inputStyle('step2Form','registration.user.password')\">\n" +
    "        <label for=\"registration_user_password\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          密码\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_user_password\"\n" +
    "                 name=\"registration.user.password\"\n" +
    "                 ng-model=\"registration.user.password\"\n" +
    "                 ng-required ng-minlength=\"6\" ng-maxlength=\"20\"\n" +
    "                 type=\"password\" class=\"form-control\"\n" +
    "                 placeholder=\"数字,字母，符号组合\"/>\n" +
    "          <i class=\"icon-lock\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          设置6-20位密码\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\" ng-class=\"inputStyle('step2Form','registration.user.repeatPassword')\">\n" +
    "        <label for=\"registration_user_repeat_password\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          重复密码\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_user_repeat_password\"\n" +
    "                 name=\"registration.user.repeatPassword\"\n" +
    "                 ng-model=\"registration.user.repeatPassword\"\n" +
    "                 ng-required\n" +
    "                 type=\"password\" class=\"form-control\"\n" +
    "                 placeholder=\"重复密码\"/>\n" +
    "          <i class=\"icon-retweet\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          重复以上密码\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "  </div><!-- #step 2 -->\n" +
    "\n" +
    "  <div id=\"step3\" class=\"step-pane\" data-step=\"3\">\n" +
    "\n" +
    "    <form name=\"step3Form\" novalidate class=\"form-horizontal\" role=\"form\">\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step3Form','registration.attachments.yyzz')\"\n" +
    "           ng-hide=\"registration.individual()\">\n" +
    "        <label for=\"registration_attachments_yyzz\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          营业执照\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_attachments_yyzz\"\n" +
    "                 name=\"registration.attachments.yyzz\"\n" +
    "                 ng-model=\"registration.attachments.yyzz\"\n" +
    "                 type=\"file\" class=\"width-100\">\n" +
    "          <i class=\"icon-briefcase\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          请上传营业执照复印件\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group has-info\"\n" +
    "           ng-class=\"inputStyle('step3Form','registration.attachments.swdjz')\"\n" +
    "           ng-hide=\"registration.individual()\">\n" +
    "        <label for=\"registration_attachments_swdjz\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          税务登记证\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_attachments_swdjz\"\n" +
    "                 name=\"registration.attachments.swdjz\"\n" +
    "                 ng-model=\"registration.attachments.swdjz\"\n" +
    "                 type=\"file\" class=\"width-100\">\n" +
    "          <i class=\"icon-certificate\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          请上传税务登记证复印件\n" +
    "        </div>\n" +
    "      </div>\n" +
    "\n" +
    "      <div class=\"form-group\"\n" +
    "           ng-class=\"inputStyle('step3Form','registration.attachments.id_card')\"\n" +
    "           ng-show=\"registration.individual()\">\n" +
    "        <label for=\"registration_attachments_id_card\" class=\"col-xs-12 col-sm-3 control-label no-padding-right\">\n" +
    "          个人身份证\n" +
    "        </label>\n" +
    "\n" +
    "        <div class=\"col-xs-12 col-sm-5\">\n" +
    "        <span class=\"block input-icon input-icon-right\">\n" +
    "          <input id=\"registration_attachments_id_card\"\n" +
    "                 name=\"registration.attachments.id_card\"\n" +
    "                 ng-model=\"registration.attachments.id_card\"\n" +
    "                 type=\"file\" class=\"width-100\">\n" +
    "          <i class=\"icon-credit-card\"></i>\n" +
    "        </span>\n" +
    "        </div>\n" +
    "        <div class=\"help-block col-xs-12 col-sm-reset inline\">\n" +
    "          请上传个人身份证复印件\n" +
    "        </div>\n" +
    "      </div>\n" +
    "    </form>\n" +
    "  </div><!-- #step 3 -->\n" +
    "\n" +
    "  <div id=\"step4\" class=\"step-pane\" data-step=\"4\">\n" +
    "  <pre>\n" +
    "  {{registration}}\n" +
    "  </pre>\n" +
    "    <form name=\"step4Form\" novalidate class=\"form-horizontal\" role=\"form\">\n" +
    "      <label class=\"block\">\n" +
    "        <input\n" +
    "            id=\"accept_license\"\n" +
    "            name=\"acceptLicense\"\n" +
    "            ng-model=\"acceptLicense\"\n" +
    "            type=\"checkbox\" value=\"accept\" class=\"ace\"/>\n" +
    "        <span class=\"lbl\"> 我接受 <a href=\"#\">使用协议</a> </span>\n" +
    "      </label>\n" +
    "    </form>\n" +
    "  </div><!-- #step 4 -->\n" +
    "\n" +
    "  </div><!--#step container-->\n" +
    "\n" +
    "  <hr/>\n" +
    "\n" +
    "  <div class=\"row-fluid actions\">\n" +
    "    <button class=\"btn btn-prev\" disabled=\"disabled\">\n" +
    "      <i class=\"icon-arrow-left\"></i>\n" +
    "      上一步\n" +
    "    </button>\n" +
    "\n" +
    "    <button class=\"btn btn-success btn-next\" data-last=\"注册\">\n" +
    "      下一步\n" +
    "      <i class=\"icon-arrow-right icon-on-right\"></i>\n" +
    "    </button>\n" +
    "  </div><!-- .wizard-actions -->\n" +
    "\n" +
    "</div><!-- #fuelux-wizard -->\n" +
    "\n" +
    "\n" +
    "\n" +
    "</div><!-- .widget-main -->\n" +
    "\n" +
    "</div><!-- .widget-body -->\n" +
    "\n" +
    "</div><!-- #signup-box -->\n" +
    "");
}]);
