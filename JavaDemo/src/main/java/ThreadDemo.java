import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class ThreadDemo implements Runnable{

    Lock firstLock;
    Lock secondLock;

    public ThreadDemo(Lock firstLock, Lock secondLock) {
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        try {
            firstLock.lockInterruptibly();
            TimeUnit.MILLISECONDS.sleep(10);
            secondLock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            firstLock.unlock();
            secondLock.unlock();
            System.out.println(Thread.currentThread().getName() + "获取到了资源，已经结束");
        }
    }

}
