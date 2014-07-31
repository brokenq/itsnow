/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

/**
 * <h1>服务采购方账号</h1>
 */
public class MsuAccount extends Account{
    @Override
    public String getType() {
        return "msu";
    }
}
