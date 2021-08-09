package org.example.hippodrome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Hippodrome {
    private final List<Integer> posFinishedHorse = new ArrayList<>();

    public void exec() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter number of horses: ");
            int numHorses = Integer.parseInt(reader.readLine());
            System.out.println("Enter horse number which you betting on (Range from 0 to " + (numHorses - 1) + ")");
            int horseBetNum = Integer.parseInt(reader.readLine());
            startRace(numHorses);
            int posOfHorseBet = getPosOfHorse(horseBetNum);
            System.out.println("Position of your horse = " + posOfHorseBet + " [from 1 to " + numHorses + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startRace(int numHorses) {
        ExecutorService executor = Executors.newFixedThreadPool(numHorses);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for (int i = 0; i < numHorses; i++) {
            Horse horse = new Horse(i);
            MovementTask movementTask = new MovementTask(horse);
            completionService.submit(movementTask);
        }
        for (int i = 0; i < numHorses; i++) {
            try {
                Future<Integer> res = completionService.take();
                posFinishedHorse.add(res.get());
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
        System.out.println("posFinishedHorse = " + posFinishedHorse);
    }

    private int getPosOfHorse(int horseNum) {
        return posFinishedHorse.indexOf(horseNum) + 1;
    }
}
