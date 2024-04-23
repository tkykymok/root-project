package com.example.mainservice.infrastructure.typehandler;

import com.example.mainservice.domain.model.valueobject.ValueObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerValueObjectTypeHandler<T extends ValueObject<Integer>> extends BaseTypeHandler<T> {
    private final Class<T> valueObjectClass;

    public IntegerValueObjectTypeHandler(Class<T> valueObjectClass) {
        this.valueObjectClass = valueObjectClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.value());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Integer value = rs.getInt(columnName);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Integer value = rs.getInt(columnIndex);
        return rs.wasNull() ? null : createValueObject(value);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Integer value = cs.getInt(columnIndex);
        return cs.wasNull() ? null : createValueObject(value);
    }

    private T createValueObject(Integer value) {
        try {
            return valueObjectClass.getConstructor(Integer.class).newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create ValueObject instance", e);
        }
    }
}
