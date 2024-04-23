package com.example.mainservice.infrastructure.typehandler;

import com.example.mainservice.domain.model.valueobject.ValueObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringValueObjectTypeHandler<T extends ValueObject<String>> extends BaseTypeHandler<T> {
    private final Class<T> valueObjectClass;

    public StringValueObjectTypeHandler(Class<T> valueObjectClass) {
        this.valueObjectClass = valueObjectClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.value());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return cs.wasNull() ? null : createValueObject(value);
    }

    private T createValueObject(String value) {
        try {
            return valueObjectClass.getConstructor(String.class).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ValueObject instance", e);
        }
    }
}
