package io.github.amitg87.semaphore.problems.childCare;


import io.github.amitg87.semaphore.lib.Mutex;
import io.github.amitg87.semaphore.lib.Semaphore;
import io.github.amitg87.semaphore.util.Runner;

/**
 * At a child care center, state regulations require that there is always one adult present for every three children.
 *
 */
public class Demo {
    private static int children = 0;
    private static int adults = 0;
    private static int childrenWaitingToEnter = 0;
    private static int adultsWaitingToLeave = 0;
    private static Mutex mutex = new Mutex();
    private static Semaphore childQueueEnter = new Semaphore(0, true);
    private static Semaphore adultQueueLeave = new Semaphore(0, true);

    public static void main(String args[]){
        int caretakerCount = 3;
        int childrenCount = 9;
        for(int i=1; i<=caretakerCount; i++){
            new Children("Child-" + i);
        }
        for(int i=1; i<=childrenCount; i++){
            new CareTaker("Caretaker-" + i);
        }
    }

    /**
     * Caretaker:
     * Incoming caretaker will result in waiting children to enter care center.
     * Outgoing caretaker will have to wait for children to leave.
     */
    static class CareTaker extends Runner{

        public CareTaker(String name) {
            super(name);
        }

        @Override
        public void loop() {
            mutex.enter();
            adults++;
            if(childrenWaitingToEnter>0){
                int count = Math.min(childrenWaitingToEnter, 3);
                childQueueEnter.release(count);
                childrenWaitingToEnter = childrenWaitingToEnter - count;
                children = children + 3;
            }
            mutex.leave();

            //critical section

            mutex.enter();
            if(children < 3*(adults-1)){
                adults--;
                mutex.leave();
            } else {
                adultsWaitingToLeave++;
                mutex.leave();
                adultQueueLeave.acquire();
            }
        }
    }

    /**
     * Incoming children will wait if there are not enough caretakers.
     * Outgoing children will signal any waiting caretaker.
     */
    static class Children extends Runner{

        public Children(String name) {
            super(name);
        }

        @Override
        public void loop() {
            mutex.enter();
            if(children < 3*adults){
                children++;
                mutex.leave();
            } else {
                childrenWaitingToEnter++;
                mutex.leave();
                childQueueEnter.acquire();
            }

            //critical section

            mutex.enter();
            children--;
            if(adultsWaitingToLeave>0 && children <= 3*(adults-1)){
                adultsWaitingToLeave--;
                adults--;
                adultQueueLeave.release();
            }
            mutex.leave();
        }
    }
}
