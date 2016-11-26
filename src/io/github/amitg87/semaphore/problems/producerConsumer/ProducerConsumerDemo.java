package io.github.amitg87.semaphore.problems.producerConsumer;


import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.lib.Semaphore;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * Producers and Consumers share a bounded buffer - ArrayList numbers - size bound 5.
 * producers push a random number to buffer.
 * Consumers remove a number.
 */
public class ProducerConsumerDemo {

    private static int SIZE = 5;
    private static int COUNT = 10;
    private static Semaphore available = new Semaphore(0, true);
    private static Semaphore spaces = new Semaphore(SIZE, true);
    private static ArrayList<Integer> numbers = new ArrayList<>(SIZE);
    private static Mutex mutex = new Mutex();

    public static void main(String args[]){
        for(int i=1; i<=COUNT; i++){
            new Consumer("Consumer-" + i);
            new Producer("Producer-" + i);
        }
    }

    private static int getNumber(){
        return new Random(System.currentTimeMillis()).nextInt(100);
    }

    static class Producer extends Runner{

        public Producer(String name) {
            super(name);
        }

        @Override
        public void loop() {
            spaces.acquire();
            mutex.enter();
            int number = getNumber();
            numbers.add(number);
            ThreadUtil.println("produced " + number + " size: " + numbers.size());
            mutex.leave();
            available.release();
            ThreadUtil.sleep(100);
        }
    }

    static class Consumer extends Runner{

        public Consumer(String name) {
            super(name);
        }

        @Override
        public void loop() {
            available.acquire();
            mutex.enter();
            int number = numbers.remove(0);
            ThreadUtil.println("consuming " + number + " size: " + numbers.size());
            mutex.leave();
            spaces.release();
        }
    }
}
