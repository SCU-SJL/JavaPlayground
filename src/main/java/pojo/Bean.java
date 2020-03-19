package pojo;

/**
 * @author ShaoJiale
 * Date: 2020/3/19
 */
public class Bean {
    private static int id = getId();

    static {
        System.out.println("static block");
    }

    private static int getId() {
        System.out.println("static method");
        return 1;
    }
}
