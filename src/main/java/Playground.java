import com.alibaba.fastjson.JSON;
import json.support.DefaultJsonParser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pojo.User;

public class Playground {
    @Getter
    @Setter
    @NoArgsConstructor
    static class Shop {
        private int id = 1;
        private Integer num = 1;
        private Shop shop;
        public Shop(Shop shop) {
            this.shop = shop;
        }
    }
    public static void main(String[] args) throws Exception {
        User user = new User.Builder()
                .name("Pony Ma")
                .age(20)
                .build();

        Shop shop1 = new Shop();
        Shop shop2 = new Shop(shop1);
        System.out.println(JSON.toJSONString(shop2));
//
//        Shop shop2 = new Shop(user);
//        Field[] fields = Shop.class.getDeclaredFields();
//        for (Field field : fields) {
//            Object value = field.get(shop2);
//            System.out.println(value);
//        }
        System.out.println();
        System.out.println();
        System.out.println(new DefaultJsonParser().parseToJsonString(shop2));
        System.out.println("" instanceof Object);
    }
}
