package io.github.amitg87.semaphore.lib;

/**
 * A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
 * CyclicBarriers are useful in programs involving a fixed sized party of threads that must occasionally wait for each other.
 * <p/>
 * The barrier is called cyclic because it can be re-used after the waiting threads are released.
 * {@link java.util.concurrent.CyclicBarrier} for reference
 */
public class Barrier{
    private int parties;
    private Semaphore barrier;
    private int count;
    private Mutex mutex;
    private Generation generation;

    /**
     * Creates a new <tt>Barrier</tt> that will trip when the
     * given number of parties (threads) are waiting upon it.
     * @param parties number of parties allowed in barrier
     */
    public Barrier(int parties){
        this.parties=parties;
        this.count = 0;
        mutex = new Mutex();
        barrier = new Semaphore(0);
        generation = new Generation();
    }

    /**
     * Wait for all parties to arrive.
     */
    public void await(){
        mutex.enter();
        Generation g = generation;
        count++;
        if(count==parties){
            count = 0;
            generation = new Generation();
            barrier.release(parties);
        }
        mutex.leave();

        while(true){
            barrier.acquire();
            if(generation != g)
                return;
        }
    }

    static class Generation{
        private static int number=0;

        Generation(){
            number++;
        }
    }
}
