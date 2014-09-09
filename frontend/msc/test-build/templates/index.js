angular.module('Index.Templates', ['main/main-container.tpl.html', 'table/itsnow-table.tpl.html']);

angular.module("main/main-container.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("main/main-container.tpl.html",
    "<div class=\"main-container\" id=\"main-container\">\n" +
    "    <script type=\"text/javascript\">\n" +
    "        try {\n" +
    "            ace.settings.check('main-container', 'fixed')\n" +
    "        } catch (e) {\n" +
    "        }\n" +
    "    </script>\n" +
    "\n" +
    "    <div class=\"main-container-inner\">\n" +
    "        <a class=\"menu-toggler\" id=\"menu-toggler\" href=\"#\">\n" +
    "            <span class=\"menu-text\"></span>\n" +
    "        </a>\n" +
    "\n" +
    "        <div class=\"sidebar\" id=\"sidebar\">\n" +
    "            <script type=\"text/javascript\">\n" +
    "                try {\n" +
    "                    ace.settings.check('sidebar', 'fixed')\n" +
    "                } catch (e) {\n" +
    "                }\n" +
    "            </script>\n" +
    "\n" +
    "            <div class=\"sidebar-shortcuts\" id=\"sidebar-shortcuts\">\n" +
    "                <div class=\"sidebar-shortcuts-large\" id=\"sidebar-shortcuts-large\">\n" +
    "                    <button class=\"btn btn-success\">\n" +
    "                        <i class=\"icon-signal\"></i>\n" +
    "                    </button>\n" +
    "\n" +
    "                    <button class=\"btn btn-info\">\n" +
    "                        <i class=\"icon-pencil\"></i>\n" +
    "                    </button>\n" +
    "\n" +
    "                    <button class=\"btn btn-warning\">\n" +
    "                        <i class=\"icon-group\"></i>\n" +
    "                    </button>\n" +
    "\n" +
    "                    <button class=\"btn btn-danger\">\n" +
    "                        <i class=\"icon-cogs\"></i>\n" +
    "                    </button>\n" +
    "                </div>\n" +
    "\n" +
    "                <div class=\"sidebar-shortcuts-mini\" id=\"sidebar-shortcuts-mini\">\n" +
    "                    <span class=\"btn btn-success\"></span>\n" +
    "\n" +
    "                    <span class=\"btn btn-info\"></span>\n" +
    "\n" +
    "                    <span class=\"btn btn-warning\"></span>\n" +
    "\n" +
    "                    <span class=\"btn btn-danger\"></span>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <!-- #sidebar-shortcuts -->\n" +
    "            <!-- Menu Component -->\n" +
    "            <div ng-controller=\"MenuCtrl\">\n" +
    "                <script type=\"text/ng-template\" id=\"nodes_renderer.html\">\n" +
    "                    <a ui-sref=\"{{menu.state}}\" ng-class=\"{'dropdown-toggle':true}\">\n" +
    "                        <i ng-class=\"{'icon-desktop':true}\"></i>\n" +
    "                        <span ng-class=\"{'menu-text':true}\">{{menu.name}}</span>\n" +
    "                        <b ng-class=\"{'arrow icon-angle-down':true}\"></b>\n" +
    "                    </a>\n" +
    "                    <ul ui-tree-nodes ng-model=\"menu.children\" ng-class=\"{'submenu':true}\">\n" +
    "                        <li ng-repeat=\"menu in menu.children\" ui-tree-node ng-include=\"'nodes_renderer.html'\" data-nodrag></li>\n" +
    "                    </ul>\n" +
    "                </script>\n" +
    "                <div ui-tree>\n" +
    "                    <ul ui-tree-nodes ng-model=\"menuList\" id=\"tree-root\" ng-class=\"{'nav':true, 'nav-list':true}\">\n" +
    "                        <li ng-repeat=\"menu in menuList\" ui-tree-node ng-include=\"'nodes_renderer.html'\" data-nodrag></li>\n" +
    "                    </ul>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "\n" +
    "            <!-- /.nav-list -->\n" +
    "\n" +
    "            <div class=\"sidebar-collapse\" id=\"sidebar-collapse\">\n" +
    "                <i class=\"icon-double-angle-left\" data-icon1=\"icon-double-angle-left\" data-icon2=\"icon-double-angle-right\"></i>\n" +
    "            </div>\n" +
    "\n" +
    "            <script type=\"text/javascript\">\n" +
    "                try {\n" +
    "                    ace.settings.check('sidebar', 'collapsed')\n" +
    "                } catch (e) {\n" +
    "                }\n" +
    "            </script>\n" +
    "        </div>\n" +
    "\n" +
    "        <div class=\"main-content\">\n" +
    "            <div class=\"breadcrumbs\" id=\"breadcrumbs\">\n" +
    "                <script type=\"text/javascript\">\n" +
    "                    try {\n" +
    "                        ace.settings.check('breadcrumbs', 'fixed')\n" +
    "                    } catch (e) {\n" +
    "                    }\n" +
    "                </script>\n" +
    "\n" +
    "                <ul class=\"breadcrumb\">\n" +
    "                    <li>\n" +
    "                        <i class=\"icon-home home-icon\"></i>\n" +
    "                        <a href=\"#\">首页</a>\n" +
    "                    </li>\n" +
    "\n" +
    "                    <li>\n" +
    "                        <a href=\"#\">其他</a>\n" +
    "                    </li>\n" +
    "                    <li class=\"active\">空白</li>\n" +
    "                </ul>\n" +
    "                <!-- .breadcrumb -->\n" +
    "\n" +
    "                <div class=\"nav-search\" id=\"nav-search\">\n" +
    "                    <form class=\"form-search\">\n" +
    "								<span class=\"input-icon\">\n" +
    "									<input type=\"text\" placeholder=\"Search ...\" class=\"nav-search-input\" id=\"nav-search-input\"\n" +
    "                                           autocomplete=\"off\"/>\n" +
    "									<i class=\"icon-search nav-search-icon\"></i>\n" +
    "								</span>\n" +
    "                    </form>\n" +
    "                </div>\n" +
    "                <!-- #nav-search -->\n" +
    "            </div>\n" +
    "\n" +
    "            <div class=\"page-content\">\n" +
    "                <div class=\"row\">\n" +
    "                    <div class=\"col-xs-12\">\n" +
    "                        <!-- PAGE CONTENT BEGINS -->\n" +
    "                        <div ui-view></div>\n" +
    "                        <!-- PAGE CONTENT ENDS -->\n" +
    "                    </div>\n" +
    "                    <!-- /.col -->\n" +
    "                </div>\n" +
    "                <!-- /.row -->\n" +
    "            </div>\n" +
    "            <!-- /.page-content -->\n" +
    "        </div>\n" +
    "        <!-- /.main-content -->\n" +
    "\n" +
    "        <div class=\"ace-settings-container\" id=\"ace-settings-container\">\n" +
    "            <div class=\"btn btn-app btn-xs btn-warning ace-settings-btn\" id=\"ace-settings-btn\">\n" +
    "                <i class=\"icon-cog bigger-150\"></i>\n" +
    "            </div>\n" +
    "\n" +
    "            <div class=\"ace-settings-box\" id=\"ace-settings-box\">\n" +
    "                <div>\n" +
    "                    <div class=\"pull-left\">\n" +
    "                        <select id=\"skin-colorpicker\" class=\"hide\">\n" +
    "                            <option data-skin=\"default\" value=\"#438EB9\">#438EB9</option>\n" +
    "                            <option data-skin=\"skin-1\" value=\"#222A2D\">#222A2D</option>\n" +
    "                            <option data-skin=\"skin-2\" value=\"#C6487E\">#C6487E</option>\n" +
    "                            <option data-skin=\"skin-3\" value=\"#D0D0D0\">#D0D0D0</option>\n" +
    "                        </select>\n" +
    "                    </div>\n" +
    "                    <span>&nbsp; 选择主题</span>\n" +
    "                </div>\n" +
    "\n" +
    "                <div>\n" +
    "                    <input type=\"checkbox\" class=\"ace ace-checkbox-2\" id=\"ace-settings-navbar\"/>\n" +
    "                    <label class=\"lbl\" for=\"ace-settings-navbar\"> 固定导航</label>\n" +
    "                </div>\n" +
    "\n" +
    "                <div>\n" +
    "                    <input type=\"checkbox\" class=\"ace ace-checkbox-2\" id=\"ace-settings-sidebar\"/>\n" +
    "                    <label class=\"lbl\" for=\"ace-settings-sidebar\"> 固定侧栏</label>\n" +
    "                </div>\n" +
    "\n" +
    "                <div>\n" +
    "                    <input type=\"checkbox\" class=\"ace ace-checkbox-2\" id=\"ace-settings-breadcrumbs\"/>\n" +
    "                    <label class=\"lbl\" for=\"ace-settings-breadcrumbs\"> 固定上下文</label>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "        <!-- /#ace-settings-container -->\n" +
    "    </div>\n" +
    "    <!-- /.main-container-inner -->\n" +
    "\n" +
    "    <a href=\"#\" id=\"btn-scroll-up\" class=\"btn-scroll-up btn btn-sm btn-inverse\">\n" +
    "        <i class=\"icon-double-angle-up icon-only bigger-110\"></i>\n" +
    "    </a>\n" +
    "</div>");
}]);

