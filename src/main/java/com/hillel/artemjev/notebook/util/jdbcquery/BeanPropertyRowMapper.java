package com.hillel.artemjev.notebook.util.jdbcquery;

import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BeanPropertyRowMapper<T> implements RowMapper<T> {
    private final Class<T> resultClass;

    @Override
    public T map(ResultSet rs) {
        try {
            T obj = resultClass.getConstructor().newInstance();

            List<String> columns = getColumns(rs);

            List<String> fields = Arrays.stream(resultClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());

            for (String column : columns) {
                String camelCaseColumnName = toCamelCase(column);

                if (fields.stream().noneMatch(f -> f.equals(camelCaseColumnName))) {
                    continue;
                }
                Field f = resultClass.getDeclaredField(camelCaseColumnName);
                f.setAccessible(true);
                f.set(obj, rs.getObject(column, f.getType()));
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Problem with mapping selected row to object", e);
        }
    }

    private List<String> getColumns(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        List<String> columns = new ArrayList<>(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            columns.add(rs.getMetaData().getColumnName(i));
        }
        return columns;
    }

    private String toCamelCase(String column) {
        Pattern pattern = Pattern.compile("\\_([a-z])");
        Matcher matcher = pattern.matcher(column);
        return matcher.replaceAll(res -> res.group(1).toUpperCase());
    }
}
