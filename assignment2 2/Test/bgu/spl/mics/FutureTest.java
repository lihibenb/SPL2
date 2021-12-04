package bgu.spl.mics;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {

    Future<String> future;

    @Test
    public void testGet() {
        future = new Future<>();
        Thread th1 = new Thread(new Runnable() {
            public void run() {
                String result = (String) future.get();
                assertEquals(result, "result");
            }
        });

        Thread th2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    assertFalse(future.isDone());
                }
                future.resolve("result");
                assertTrue(future.isDone());
            }
        });
        th2.start();

        th1.start();
    }

    @Test
    public void testResolve() {
        testGet(); // iff situation with testGet(no way to test get without testing is)
    }

    @Test
    public void testIsDone() {
        future = new Future<>();
        assertFalse(future.isDone());
        future.resolve("T result changed");
        assertTrue(future.isDone());
    }

    public void testTestGet() {
        future = new Future<>();
        Thread publisherTest = new Thread(new Runnable() {
            public void run() {
                future.get(15, TimeUnit.SECONDS);
            }
        });
        publisherTest.start();
        try {
            Thread.sleep(15 * 1000);
            assertFalse(publisherTest.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
