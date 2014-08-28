/**
 * xiongjie on 14-7-23.
 */
package dnt.itsnow.model;

/**
 * <h1>The user group</h1>
 *
 * group作为配置项
 *
 * group#name == group#groupName
 *
 * TODO 添加测试用例
 * TODO add group to members support
 */
public class Group extends ConfigItem {

    public String getGroupName(){
        return super.getName();
    }

    public void setGroupName(String name){
        setName(name);
    }
}
