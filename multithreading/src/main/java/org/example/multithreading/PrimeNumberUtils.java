package org.example.multithreading;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.*;

public class PrimeNumberUtils {

    public static int count(List<Integer> numbers) throws ExecutionException, InterruptedException {
        FutureTask<Integer> leftTask = new FutureTask<>(() -> countSync(numbers.subList(0, numbers.size() / 2)));
        FutureTask<Integer> rightTask = new FutureTask<>(() -> countSync(numbers.subList(numbers.size() / 2, numbers.size())));
        Executor executor = r -> new Thread(r).start();
        executor.execute(leftTask);
        executor.execute(rightTask);
        return leftTask.get() + rightTask.get();
    }

    private static int countSync(List<Integer> numbers) {
        int countPrimeNumber = 0;
        for (Integer num : numbers) {
            BigInteger bigInteger = BigInteger.valueOf(num);
            if (bigInteger.isProbablePrime((int) Math.log(num))) {
                countPrimeNumber++;
            }
        }
        return countPrimeNumber;
    }
}
