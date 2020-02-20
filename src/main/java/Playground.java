import com.alibaba.fastjson.JSON;
import json.support.DefaultJsonParser;
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

        System.out.println(new DefaultJsonParser().parseToJsonString(student));

        MapClass mapClass = new MapClass();
        List<School> schoolList = Arrays.asList(scu, bju);
        mapClass.setList(schoolList);

        System.out.println(JSON.toJSONString(mapClass));

        System.out.println(new DefaultJsonParser().parseToJsonString(mapClass));
    }
    @Data
    @NoArgsConstructor
    private static class MapClass {
        private Set<Integer> set = new HashSet<>();
//        private Map<Integer, String> map = new HashMap<>();
        private List<School> list;
        {
//            map.put(1, "one");
//            map.put(2, "two");
            set.add(3);
            set.add(4);
        }
    }
}