angular.module("table/itsnow-table.tpl.html", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("table/itsnow-table.tpl.html",
    "<div class=\"table-responsive\">\n" +
    "    <div id=\"sample-table-2_wrapper\" class=\"dataTables_wrapper\" role=\"grid\">\n" +
    "\n" +
    "        <div class=\"row\">\n" +
    "            <div class=\"col-sm-6\">\n" +
    "                <div id=\"sample-table-2_length\" class=\"dataTables_length\"><label>Search: <input type=\"text\"\n" +
    "                                                                                                ng-model=\"searchKey\"\n" +
    "                                                                                                aria-controls=\"sample-table-2\">\n" +
    "                </label>\n" +
    "\n" +
    "                    <a href=\"javascript:void(0)\" id=\"fbox_grid-table_search\" ng-click=\"searchFun()\"\n" +
    "                       class=\"fm-button ui-state-default ui-corner-all fm-button-icon-right ui-reset btn btn-sm btn-purple\"><span\n" +
    "                            class=\"icon-search\"></span>Find</a>\n" +
    "\n" +
    "                </div>\n" +
    "            </div>\n" +
    "            <div class=\"col-sm-6\">\n" +
    "                <div class=\"dataTables_filter\" id=\"sample-table-2_filter\">\n" +
    "                    <button type=\"button\" class=\"btn btn-sm btn-success\" ng-click=\"newFun()\" id=\"new\" name=\"new\">新建</button>\n" +
    "                    <button type=\"button\" class=\"btn btn-sm btn-primary\" ng-click=\"acceptFun()\" id=\"accept\" name=\"accept\">签收</button>\n" +
    "                    <button type=\"button\" class=\"btn btn-sm btn-warning\" ng-click=\"analysisFun()\" id=\"analysis\" name=\"analysis\">分析\n" +
    "                    </button>\n" +
    "                    <button type=\"button\" class=\"btn btn-sm btn-info\" ng-click=\"processFun()\" id=\"process\" name=\"process\">处理</button>\n" +
    "                    <button type=\"button\" class=\"btn btn-sm btn-danger\" ng-click=\"closeFun()\" id=\"close\" name=\"close\">关闭</button>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "\n" +
    "\n" +
    "        <div class=\"gridStyle\" ng-grid=\"gridUserList\"></div>\n" +
    "    </div>\n" +
    "</div>\n" +
    "");
}]);
