package org.example.multithreading;

public class HelloSpammer {

    public void start(){
        for(int i = 0; i < 50; i++){
            Thread thread = new Thread(this::printGreeting);
            thread.start();
        }
    }

    private synchronized void printGreeting(){
        System.out.println("Hello from thread" + " " + Thread.currentThread());
    }
}
