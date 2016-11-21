package io.github.amitg87.semaphore.lib;

/**
 * Represents a Condition on with a thread can wait.
 */
public class Condition extends Semaphore {

    /**
     * Creates a non-fair Condition, initially open.
     *
     * @param open true if condition is initially open
     */
    public Condition(boolean open) {
        this(open, false);
    }

    /**
     * Creates a Condition with
     *
     * @param open true if condition is initially open
     * @param fair true if condition is fair ie wakes oldest waiting thread
     */
    public Condition(boolean open, boolean fair) {
        super(open ? 1 : 0, fair);
    }

    /**
     * Await Condition to open.
     * This operation may block if condition is not open and will remain so until
     * signalled by another thread signalling this condition.
     */
    public void await() {
        super.acquire();
    }

    /**
     * Signal Condition to open.
     * If one or more threads are awaiting on this condition, one of them will resume execution.
     * fair decides if oldest thread is woken or any one.
     */
    public void signal() {
        super.release();
    }
}
