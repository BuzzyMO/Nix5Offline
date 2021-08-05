package org.example.multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        HelloSpammer spammer = new HelloSpammer();
        spammer.start();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            numbers.add(i);
        }

        try {
            int countsPrimeNumber = PrimeNumberUtils.count(numbers);
            System.out.println("countsPrimeNumber = " + countsPrimeNumber);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
