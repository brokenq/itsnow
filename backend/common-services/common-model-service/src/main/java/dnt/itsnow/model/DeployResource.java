/**
 * Developer: Kadvin Date: 14-9-24 下午1:22
 */
package dnt.itsnow.model;

import java.util.Properties;

/**
 * The deploy resource, such as host, schema, process
 */
public class DeployResource extends ConfigItem {
    //资源的配置项
    // 通用: 用户名，密码
    // 主机: 一般为CPU、内存、磁盘空间大小等
    private Properties configuration;

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }


    public String getProperty(String name){
        if( this.configuration == null ) return null;
        return this.configuration.getProperty(name);
    }

    public void setProperty(String name, String value){
        if( this.configuration == null ) this.configuration = new Properties();
        this.configuration.setProperty(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeployResource)) return false;
        if (!super.equals(o)) return false;

        DeployResource that = (DeployResource) o;

        //noinspection RedundantIfStatement
        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }

    public String getProperty(String name, String defaultValue) {
        String value = getProperty(name);
        return value == null ? defaultValue : value;
    }
}
