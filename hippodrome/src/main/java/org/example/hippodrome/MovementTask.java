package org.example.hippodrome;

import java.util.Random;
import java.util.concurrent.Callable;

public class MovementTask implements Callable<Integer> {
    private final Horse horse;

    public MovementTask(Horse horse) {
        this.horse = horse;
    }

    @Override
    public Integer call() {
        Random random = new Random();
        int delay;
        while (horse.getCurrentPos() < 1000) {
            delay = random.nextInt(500 - 400) + 400;
            horse.move();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread is interrupted: " + e);
            }
        }
        return horse.getId();
    }
}
