package io.github.amitg87.semaphore.lib;

/**
 * Represents a synchronization pattern of light-switch in a room. First thread to enter critical section locks
 * the semaphore and last thread leaving critical section unlocks it.
 */
public class LightSwitch{
    private Mutex mutex = new Mutex();
    private volatile int count;
    private Semaphore semaphore;

    /**
     * Create lightswitch with given semaphore.
     * First thread to enter lightswitch will acquire the semaphore, and last to leave will release the semaphore.
     * @param semaphore semaphore to used by lightswitch.
     */
    public LightSwitch(Semaphore semaphore){
        this.count=0;
        this.semaphore= semaphore;
    }

    /**
     * Enter a lightswitch.
     * First to enter a lightswitch will acquire the underlying semaphore.
     */
    public void enter(){
        mutex.enter();
        count++;
        if(count==1)
            semaphore.acquire();
        mutex.leave();
    }

    /**
     * Leave a lightswitch.
     * Last thread to leave a lightswitch will leave/release/unblock the underlying mutex/semaphore/turnstile.
     */
    public void leave(){
        mutex.enter();
        count--;
        if(count==0)
            semaphore.release();
        mutex.leave();
    }
}
