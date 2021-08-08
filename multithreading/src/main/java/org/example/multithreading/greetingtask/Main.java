package org.example.multithreading.greetingtask;

public class Main {
    public static void main(String[] args) {
        HelloSpammer spammer = new HelloSpammer();
        spammer.start();
    }
}
