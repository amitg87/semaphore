package io.github.amitg87.semaphore.problems.rendezvous;


import io.github.amitg87.semaphore.lib.Semaphore;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 *
 */
public class Rendezvous{
    private static Semaphore husbandReached =new Semaphore(0);
    private static Semaphore wifeReached =new Semaphore(0);

    public static void main(String args[]){
        new HusbandThread("Husband");
        new WifeThread("Wife");
    }

    static class WifeThread extends Runner {
        public WifeThread(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("on the way");
            ThreadUtil.randomDelay(1000);
            husbandReached.release();
            ThreadUtil.println("reached");
            wifeReached.acquire();
            ThreadUtil.println("Having dinner together");
            //have dinner
            ThreadUtil.randomDelay(10000);
        }
    }

    static class HusbandThread extends Runner {
        public HusbandThread(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("on the way");
            ThreadUtil.randomDelay(1000);
            wifeReached.release();
            ThreadUtil.println("reached");
            husbandReached.acquire();
            ThreadUtil.println("Having dinner together");
            //have dinner
            ThreadUtil.randomDelay(10000);
        }
    }
}

