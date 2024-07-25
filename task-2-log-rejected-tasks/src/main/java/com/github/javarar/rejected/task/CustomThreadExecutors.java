package com.github.javarar.rejected.task;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public final class CustomThreadExecutors {

    public static Executor logRejectedThreadPoolExecutor(int corePoolSize, int maxThreads, int queueSize) {
        if (corePoolSize > maxThreads || corePoolSize <= 0 || queueSize <=0) {
            throw new IllegalArgumentException();
        }
        return new ThreadPoolExecutor(corePoolSize, maxThreads, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        log.error("Задача отклонена {}", r);
                    }
                });
    }
}
