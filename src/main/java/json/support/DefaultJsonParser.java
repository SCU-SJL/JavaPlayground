package json.support;

import json.JsonObject;
import json.JsonParser;
import json.exception.JsonParseException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ShaoJiale
 * Date: 2020/2/20
 */
public class DefaultJsonParser implements JsonParser {

    public final static Set<Class<?>> COMMON_TYPE;
    public final static Set<Class<?>> ARRAY_TYPE;

    static {
        COMMON_TYPE = new HashSet<>();
        ARRAY_TYPE = new HashSet<>();
        COMMON_TYPE.add(byte.class);
        COMMON_TYPE.add(boolean.class);
        COMMON_TYPE.add(short.class);
        COMMON_TYPE.add(char.class);
        COMMON_TYPE.add(int.class);
        COMMON_TYPE.add(float.class);
        COMMON_TYPE.add(long.class);
        COMMON_TYPE.add(double.class);
        COMMON_TYPE.add(Byte.class);
        COMMON_TYPE.add(Boolean.class);
        COMMON_TYPE.add(Short.class);
        COMMON_TYPE.add(Character.class);
        COMMON_TYPE.add(Integer.class);
        COMMON_TYPE.add(Float.class);
        COMMON_TYPE.add(Long.class);
        COMMON_TYPE.add(Double.class);
        COMMON_TYPE.add(String.class);
        ARRAY_TYPE.add(byte[].class);
        ARRAY_TYPE.add(boolean[].class);
        ARRAY_TYPE.add(short[].class);
        ARRAY_TYPE.add(char[].class);
        ARRAY_TYPE.add(int[].class);
        ARRAY_TYPE.add(float[].class);
        ARRAY_TYPE.add(long[].class);
        ARRAY_TYPE.add(double[].class);
        ARRAY_TYPE.add(Object[].class);
    }

    @Override
    public String parseToJsonString(Object bean) throws JsonParseException {
        if (bean == null) {
            return null;
        }
        JsonObject<?> jsonObject = parseToJsonObject(bean);
        Field[] fields = jsonObject.getFields();
        StringBuilder result = new StringBuilder("{");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            String name = field.getName();
            Class<?> fieldClass = field.getType();
            Object value;

            try {
                value = field.get(bean);
            } catch (IllegalAccessException ex) {
                throw new JsonParseException("Cannot access this field: [" + field + "]", ex);
            }

            result.append("\"")
                    .append(name)
                    .append("\":");

            if (COMMON_TYPE.contains(fieldClass)) {
                if (fieldClass.equals(String.class)) {
                    result.append("\"")
                            .append(value)
                            .append("\"");
                } else {
                    result.append(value);
                }
            } else if (ARRAY_TYPE.contains(fieldClass)) {
                // TODO resolve array fields
            } else {
                result.append(parseToJsonString(value));
            }

            if (i < fields.length - 1) {
                result.append(",");
            }
        }

        result.append("}");
        return result.toString();
    }

    @Override
    public Object parseToObject(String jsonStr, Class<?> targetClass) {
        return null;
    }

    @Override
    public JsonObject<?> parseToJsonObject(Object bean) {
        return new JsonObject<>(bean);
    }
}
