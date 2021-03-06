package org.example.multithreading.greetingtask;

public class HelloSpammer {
    private int counter = 0;

    public void start() {
        Thread thread = new Thread(this::printGreeting);
        thread.start();
    }

    private void printGreeting() {
        if (counter < 50) {
            counter++;
            Thread thread = new Thread(this::printGreeting);
            thread.start();
            try {
                thread.join();
                System.out.println("Hello from thread" + " " + Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
