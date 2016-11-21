package io.github.amitg87.semaphore.lib;

/**
 * Represents a turnstile like on a metro station.
 * Can be used to make things happen serially with options to block/unblock.
 */
public class Turnstile extends Mutex {

    /**
     * Creates an open, non-fair Turnstile.
     */
    public Turnstile() {
        this(true);
    }

    /**
     * Creates a non-fair Turnstile.
     *
     * @param open true if turnstile is initially open.
     */
    public Turnstile(boolean open) {
        this(open, false);
    }

    /**
     * Creates a Turnstile.
     *
     * @param open true if turnstile is initially open
     * @param fair true if turnstile is fair
     */
    public Turnstile(boolean open, boolean fair) {
        super(open ? 1 : 0, fair);
    }

    /**
     * Block turnstile so that no thread can pass through.
     */
    public void block() {
        super.acquire();
    }

    /**
     * Unblock turnstile so that threads can pass through.
     */
    public void unblock() {
        super.release();
    }

    /**
     * Cross a turnstile.
     * if turnstile is blocked, current thread will get blocked.
     * Note: this method is <b>synchronized</b>.
     */
    public synchronized void cross() {
        super.acquire();
        super.release();
    }
}
