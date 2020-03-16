package classLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ShaoJiale
 * Date: 2020/3/16
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream in = this.getClass().getResourceAsStream(fileName);
                    if (in == null) {
                        return super.loadClass(name);
                    }
                    byte[] fileBytes = new byte[in.available()];
                    in.read(fileBytes);
                    return defineClass(name, fileBytes, 0, fileBytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        String fileName = "classLoader.ClassLoaderTest";
        Object obj = myLoader.loadClass(fileName).getDeclaredConstructor().newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
    }
}
