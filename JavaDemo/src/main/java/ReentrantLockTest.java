import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        test2();
    }

    /**
     * 说明：测试ReentrantLock的公平锁和非公平锁
     */
    public static void test1() {
        new Thread(() -> test(), "线程A").start();
        new Thread(() -> test(), "线程B").start();
        new Thread(() -> test(), "线程C").start();
        new Thread(() -> test(), "线程D").start();
        new Thread(() -> test(), "线程E").start();
    }

    /**
     * 说明：测试ReentrantLock的lockInterruptibly
     */
    public static void test2() {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        Thread t1 = new Thread(new ThreadDemo(lock1, lock2), "线程1");
        Thread t2 = new Thread(new ThreadDemo(lock2, lock1), "线程2");
        t1.start();
        t2.start();
        t1.interrupt();
    }


    public static void test() {
        for (int i = 0; i < 2; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                Thread.sleep(2);
            }catch (Exception e) {

            }finally {
                lock.unlock();
            }
        }
    }

}
