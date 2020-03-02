package concurrent;

/**
 * @author ShaoJiale
 * Date: 2020/3/2
 */
public class SyncTest {
    private String lock = "m";

    public synchronized void print(String s) throws Exception {
        while (true) {
            System.out.println(s);
            Thread.sleep(2000);
        }
    }

    public static void main(String[] args) throws Exception{
        SyncTest sync1 = new SyncTest();
        SyncTest sync2 = new SyncTest();
        sync1.print("sync1");
        sync2.print("sync2");   // never get invoked
    }
}
