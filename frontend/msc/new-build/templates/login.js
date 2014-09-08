angular.module('Login.Templates', ['authenticate/authenticate.tpl.html', 'forgot/forgot.tpl.html']);

angular.module("authenticate/authenticate.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("authenticate/authenticate.tpl.html",
    "<div id=\"login-box\" class=\"login-box visible widget-box no-border\">\n" +
    "  <div class=\"widget-body\">\n" +
    "    <div class=\"widget-main\">\n" +
    "      <h4 class=\"header blue lighter bigger\">\n" +
    "        <i class=\"icon-coffee green\"></i>\n" +
    "        请输入用户名/密码登录系统\n" +
    "      </h4>\n" +
    "\n" +
    "      <h5 class=\"header red\">\n" +
    "        <span ng-show=\"error\">{{error}}</span>\n" +
    "      </h5>\n" +
    "\n" +
    "      <form novalidate>\n" +
    "        <fieldset>\n" +
    "          <label class=\"block clearfix\">\n" +
    "            <span class=\"block input-icon input-icon-right\">\n" +
    "              <input type=\"text\" ng-model=\"credential.username\" ng-required=\"true\" ng-minlength=\"3\" ng-maxlength=\"20\" class=\"form-control\" placeholder=\"用户名\"/>\n" +
    "              <i class=\"icon-user\"></i>\n" +
    "            </span>\n" +
    "          </label>\n" +
    "\n" +
    "          <label class=\"block clearfix\">\n" +
    "            <span class=\"block input-icon input-icon-right\">\n" +
    "              <input type=\"password\" ng-model=\"credential.password\" ng-required=\"true\" ng-minlength=\"4\" ng-maxlength=\"12\" class=\"form-control\" placeholder=\"密码\"/>\n" +
    "              <i class=\"icon-lock\"></i>\n" +
    "            </span>\n" +
    "          </label>\n" +
    "\n" +
    "          <div class=\"space\"></div>\n" +
    "\n" +
    "          <div class=\"clearfix\">\n" +
    "            <label class=\"inline\">\n" +
    "              <input ng-model=\"credential.remember\" type=\"checkbox\" class=\"ace\"/>\n" +
    "              <span class=\"lbl\"> 一个月内不再重复登录</span>\n" +
    "            </label>\n" +
    "\n" +
    "            <button type=\"button\" ng-click=\"challenge()\" class=\"width-35 pull-right btn btn-sm btn-primary\">\n" +
    "              <i class=\"icon-key\"></i>\n" +
    "              登录\n" +
    "            </button>\n" +
    "          </div>\n" +
    "\n" +
    "          <div class=\"space-4\"></div>\n" +
    "        </fieldset>\n" +
    "      </form>\n" +
    "\n" +
    "    </div>\n" +
    "    <!-- /widget-main -->\n" +
    "\n" +
    "    <div class=\"toolbar clearfix\">\n" +
    "      <div>\n" +
    "        <a ui-sref=\"forgot\"  class=\"forgot-password-link\">\n" +
    "          <i class=\"icon-arrow-left\"></i>\n" +
    "          忘记密码\n" +
    "        </a>\n" +
    "      </div>\n" +
    "\n" +
    "      <div>\n" +
    "        <a ui-sref=\"signup\" class=\"user-signup-link\">\n" +
    "          注册帐户\n" +
    "          <i class=\"icon-arrow-right\"></i>\n" +
    "        </a>\n" +
    "      </div>\n" +
    "    </div>\n" +
    "  </div>\n" +
    "  <!-- /widget-body -->\n" +
    "</div><!-- /login-box -->\n" +
    "");
}]);

angular.module("forgot/forgot.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("forgot/forgot.tpl.html",
    "<div id=\"forgot-box\" class=\"forgot-box visible widget-box no-border\">\n" +
    "  <div class=\"widget-body\">\n" +
    "    <div class=\"widget-main\">\n" +
    "      <h4 class=\"header red lighter bigger\">\n" +
    "        <i class=\"icon-key\"></i>\n" +
    "        找回密码\n" +
    "      </h4>\n" +
    "\n" +
    "      <div class=\"space-6\"></div>\n" +
    "      <p>\n" +
    "        输入您的邮件地址找回密码\n" +
    "      </p>\n" +
    "\n" +
    "      <form>\n" +
    "        <fieldset>\n" +
    "          <label class=\"block clearfix\">\n" +
    "            <span class=\"block input-icon input-icon-right\">\n" +
    "              <input type=\"email\" class=\"form-control\" placeholder=\"Email\" />\n" +
    "              <i class=\"icon-envelope\"></i>\n" +
    "            </span>\n" +
    "          </label>\n" +
    "\n" +
    "          <div class=\"clearfix\">\n" +
    "            <button type=\"button\" class=\"width-35 pull-right btn btn-sm btn-danger\">\n" +
    "              <i class=\"icon-lightbulb\"></i>\n" +
    "              发送密码\n" +
    "            </button>\n" +
    "          </div>\n" +
    "        </fieldset>\n" +
    "      </form>\n" +
    "    </div><!-- /widget-main -->\n" +
    "\n" +
    "    <div class=\"toolbar center\">\n" +
    "      <a ui-sref=\"authenticate\" class=\"back-to-login-link\">\n" +
    "        返回登录\n" +
    "        <i class=\"icon-arrow-right\"></i>\n" +
    "      </a>\n" +
    "    </div>\n" +
    "  </div><!-- /widget-body -->\n" +
    "</div><!-- /forgot-box -->\n" +
    "");
}]);
