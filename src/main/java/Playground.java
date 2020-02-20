import com.alibaba.fastjson.JSON;
import json.support.DefaultJsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojo.Building;
import pojo.School;
import pojo.Student;

public class Playground {
    @Getter
    @Setter
    @NoArgsConstructor
    static class Shop {
        private int id = 1;
        private Integer num = 1;
        private Shop[] shops;
        public Shop(Shop[] shops) {
            this.shops = shops;
        }
    }
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
    }
}
