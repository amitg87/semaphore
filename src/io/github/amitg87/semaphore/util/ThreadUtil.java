package io.github.amitg87.semaphore.util;

import java.util.Date;
import java.util.Random;

/**
 * {@link java.lang.Thread} Utilities.
 */
public final class ThreadUtil{
    /**
     * Random number generator to used for util purposes.
     */
    private static Random random=new Random();

    /**
     * Put the calling thread to sleep.
     * Note, this thread does not throw interrupted exception,
     * but may return prematurely as a result of one.
     *
     * @param milliSecond time to sleep in millisecond
     */
    public static void sleep(int milliSecond){
        try{
            Thread.sleep(milliSecond);
        }catch(InterruptedException ignore){
        }
    }

    /**
     * Put the calling thread to sleep.
     * Note, this thread does not throw interrupted exception,
     * and will not return until the thread has slept for provided time.
     *
     * @param milliSecond milliSecond time to sleep in millisecond
     */
    public static void sleepFixed(int milliSecond){
        final long endingTime=System.currentTimeMillis()+milliSecond;
        long remainingTime=milliSecond;
        while(remainingTime>0){
            try{
                Thread.sleep(remainingTime);
            }catch(InterruptedException ignore){
            }
            remainingTime=endingTime-System.currentTimeMillis();
        }
    }

    /**
     * Generate an interruptible random delay.
     * @param limit upper limit on sleep
     */
    public static void randomDelay(int limit){
        sleep(random.nextInt(limit));
    }

    /**
     * Force an un-interruptible random delay
     * @param limit upper limit on delay
     */
    public static void randomDelayFixed(int limit){
        sleepFixed(random.nextInt(limit));
    }

    /**
     * Util method: getName of executing thread
     * @return invoking thread name
     */
    public static String getName(){
        return Thread.currentThread().getName();
    }

    /**
     * Print a string on console with date and invoking thread name.
     * This method is not buffered, and will be flushed on encountering new-line.
     * @param string string to be printed
     */
    public static void print(String string){
        System.out.print("[" + new Date(System.currentTimeMillis())+ "] " +Thread.currentThread().getName()+": "+string);
    }

    /**
     * Print a string on console with date and invoking thread name and a newline.
     * @param string string to be printed
     */
    public static void println(String string){
        System.out.println("[" + new Date(System.currentTimeMillis())+ "] " +Thread.currentThread().getName()+": "+string);
    }
}
