package json;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShaoJiale
 * Date: 2020/2/20
 */
@Getter
public class JsonObject<K, V> extends HashMap<K, V> {
    public List<Map.Entry<K, V>> getEntries() {
        return new ArrayList<>(this.entrySet());
    }

    public JsonObject<?, ?> getJsonObject(K key) {
        if (!this.containsKey(key)) {
            throw new IllegalArgumentException("Invalid key: '" + key + "'");
        }

        V value = this.get(key);
        if (value instanceof JsonObject) {
            return (JsonObject<?, ?>) value;
        }

        throw new RuntimeException("Value is not a JsonObject");
    }


}
