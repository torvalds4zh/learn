package com.weibangong.mybatis.blueprint.handler;

import com.mysql.jdbc.TimeUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenbo on 16/9/20.
 */
public class FracSecsDateHandler extends BaseTypeHandler {
    protected SimpleDateFormat dateFormat;

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof Date) {
            ps.setString(i, formatDate((Date) parameter));
        } else
            throw new SQLException("Invalid parameter type!");
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

    protected String formatDate(Date parameter) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        StringBuffer buf = new StringBuffer();
        buf.append(dateFormat.format(parameter));
        int nanos = (int) ((parameter.getTime() % 1000) * 1000000);
        if (nanos != 0) {
            buf.append('.');
            //官方驱动这里是通过版本去format的,mysql版本大于5.6.4才取毫秒
            buf.append(TimeUtil.formatNanos(nanos, true, true));
        }
        return buf.toString();
    }

}
