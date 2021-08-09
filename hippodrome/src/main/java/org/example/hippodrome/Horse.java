package org.example.hippodrome;

import java.util.Random;

public class Horse {
    private final int id;
    private int currentPos;
    private final Random random;

    public Horse(int id) {
        this.id = id;
        this.currentPos = 0;
        this.random = new Random();
    }

    public void move() {
        currentPos += random.nextInt(200 - 100) + 100;
    }

    public int getId() {
        return id;
    }

    public int getCurrentPos() {
        return currentPos;
    }
}
