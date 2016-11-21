package io.github.amitg87.semaphore.lib;

/**
 * Represents a counting semaphore. A semaphore maintains a set of permits.
 * <ul>
 *     <li>Each acquire() blocks if necessary until a permit is available, and then takes it.</li>
 *     <li>Each release() adds a permit, potentially releasing a blocking acquirer.</li>
 * </ul>
 * A Semaphore created with 0 permits need to released first. However, no actual permit objects are used; the {@code Semaphore} just keeps a count of the number available and acts accordingly.
 * Semaphores don't have notion of ownership: the "semaphore" can be released by a thread other than the owner.
 * <p>
 * Fairness: A semaphore can be optionally fair: When set false, this class makes no guarantees about the order in which
 * threads acquire permits. In particular, barging is permitted, that is, a thread invoking acquire() can be allocated
 * a permit ahead of a thread that has been waiting - logically the new thread places itself at the head of the queue
 * of waiting threads. When fairness is set true, the semaphore guarantees that threads invoking any of the acquire methods
 * are selected to obtain permits in the order in which their invocation of those methods was processed (first-in-first-out; FIFO).
 * </p>
 * Generally, semaphores used to control resource access should be initialized as fair, to ensure that
 * no thread is starved out from accessing a resource. When using semaphores for other kinds of synchronization control,
 * the throughput advantages of non-fair ordering often outweigh fairness considerations.
 * <p/>
 * Memory consistency effects: Actions in a thread prior to calling a "release" method such as release()
 * happen-before actions following a successful "acquire" method such as acquire() in another thread.
 */
public class Semaphore {
    protected java.util.concurrent.Semaphore semaphore;

    /**
     * Create a non-fair semaphore with one permit.
     */
    public Semaphore(){
        this(1);
    }

    /**
     * Create a fair semaphore with given number of permits.
     * @param permits the initial number of permits available. This value may be negative,
     *                in which case releases must occur before any acquires will be granted.
     */
    public Semaphore(int permits) {
        this(permits, true);
    }

    /**
     * Create a semaphore with given number of permits and allowed fairness.
     * @param permits the initial number of permits available.
     *                This value may be negative, in which case releases must occur before any acquires will be granted.
     * @param fair true if this semaphore will guarantee first-in first-out granting of permits under contention, else false
     */
    public Semaphore(int permits, boolean fair) {
        semaphore = new java.util.concurrent.Semaphore(permits, fair);
    }

    /**
     * Acquire a permit on semaphore, reducing number of permits by one.
     * <p/>
     * If no permit is available, then this would block until one is available.
     */
    public void acquire(){
        semaphore.acquireUninterruptibly();
    }

    /**
     * Acquire the given number of permits on semaphore, reducing number of permits.
     * <p/>
     * If sufficient permits are not available, then this operation would block until required number of permits are available.
     * @param permits the number of permits to acquire.
     * @throws IllegalArgumentException if {@code permits} is negative
     */
    public void acquire(int permits){
        semaphore.acquireUninterruptibly(permits);
    }

    /**
     * Release a permit on semaphore.
     * If there is any waiting acquirer, it would be unblocked.
     * Fairness decides whether older waiting acquirer or any waiting acquirer will get permit.
     * There is no requirement that a thread that releases a permit must have acquired that permit by calling acquire().
     */
    public void release(){
        semaphore.release();
    }

    /**
     * Release provided number of permits on semaphore.
     * If there is/are any waiting acquirer, it/they would be unblocked.
     * fairness decides whether older waiting acquirer or any waiting acquirer will get permit.
     * There is no requirement that a thread that releases a permit must have acquired that permit by calling acquire().
     * @param permits number of permits to be released
     */
    public void release(int permits){
        semaphore.release(permits);
    }
}
