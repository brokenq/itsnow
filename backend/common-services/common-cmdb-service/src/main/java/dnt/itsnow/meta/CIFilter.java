/**
 * Developer: Kadvin Date: 14-8-18 上午8:57
 */
package dnt.itsnow.meta;

import dnt.itsnow.model.ConfigItem;

/**
 * The ci filter based on string
 */
public class CIFilter {
    private String rawRule;

    public CIFilter(String rule) {
        this.rawRule = rule;
    }

    public boolean accept(Class<? extends ConfigItem> klass){
        return true;
    }
}
