package concurrent;

/**
 * Use {@code synchronized} to make dead lock.
 *
 * @author ShaoJiale
 * Date: 2020/2/29
 */
public class DeadLock {
    private static final String RESOURCE_A = "A";
    private static final String RESOURCE_B = "B";

    public static void method1() throws InterruptedException {
        synchronized (RESOURCE_A) {
            System.out.println("Method one start sleeping");
            Thread.sleep(1000);
            System.out.println("Method one awake");
            synchronized (RESOURCE_B) {
                System.out.println("Method one complete");
            }
        }
    }

    public static void method2() throws InterruptedException {
        synchronized (RESOURCE_B) {
            System.out.println("Method two start sleeping");
            System.out.println("Method two awake");
            Thread.sleep(1000);
            synchronized (RESOURCE_A) {
                System.out.println("Method two complete");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread("Thread_1") {
            @Override
            public void run() {
                try {
                    method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread2 = new Thread("Thread_2") {
            @Override
            public void run() {
                try {
                    method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
