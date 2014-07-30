/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.model;

/**
 * 应该仅有一个实例的MSC帐号
 */
public class MscAccount extends Account {
    @Override
    public String getType() {
        return "msc";
    }
}
