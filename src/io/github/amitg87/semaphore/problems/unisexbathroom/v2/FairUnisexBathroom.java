package io.github.amitg87.semaphore.problems.unisexbathroom.v2;

import io.github.amitg87.semaphore.lib.LightSwitch;
import io.github.amitg87.semaphore.lib.Multiplex;
import io.github.amitg87.semaphore.lib.Turnstile;
import io.github.amitg87.semaphore.util.Runner;
import io.github.amitg87.semaphore.util.ThreadUtil;

public class FairUnisexBathroom{
    private static Multiplex bathroom = new Multiplex(2);
    //private static Mutex empty = new Mutex();
    private static Turnstile femaleTurnstile = new Turnstile();
    private static Turnstile maleTurnstile = new Turnstile();
    private static LightSwitch maleLightSwitch = new LightSwitch(femaleTurnstile);
    private static LightSwitch femaleLightSwitch = new LightSwitch(maleTurnstile);

    private static int femaleCount = 5;
    private static int maleCount = 5;

    public static void main(String args[]){
        for(int i=0; i<femaleCount; i++){
            new FemaleEmployee("Female-"+i);
        }
        for(int i=0; i<maleCount; i++){
            new MaleEmployee("Male-"+i);
        }
    }

    static class MaleEmployee extends Runner{

        public MaleEmployee(String name) {
            super(name);
        }

        @Override
        public void loop() {
            maleTurnstile.enter();
            ThreadUtil.println(": I'm here");
            maleLightSwitch.enter();
            maleTurnstile.leave();
            ThreadUtil.println(": trying to enter bathroom");
            bathroom.enter();

            //use the bathroom
            ThreadUtil.println(": using bathroom");
            ThreadUtil.randomDelay(500);

            bathroom.leave();
            ThreadUtil.println(": leaving bathroom");
            maleLightSwitch.leave();
            ThreadUtil.println(": I'm out of here");

            ThreadUtil.randomDelay(2000);
        }
    }

    static class FemaleEmployee extends Runner{

        public FemaleEmployee(String name) {
            super(name);
        }

        @Override
        public void loop() {
            femaleTurnstile.enter();

            ThreadUtil.println(": I'm here");
            femaleLightSwitch.enter();
            femaleTurnstile.leave();
            ThreadUtil.println(": trying to enter bathroom");
            bathroom.enter();

            //use the bathroom
            ThreadUtil.println(" :using bathroom");
            ThreadUtil.randomDelay(500);

            bathroom.leave();
            ThreadUtil.println(": leaving bathroom");
            femaleLightSwitch.leave();
            ThreadUtil.println(": I'm out of here");

            ThreadUtil.randomDelay(2000);
        }
    }
}
