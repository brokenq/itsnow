/**
 * Developer: Kadvin Date: 14-9-16 上午9:50
 */
package dnt.itsnow.util;

import dnt.support.JsonSupport;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 负责序列化 Properties
 */
public class PropertiesHandler implements TypeHandler<Properties> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Properties parameter, JdbcType jdbcType) throws SQLException {
        if( parameter == null )
            ps.setString(i, null);
        else{
            ps.setString(i, JsonSupport.toJSONString(parameter));
        }
    }

    @Override
    public Properties getResult(ResultSet rs, String columnName) throws SQLException {
        String raw = rs.getString(columnName);
        return stringToProperties(raw);
    }

    @Override
    public Properties getResult(ResultSet rs, int columnIndex) throws SQLException {
        String raw = rs.getString(columnIndex);
        return stringToProperties(raw);
    }

    @Override
    public Properties getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String raw = cs.getString(columnIndex);
        return stringToProperties(raw);
    }

    protected Properties stringToProperties(String raw) throws SQLException {
        if( raw != null ){
            return JsonSupport.parseJson(raw, Properties.class);
        }
        return null;
    }
}
