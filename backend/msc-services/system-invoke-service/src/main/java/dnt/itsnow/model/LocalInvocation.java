/**
 * Developer: Kadvin Date: 14-9-20 上午11:19
 */
package dnt.itsnow.model;

/**
 * <h1>Invoke some executable/script in localhost</h1>
 */
public abstract class LocalInvocation extends SystemInvocation{

    public LocalInvocation() {
        this(System.getProperty("APP_HOME") + "/script/msc");
    }

    public LocalInvocation(String wd) {
        super(wd);
    }

}
