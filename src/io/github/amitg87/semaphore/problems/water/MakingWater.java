package io.github.amitg87.semaphore.problems.water;

import io.github.amitg87.semaphore.lib.Barrier;
import io.github.amitg87.semaphore.lib.Condition;
import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * two kinds of threads: oxygen and hydrogen.
 * create a barrier that makes each thread wait until a complete molecule is ready to proceed.
 * As each thread passes the barrier, it should invoke bond.
 * <ul>
 *     <li>If an oxygen thread arrives at the barrier when no hydrogen threads are
 present, it has to wait for two hydrogen threads.</li>
 *       <li>If a hydrogen thread arrives at the barrier when no other threads are
 present, it has to wait for an oxygen thread and another hydrogen thread.</li>
 * </ul>
 */
public class MakingWater{

    private static Mutex mutex = new Mutex();
    private static int hydrogenCount = 0;
    private static int oxygenCount = 0;
    private static Condition waitForHydrogen = new Condition(false);
    private static Condition waitForOxygen = new Condition(false);
    private static Barrier barrier = new Barrier(3);

    public static void main(String args[]){
        final int oxygenThreadCount = 2;
        final int hydrogenThreadCount = 6;
        for(int i=1; i<=oxygenThreadCount; i++){
            new OxygenThread("Oxygen-"+i);
        }
        for(int i=1; i<=hydrogenThreadCount; i++){
            new HydrogenThread("Hydrogen-"+i);
        }
    }

    static class OxygenThread extends Runner{

        public OxygenThread(String name) {
            super(name);
        }

        @Override
        public void loop() {
            mutex.enter();
            oxygenCount++;
            if(hydrogenCount>=2){
                //prepare to make water
                hydrogenCount = hydrogenCount - 2;
                oxygenCount = oxygenCount - 1;
                waitForHydrogen.release(2);
                waitForOxygen.signal(); //self signal
            } else {
                //give up mutex to avoid deadlock
                mutex.leave();
            }
            waitForOxygen.acquire();

            ThreadUtil.println("bond creation");

            barrier.await();
            mutex.leave();
        }
    }

    static class HydrogenThread extends Runner{

        public HydrogenThread(String name) {
            super(name);
        }

        @Override
        public void loop() {
            mutex.enter();
            hydrogenCount++;
            if(hydrogenCount >= 2 && oxygenCount>0){
                hydrogenCount -= 2;
                oxygenCount -= 1;
                waitForHydrogen.release(); //self signal
                //do not give up mutex - transferred to other thread
            } else if(oxygenCount == 0){
                mutex.leave();
            }
            waitForHydrogen.acquire();
            ThreadUtil.println("bond creation");
            barrier.await();
        }
    }
}
