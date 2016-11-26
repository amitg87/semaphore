package io.github.amitg87.semaphore.problems.unisexbathroom.v1;


import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Multiplex;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * a unisex bathroom - following synchronization constraints can be maintained:
 * <ul>
 *     <li>There cannot be men and women in the bathroom at the same time.</li>
 *     <li>There should never be more than three employees squandering company time in the bathroom.</li>
 * </ul>
 * Problem: starvation: one of gender male/female holds lightswitch and completely bars other
 */
public class UnisexBathroom{
    private static Multiplex bathroom = new Multiplex(2);
    private static Mutex empty = new Mutex();
    private static LightSwitch maleLightSwitch = new LightSwitch(empty);
    private static LightSwitch femaleLightSwitch = new LightSwitch(empty);

    private static int femaleCount = 5;
    private static int maleCount = 5;

    public static void main(String args[]){
        for(int i=1; i<=femaleCount; i++){
            new FemaleEmployee("Female-"+i);
        }
        for(int i=1; i<=maleCount; i++){
            new MaleEmployee("Male-"+i);
        }
    }

    static class MaleEmployee extends Runner{

        public MaleEmployee(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println(" I'm here");
            maleLightSwitch.enter();
            ThreadUtil.println(" trying to enter bathroom");
            bathroom.enter();

            //use the bathroom
            ThreadUtil.println(" using bathroom");
            ThreadUtil.randomDelay(500);

            bathroom.leave();
            ThreadUtil.println(" leaving bathroom");
            maleLightSwitch.leave();
            ThreadUtil.println(" I'm out of here");

            ThreadUtil.randomDelay(2000);
        }
    }

    static class FemaleEmployee extends Runner{

        public FemaleEmployee(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println(" I'm here");
            femaleLightSwitch.enter();
            ThreadUtil.println(" trying to enter bathroom");
            bathroom.enter();

            //use the bathroom
            ThreadUtil.println(" using bathroom");
            ThreadUtil.randomDelay(500);

            bathroom.leave();
            ThreadUtil.println(" leaving bathroom");
            femaleLightSwitch.leave();
            ThreadUtil.println(" I'm out of here");

            ThreadUtil.randomDelay(2000);
        }
    }
}
