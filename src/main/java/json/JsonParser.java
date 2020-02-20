package json;

import json.exception.JsonParseException;

import java.util.List;

public interface JsonParser {
    Object parseToObject(String jsonStr, Class<?> targetClass);

    String parseToJsonString(Object bean) throws JsonParseException;

    JsonObject<?> parseToJsonObject(Object bean);
}
