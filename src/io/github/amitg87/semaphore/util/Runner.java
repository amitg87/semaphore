package io.github.amitg87.semaphore.util;

/**
 * Runner - whenever subclass of this class is instantiated - new thread is created and started.
 * This thread executes loop method provided by subclass in an infinite loop.
 */
public abstract class Runner implements Runnable {

    public Runner(String name) {
        new Thread(this, name).start();
    }

    @Override
    public void run() {
        while(true){
            loop();
        }
    }

    public abstract void loop();
}
