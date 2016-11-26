package io.github.amitg87.semaphore.problems.readerWriter.v2;

import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Multiple readers are allowed together, only one writer at a time.
 * First reader to come in marks the room as occupied, last reader marks room as available.
 * Writer blocks until room is empty.
 *
 * Used pattern: Lightswitch
 *
 * Problem: writer starvation: writer waits for empty room, while reader come and go.
 */
public class ReaderWriterTurnstile {
    private final static int READER_COUNT=10;
    private final static int WRITER_COUNT=3;

    private static Mutex roomEmpty=new Mutex();
    private static LightSwitch lightSwitch=new LightSwitch(roomEmpty);

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
            lightSwitch.enter();
            ThreadUtil.println("reading");
            ThreadUtil.randomDelay(100);
            lightSwitch.leave();
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
            roomEmpty.enter();
            ThreadUtil.println("writing");
            ThreadUtil.randomDelay(100);
            roomEmpty.leave();
            ThreadUtil.println("out of room");
            ThreadUtil.randomDelay(100);
        }
    }

}

