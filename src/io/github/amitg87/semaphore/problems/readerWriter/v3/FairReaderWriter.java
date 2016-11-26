package io.github.amitg87.semaphore.problems.readerWriter.v3;


import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.lib.Turnstile;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * solution to shortcoming of Basic version ReaderWriter:
 * Incoming writer blocks new reader threads, using a turnstile.
 */
public class FairReaderWriter{
    private final static int READER_COUNT=10;
    private final static int WRITER_COUNT=3;

    private static Mutex roomEmpty=new Mutex();
    private static Turnstile turnstile=new Turnstile();
    private static LightSwitch lightSwitch=new LightSwitch(roomEmpty);

    public static void main(String args[]){
        for(int i=0; i<READER_COUNT; i++){
            new Thread(new Reader(), "Reader-"+(i+1)).start();
        }
        for(int i=0; i<WRITER_COUNT; i++){
            new Thread(new Writer(), "Writer-"+(i+1)).start();
        }
    }

    static class Reader implements Runnable{
        @Override
        public void run(){
            while(true){
                ThreadUtil.println("trying to get in");
                turnstile.cross();
                lightSwitch.enter();
                ThreadUtil.println("reading");
                ThreadUtil.randomDelay(100);
                lightSwitch.leave();
                ThreadUtil.println("out of room");
                ThreadUtil.randomDelay(2000);
            }
        }
    }

    static class Writer implements Runnable{
        @Override
        public void run(){
            while(true){
                ThreadUtil.println("trying to get in");
                turnstile.block();
                roomEmpty.enter();
                ThreadUtil.println("writing");
                turnstile.unblock(); //next writer will compete with other readers for lock on this turnstile
                ThreadUtil.randomDelay(100);
                roomEmpty.leave();
                ThreadUtil.println("out of room");
                ThreadUtil.randomDelay(200);
            }
        }
    }
}


