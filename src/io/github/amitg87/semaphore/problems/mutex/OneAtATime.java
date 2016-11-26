package io.github.amitg87.semaphore.problems.mutex;


import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Tiger and Lion share a yard.
 * Only one should get out into the yard at a time - otherwise fatal fight.
 */
public class OneAtATime{
    private static Mutex mutex=new Mutex();
    public static void main(String args[]){
       new Animal("Tiger");
       new Animal("Lion");
    }

    static class Animal extends Runner {

        public Animal(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("trying to enter yard");
            mutex.enter();
            ThreadUtil.println("in yard");
            ThreadUtil.randomDelay(5000);
            ThreadUtil.println("leaving yard");
            mutex.leave();
            ThreadUtil.randomDelay(1000);
        }
    }
}
