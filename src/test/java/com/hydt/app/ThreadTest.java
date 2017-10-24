package com.hydt.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by bean_huang on 2017/10/23.
 */
public class ThreadTest {

    private ExecutorService executorService;

    @Before
    public void setup(){
        executorService = Executors.newFixedThreadPool(10);
    }

    @After
    public void close(){
        executorService.shutdown();
        executorService = null;
    }

    /**
     *CountDownLatch:一种同步帮助，允许一个或多个线程等待直到在其他线程中执行的一组操作完成。
     * CountDownLatch是一个或N个线程在等待“另一类的”一个，而CyclicBarrier是“一类”中的N个相互等待。
     */

    @Test
    public void testCountDownLatch() throws Exception {
        final CountDownLatch cd = new CountDownLatch(5);
        for(int i =0;i<5;i++){
            System.out.println("i:"+i);
            executorService.execute(new Runnable(){
                public void run(){
                    try {
                        //阻塞子线程，直到CountDownLatch计数器减为0
                        cd.await();
                        //cb.await();
                        System.out.println("线程名称"+Thread.currentThread().getName());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            cd.countDown();
            System.out.println("线程名称"+Thread.currentThread().getName() + "   hello countdownlatch……");
        }

        System.in.read();

    }

    @Test
    public void testCyclicBarrier() throws Exception{
        final CyclicBarrier  cb = new CyclicBarrier(5);
        for(int i =0;i<10;i++){
            System.out.println("i:"+i);
            executorService.execute(new Runnable(){
                public void run(){
                    try {
                        //五个子线程相互等待，等都到齐了才开始运行！
                        System.out.println("线程名称"+Thread.currentThread().getName()+"处理完毕，开始等待");
                        cb.await();
                        System.out.println("所有线程处理完毕，"+ Thread.currentThread().getName() + "继续其他后续业务");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("hello countdownlatch……");
        }

        System.in.read();
    }

    /**
     * Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
     */
    @Test
    public void testSemaphore() throws Exception{
        final Semaphore semaphore = new Semaphore(5);
        for(int i =0;i<10;i++){
            System.out.println("i:"+i);
            executorService.execute(new Runnable(){
                public void run(){
                    try {
                        semaphore.acquire();
                        System.out.println("工人:"+Thread.currentThread().getName()+"占用一个机器在生产");
                        Thread.sleep(2000);
                        System.out.println("工人:"+Thread.currentThread().getName()+"释放出机器");
                        semaphore.release();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("hello Semaphore……");
        }

        System.in.read();
    }
}
