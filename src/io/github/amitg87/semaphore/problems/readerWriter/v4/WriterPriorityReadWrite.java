package io.github.amitg87.semaphore.problems.readerWriter.v4;

import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Semaphore;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * In previous solution - once writer is done, next writer has to compete with readers.
 * Now all writers go through first while readers wait.
 */
public class WriterPriorityReadWrite{
    private final static int READER_COUNT=10;
    private final static int WRITER_COUNT=3;
    private static Semaphore noReaders = new Semaphore();
    private static Semaphore noWriters = new Semaphore();
    private static LightSwitch readerSwitch=new LightSwitch(noWriters);
    private static LightSwitch writerSwitch=new LightSwitch(noReaders);

    public static void main(String args[]){
        for(int i=0; i<READER_COUNT; i++){
            new Thread(new Reader(), "ReaderThread-"+(i+1)).start();
        }
        for(int i=0; i<WRITER_COUNT; i++){
            new Thread(new Writer(), "WriterThread-"+(i+1)).start();
        }
    }

    static class Reader implements Runnable{
        @Override
        public void run(){
            while(true){
                ThreadUtil.println("trying to get in");
                noReaders.acquire(); //compete with writers
                readerSwitch.enter(); //block writers while there are readers
                noReaders.release();
                System.out.println("reading");
                ThreadUtil.randomDelay(100);
                readerSwitch.leave();
                System.out.println("out of room");
                ThreadUtil.randomDelay(100);
            }
        }
    }

    static class Writer implements Runnable{
        @Override
        public void run(){
            while(true){
                ThreadUtil.println("trying to get in");
                writerSwitch.enter(); //lets many writers in - blocks readers with noReaders
                noWriters.acquire(); //only one writer can proceed writing at a time
                ThreadUtil.println("writing");
                ThreadUtil.randomDelay(100);
                noWriters.release();
                writerSwitch.leave();
                ThreadUtil.println("out of room");
                ThreadUtil.randomDelay(100);
            }
        }
    }

}

