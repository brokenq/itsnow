package dnt.itsnow.util;

import dnt.itsnow.model.DictDetail;
import dnt.support.JsonSupport;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 2014/11/18.
 */
public class DictDetailHandler implements TypeHandler<DictDetail[]> {

    @Override
    public void setParameter(PreparedStatement ps, int i, DictDetail[] parameter, JdbcType jdbcType) throws SQLException {
        if( parameter == null )
            ps.setString(i, null);
        else{
            ps.setString(i, JsonSupport.toJSONString(parameter));
        }
    }

    @Override
    public DictDetail[] getResult(ResultSet rs, String columnName) throws SQLException {
        String raw = rs.getString(columnName);
        return stringToDictDetail(raw);
    }

    @Override
    public DictDetail[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        String raw = rs.getString(columnIndex);
        return stringToDictDetail(raw);
    }

    @Override
    public DictDetail[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String raw = cs.getString(columnIndex);
        return stringToDictDetail(raw);
    }
    protected DictDetail[] stringToDictDetail(String raw) throws SQLException {
        if( raw != null ){
            return JsonSupport.parseJson(raw, DictDetail[].class);
        }
        return null;
    }
}
