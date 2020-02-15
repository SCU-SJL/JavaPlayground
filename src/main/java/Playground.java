import pojo.User;

public class Playground {
    public static void main(String[] args) {
        User user = new User.Builder()
                .name("ShaoJiale")
                .age(20)
                .build();
        System.out.println(user);
    }
}
