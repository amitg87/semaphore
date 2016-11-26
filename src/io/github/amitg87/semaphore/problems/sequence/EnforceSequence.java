package io.github.amitg87.semaphore.problems.sequence;


import io.github.amitg87.semaphore.lib.Condition;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Enforce sequence: wife cooks before husband can eat. wife is entitled to some free time.
 */
public class EnforceSequence{
    private static Condition hungry=new Condition(false);
    private static Condition foodReady =new Condition(false);

    public static void main(String args[]){
        new Husband("Husband");
        new Wife("Wife");
    }

    static class Wife extends Runner {

        public Wife(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("Waiting for hungry husband");
            hungry.await();
            ThreadUtil.println("Cooking");
            ThreadUtil.randomDelay(100);
            ThreadUtil.println("Done cooking - ready to eat");
            foodReady.signal();
            ThreadUtil.println("Enjoying my time");
            ThreadUtil.randomDelay(10000);
        }
    }

    static class Husband extends Runner {
        public Husband(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("Hungry");
            hungry.signal();
            ThreadUtil.println("Waiting for food");
            foodReady.await();
            ThreadUtil.println("Eating - done eating");
            ThreadUtil.randomDelay(10000);
        }
    }
}

