package io.github.amitg87.semaphore.lib;

/**
 * Represents a multiplex to allow fixed number of threads in critical section.
 */
public class Multiplex extends Semaphore {

    /**
     * Create a non-fair Multiplex with given number of permits.
     *
     * @param permits represent number of threads that will allowed in critical section.
     */
    public Multiplex(int permits) {
        this(permits, false);
    }

    /**
     * Create a Multiplex with given number of permits and fairness
     *
     * @param permits number of threads that will allowed in critical section.
     * @param fair    true if multiplex is fair
     */
    public Multiplex(int permits, boolean fair) {
        super(permits, fair);
    }

    /**
     * Enter critical section.
     * Uses one permit - if no permits are available, current thread will block.
     * Will be unblocked by any outgoing thread
     */
    public void enter() {
        super.acquire();
    }

    /**
     * Multiple threads enter a multiplex.
     *
     * @param count number of used permits
     */
    public void enter(int count) {
        super.acquire(count);
    }

    /**
     * Leave critical section.
     * Will leave a permit - will unblock a waiting thread. Fairness decides which thread will be unblocked.
     */
    public void leave() {
        super.release();
    }

    /**
     * Multiple threads leave a multiplex.
     *
     * @param count number of permits to release.
     */
    public void leave(int count) {
        super.release(count);
    }
}
