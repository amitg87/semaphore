package io.github.amitg87.semaphore.problems.multiplex;


import io.github.amitg87.semaphore.lib.Multiplex;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

/**
 * Discotheque allows 5 people at a time.
 */
public class Discotheque{
    //max 5 at a time
    private static Multiplex multiplex=new Multiplex(5);

    public static void main(String args[]){
        for(int i=0; i<20; i++){
            new Person("Person-"+(i+1));
        }
    }

    static class Person extends Runner {
        public Person(String name) {
            super(name);
        }

        @Override
        public void loop() {
            ThreadUtil.println("Waiting to get in");
            multiplex.enter();
            ThreadUtil.println("Got in");
            //dance
            ThreadUtil.randomDelay(5000);
            ThreadUtil.println("Leaving");
            multiplex.leave();
            ThreadUtil.randomDelay(1000);
        }
    }
}

