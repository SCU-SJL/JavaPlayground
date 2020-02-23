import com.alibaba.fastjson.JSON;
import json.JsonArray;
import json.JsonObject;
import json.support.DefaultJsonParser;
import json.token.TokenList;
import json.token.Tokenizer;
import json.util.CharReader;
import lombok.Data;
import lombok.NoArgsConstructor;
import pojo.Building;
import pojo.School;
import pojo.Student;

import java.util.*;

public class Playground {
    public static void main(String[] args) throws Exception {
        School scu = new School("Sichuan University", new Building[]{
                new Building("No.1 Teaching Building"),
                new Building("No.2 Teaching Building")
        });
        School bju = new School("Beijing University", new Building[]{
                new Building("No.1 Teaching Building"),
                new Building("No.2 Teaching Building")
        });
        String[] hobbies = {"Basketball", "Swimming"};
        School[] schools = {scu, bju};
        Student student = new Student("Marvin", "20170123", hobbies, schools);

//        System.out.println(new DefaultJsonParser().parseToJsonString(student));

//        MapClass mapClass = new MapClass();
//        List<School> schoolList = Arrays.asList(scu, bju);
//        mapClass.setList(schoolList);

//        System.out.println(JSON.toJSONString(mapClass));

        String json = new DefaultJsonParser().parseToJsonString(student);
        System.out.println(json);
        System.out.println();

        JsonObject<String, Object> jsonObject = new DefaultJsonParser().parseToJsonObject(json);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            System.out.print(entry.getKey() + ":");
            System.out.println(entry.getValue());
        }

    }
    @Data
    @NoArgsConstructor
    private static class MapClass {
        String name = null;
        Boolean flag = false;
        private Set<Integer> set = new HashSet<>();
        private Map<Integer, String> map = new HashMap<>();
//        private List<School> list;
        {
            map.put(1, "one");
            map.put(2, "two");
            set.add(3);
            set.add(4);
        }
    }
}
