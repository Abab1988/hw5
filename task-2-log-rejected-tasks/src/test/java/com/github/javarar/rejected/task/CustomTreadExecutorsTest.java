package com.github.javarar.rejected.task;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Log4j2
public class CustomTreadExecutorsTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 10, 30})
    public void threadPoolDoesNotThrowExceptionOnQueueOverflow(int threads) {
        AtomicInteger sum = new AtomicInteger();
        Executor executor = CustomThreadExecutors.logRejectedThreadPoolExecutor(threads, threads, 10);
        // пытаемся последовательно сложить числа от 1 до 100, отправляя значения в пулл потоков,
        // и аккумулируя значение в переменной sum
        IntStream.range(1, 101).forEach(value -> {
            executor.execute(() -> {
                try {
                    // Увеличиваем сумму
                    sum.addAndGet(value);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        try {
            //Ждем, когда все потоки отработают
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Очередь на 10 задач + 10 активных потоков итого 20 задач должно обработаться, остальные не должны добавиться в очередь задач
        // Сумма членов арифметической прогрессии (1 + 20) / 2 * 20 = 210
        double expected = (double)(1 + threads + 10) / 2 * (threads + 10);
        Assertions.assertEquals(expected, sum.get());

    }
}
