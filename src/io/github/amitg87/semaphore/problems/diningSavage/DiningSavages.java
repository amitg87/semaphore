package io.github.amitg87.semaphore.problems.diningSavage;


import io.github.amitg87.semaphore.lib.Condition;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Savages eat from community pot. when pot is empty, cook wakes up to cook.
 * Cook fills up the pot again and goes to sleep.
 *
 * Note: transfer of mutex here: savage is holding mutex and waiting for food to be cooked.
 * We can safely update servingLeftInPot, since we know savage is not changing this value,
 * and waiting for pot to be filled.
 *
 * Note: problem starts with food in pot.
 */
public class DiningSavages {
    private static Mutex mutex = new Mutex();
    private static int servingsLeftInPot = 20;
    private static Condition waitForEmptyPot = new Condition(false);
    private static Condition waitForFullPot = new Condition(false);

    public static void main(String args[]){
        final int savageCount = 10;
        for(int i=1; i<=savageCount; i++){
            new DiningSavage("Savage-"+i);
        }

        new Cook("Cook");
    }

    static class DiningSavage extends Runner{

        public DiningSavage(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("I'm hungary");
            mutex.enter();
            if(servingsLeftInPot == 0){
                ThreadUtil.println("Pot Empty");
                waitForEmptyPot.signal();
                ThreadUtil.println("Waiting for cook to fill pot");
                waitForFullPot.await();
            }
            servingsLeftInPot--;
            ThreadUtil.println("Eating. Remaining: " + servingsLeftInPot);
            mutex.leave();
            ThreadUtil.println("Done eating");
            ThreadUtil.randomDelay(400);
        }
    }

    static class Cook extends Runner{
        public static final int COOKING_BATCH_SIZE = 20;

        public Cook(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("I'm off to sleep, wake me when no food");
            waitForEmptyPot.await();
            ThreadUtil.println("Cooking");
            ThreadUtil.sleep(1000);
            servingsLeftInPot += COOKING_BATCH_SIZE; //assumption: savage is holding lock
            ThreadUtil.println("Done Cooking");
            waitForFullPot.signal();
        }
    }
}