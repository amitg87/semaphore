package io.github.amitg87.semaphore.problems.readerWriter.v1;

import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.lib.Semaphore;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Multiple readers are allowed together, only one writer at a time.
 * First reader to come in marks the room as occupied, last reader marks room as available.
 * Writer blocks until room is empty.
 *
 * Problem: writer starvation: writer waits for empty room, while reader come and go.
 */
public class ReaderWriter {
    private final static int READER_COUNT=10;
    private final static int WRITER_COUNT=3;

    private static Semaphore roomEmpty=new Semaphore();
    private static int readerCount = 0;
    private static Mutex mutex = new Mutex();

    public static void main(String args[]){
        for(int i=1; i<=READER_COUNT; i++){
            new Reader("Reader-"+i);
        }
        for(int i=1; i<=WRITER_COUNT; i++){
            new Writer("Writer-"+i);
        }
    }

    static class Reader extends Runner{
        public Reader(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("trying to get in");
            mutex.enter();
                readerCount++;
                if(readerCount == 1){
                    //first reader marks room occupied
                    roomEmpty.acquire();
                }
            mutex.leave();
            ThreadUtil.println("reading");
            ThreadUtil.randomDelay(100);
            mutex.enter();
                readerCount--;
                if(readerCount == 0){
                    roomEmpty.release();
                }
            mutex.leave();
            ThreadUtil.println("out of room");
            ThreadUtil.randomDelay(2000);
        }
    }

    static class Writer extends Runner{
        public Writer(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("trying to get in");
            roomEmpty.acquire();
            ThreadUtil.println("writing");
            ThreadUtil.randomDelay(100);
            ThreadUtil.println("out of room");
            roomEmpty.release();
            ThreadUtil.randomDelay(200);
        }
    }

}

