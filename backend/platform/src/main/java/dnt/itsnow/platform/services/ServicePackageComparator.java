/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.platform.services;

import java.io.File;
import java.util.Comparator;

/**
* <h1>Class Usage</h1>
*/
class ServicePackageComparator implements Comparator<File> {
    @Override
    public int compare(File jar1, File jar2) {
        return order(jar1.getName()) - order(jar2.getName());
    }

    private int order(String name){
        int base = baseOrder(name);
        int sub = subOrder(name);
        return base + sub;
    }

    private int baseOrder(String name){
        if( name.contains("common-") ){
            return 10;
        } else if( name.contains("general-")) {
            return 30;
        }else if (name.contains("mutable-")){
            return 50;
        }else if( name.contains("demo-")) {
            return 70;
        }else {
            return 90;
        }
    }

    private int subOrder(String name){
        if( name.contains("gui-service")) return 1;
        if( name.contains("account-service")) return 2;
        if( name.contains("user-service")) return 3;
        if( name.contains("acl-service")) return 4;
        if( name.contains("service_catalog")) return 5;
        if( name.contains("sla-service")) return 6;
        if( name.contains("contract-service")) return 7;
        if( name.contains("staff-service")) return 8;
        return 10;
    }

}
