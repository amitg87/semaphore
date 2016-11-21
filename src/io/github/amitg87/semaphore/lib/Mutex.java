package io.github.amitg87.semaphore.lib;

/**
 * Represents a mutex to allow only one thread in critical section.
 * It has two states: mutex entry available, or not available.
 */
public class Mutex extends Semaphore {
    /**
     * Creates a non-fair Mutex.
     */
    public Mutex() {
        super(1, false);
    }

    /**
     * Creates a Mutex with provided fairness.
     *
     * @param fair true if mutex is fair
     */
    public Mutex(boolean fair) {
        super(1, fair);
    }

    /**
     * Creates a Mutex with provided number of permits and fairness.
     *
     * @param permits the initial number of permits available. This value may be negative,
     *                in which case enter must occur before any leaves will be granted.
     * @param fair true if mutex is fair
     */
    public Mutex(int permits, boolean fair) {
        super(permits, fair);
    }

    /**
     * Acquire the mutex. If another thread already holds the mutex, this thread will block until mutex
     * released by thread which acquired mutex.
     */
    public void enter() {
        super.acquire();
    }

    /**
     * Release the mutex. If there are threads waiting to enter mutex, one of them will be resumed,
     * which thread will be resumed depends on fairness.
     */
    public void leave() {
        super.release();
    }
}
