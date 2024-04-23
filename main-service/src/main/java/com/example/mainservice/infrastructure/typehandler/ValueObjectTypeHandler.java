package com.example.mainservice.infrastructure.typehandler;

import com.example.mainservice.domain.model.valueobject.ValueObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValueObjectTypeHandler<T extends ValueObject<V>, V> extends BaseTypeHandler<T> {
    private final Class<T> valueObjectClass;

    public ValueObjectTypeHandler(Class<T> valueObjectClass) {
        this.valueObjectClass = valueObjectClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.value());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        V value = (V) rs.getObject(columnName);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        V value = (V) rs.getObject(columnIndex);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        V value = (V) cs.getObject(columnIndex);
        return cs.wasNull() ? null : createValueObject(value);
    }

    private T createValueObject(V value) {
        try {
            return valueObjectClass.getConstructor(value.getClass()).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ValueObject instance", e);
        }
    }
}