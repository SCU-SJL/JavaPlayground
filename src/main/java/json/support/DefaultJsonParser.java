package json.support;

import json.JsonObject;
import json.JsonParser;
import json.exception.JsonParseException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * The default implementation of {@link JsonParser}
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
        ARRAY_TYPE.add(String[].class);
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

            if (value == null) {
                result.append("null");
                if (i < fields.length - 1) {
                    result.append(",");
                }
                continue;
            }

            if (COMMON_TYPE.contains(fieldClass)) {
                if (fieldClass.equals(String.class)) {
                    result.append("\"")
                            .append(value)
                            .append("\"");
                } else {
                    result.append(value);
                }
            } else if (fieldClass.isArray()) {  // if the current field is a array
                result.append("[");
                Object[] array = (Object[])value;
                if (ARRAY_TYPE.contains(fieldClass)) {
                    if (String[].class.equals(fieldClass)) { // if it's a String array
                        for (int j = 0; j < array.length; j++) {
                            result.append("\"")
                                    .append(array[j])
                                    .append("\"");
                            if (j < array.length - 1) {
                                result.append(",");
                            }
                        }
                    } else {    // if it's a basic array
                        for (int j = 0; j < array.length; j++) {
                            result.append(array[j]);
                            if (j < array.length - 1) {
                                result.append(",");
                            }
                        }
                    }
                } else {    // if it's an array of beans
                    for (int j = 0; j < array.length; j++) {
                        result.append(parseToJsonString(array[j]));
                        if (j < array.length - 1) {
                            result.append(",");
                        }
                    }
                }
                result.append("]");
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
