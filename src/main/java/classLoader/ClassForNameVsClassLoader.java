package classLoader;

/**
 * @author ShaoJiale
 * Date: 2020/3/19
 */
public class ClassForNameVsClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        cl.loadClass("pojo.Bean");

        System.out.println("Class for name");
        Class.forName("pojo.Bean");
    }
}
