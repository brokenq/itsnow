angular.module('MscIndex.Templates', ['contract/list-contract.tpl.html', 'sla/list-sla.tpl.html', 'user/list-user.tpl.html']);

angular.module("contract/list-contract.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("contract/list-contract.tpl.html",
    "<!--<div ng-app=\"user\">-->\n" +
    "    <div ng-controller=\"ContractListCtrl\">\n" +
    "        <div class=\"gridStyle\" ng-grid=\"gridContractList\"></div>\n" +
    "    </div>\n" +
    "<!--</div>-->");
}]);

angular.module("sla/list-sla.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("sla/list-sla.tpl.html",
    "<!--<div ng-app=\"user\">-->\n" +
    "    <div ng-controller=\"SlaListCtrl\">\n" +
    "        <div class=\"gridStyle\" ng-grid=\"gridSlaList\"></div>\n" +
    "    </div>\n" +
    "<!--</div>-->");
}]);

angular.module("user/list-user.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("user/list-user.tpl.html",
    "<!--<div ng-app=\"user\">-->\n" +
    "    <div ng-controller=\"UserListCtrl\">\n" +
    "        <div class=\"gridStyle\" ng-grid=\"gridUserList\"></div>\n" +
    "    </div>\n" +
    "<!--</div>-->");
}]);
