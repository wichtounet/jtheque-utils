package org.jtheque.utils;

import org.jtheque.utils.collections.CollectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Loggers {
    private static final Map<Class<?>, AsyncLogger> CACHE = CollectionUtils.newConcurrentMap(100);

    private static final BlockingQueue<Message> MESSAGES = new LinkedBlockingQueue<Message>();

    private static final Message DUMMY = new SimpleMessage(null, null, null);
    private static final int threads = 16;
    private static CountDownLatch latch;

    private Loggers() {
        throw new AssertionError();
    }

    public static void main(String[] args) {
        //warmup(getLogger(Loggers.class));
        warmup(LoggerFactory.getLogger(Loggers.class));

        //testLoggers();
        testSLF4J();
    }

    private static void testLoggers() {
        long startTime = System.nanoTime();

        latch = new CountDownLatch(threads + 1);

        CyclicBarrier barrier = new CyclicBarrier(threads + 1);

        launchThreads(getLogger(Loggers.class), latch, threads, barrier);

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BrokenBarrierException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        MESSAGES.add(DUMMY);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        System.out.println("Results : " + (System.nanoTime() - startTime) / 1000 / 1000 + " ms");
    }

    private static void testSLF4J() {
        long startTime = System.nanoTime();

        latch = new CountDownLatch(threads);

        CyclicBarrier barrier = new CyclicBarrier(threads + 1);

        launchThreads(LoggerFactory.getLogger(Loggers.class), latch, threads, barrier);

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (BrokenBarrierException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        System.out.println("Results : " + (System.nanoTime() - startTime) / 1000 / 1000 + " ms");
    }

    private static void launchThreads(final Logger logger, final CountDownLatch latch, int threads, final CyclicBarrier barrier) {
        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int a = 0; a < 5000; a++) {
                        logger.trace("Test 1");
                        logger.trace("Test 1 {}", 1);
                        logger.trace("Test 1 {}{}", 1, 2);
                        logger.trace("Test 1 {}{}{}", new Object[]{1, 2, 3});

                        logger.info("Test 2");
                        logger.info("Test 2 {}", 1);
                        logger.info("Test 2 {}{}", 1, 2);
                        logger.info("Test 2 {}{}{}", new Object[]{1, 2, 3});

                        logger.debug("Test 3");
                        logger.debug("Test {}", 1);
                        logger.debug("Test {}{}", 1, 2);
                        logger.debug("Test {}{}{}", new Object[]{1, 2, 3});

                        logger.warn("Test 4");
                        logger.warn("Test 4 {}", 1);
                        logger.warn("Test 4 {}{}", 1, 2);
                        logger.warn("Test 4 {}{}{}", new Object[]{1, 2, 3});

                        logger.error("Test 5");
                        logger.error("Test 5 {}", 1);
                        logger.error("Test 5 {}{}", 1, 2);
                        logger.error("Test 5 {}{}{}", new Object[]{1, 2, 3});
                    }

                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    latch.countDown();
                }
            }).start();
        }
    }

    private static void warmup(Logger logger) {
        logger.debug("Warmup start");

        long startTime = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            logger.trace("Warmup 1");
            logger.trace("Warmup 1 {}", 1);
            logger.trace("Warmup 1 {}{}", 1, 2);
            logger.trace("Warmup 1 {}{}{}", new Object[]{1, 2, 3});

            logger.info("Warmup 2");
            logger.info("Warmup 2 {}", 1);
            logger.info("Warmup 2 {}{}", 1, 2);
            logger.info("Warmup 2 {}{}{}", new Object[]{1, 2, 3});

            logger.debug("Warmup 3");
            logger.debug("Warmup {}", 1);
            logger.debug("Warmup {}{}", 1, 2);
            logger.debug("Warmup {}{}{}", new Object[]{1, 2, 3});

            logger.warn("Warmup 4");
            logger.warn("Warmup 4 {}", 1);
            logger.warn("Warmup 4 {}{}", 1, 2);
            logger.warn("Warmup 4 {}{}{}", new Object[]{1, 2, 3});

            logger.error("Warmup 5");
            logger.error("Warmup 5 {}", 1);
            logger.error("Warmup 5 {}{}", 1, 2);
            logger.error("Warmup 5 {}{}{}", new Object[]{1, 2, 3});
        }

        logger.debug("Warmup took {} ms", (System.nanoTime() - startTime) / 1000 / 1000);
    }

    public static Logger getLogger(Class<?> type) {
        if (!CACHE.containsKey(type)) {
            CACHE.put(type, new AsyncLogger(LoggerFactory.getLogger(type)));
        }

        return CACHE.get(type);
    }

    private enum Level {
        TRACE,
        WARN,
        DEBUG,
        INFO,
        ERROR
    }

    private interface Message {
        void log();
    }

    private static class SimpleMessage implements Message {
        private final Logger logger;
        private final Level level;
        private final String message;
        private final Object[] args;

        private SimpleMessage(Logger logger, Level level, String message, Object... args) {
            super();

            this.logger = logger;
            this.level = level;
            this.message = message;
            this.args = args;
        }

        @Override
        public void log() {
            switch (level) {
                case TRACE:
                    if (args.length == 0) {
                        logger.trace(message);
                    } else if (args.length == 1) {
                        logger.trace(message, args[0]);
                    } else if (args.length == 2) {
                        logger.trace(message, args[0], args[1]);
                    } else {
                        logger.trace(message, args);
                    }
                    break;
                case WARN:
                    if (args.length == 0) {
                        logger.warn(message);
                    } else if (args.length == 1) {
                        logger.warn(message, args[0]);
                    } else if (args.length == 2) {
                        logger.warn(message, args[0], args[1]);
                    } else {
                        logger.warn(message, args);
                    }
                    break;
                case DEBUG:
                    if (args.length == 0) {
                        logger.debug(message);
                    } else if (args.length == 1) {
                        logger.debug(message, args[0]);
                    } else if (args.length == 2) {
                        logger.debug(message, args[0], args[1]);
                    } else {
                        logger.debug(message, args);
                    }
                    break;
                case INFO:
                    if (args.length == 0) {
                        logger.info(message);
                    } else if (args.length == 1) {
                        logger.info(message, args[0]);
                    } else if (args.length == 2) {
                        logger.info(message, args[0], args[1]);
                    } else {
                        logger.info(message, args);
                    }
                    break;
                case ERROR:
                    if (args.length == 0) {
                        logger.error(message);
                    } else if (args.length == 1) {
                        logger.error(message, args[0]);
                    } else if (args.length == 2) {
                        logger.error(message, args[0], args[1]);
                    } else {
                        logger.error(message, args);
                    }
                    break;
            }
        }
    }

    private static class ThrowableMessage implements Message {
        private final Logger logger;
        private final Level level;
        private final String message;
        private final Throwable throwable;

        private ThrowableMessage(Logger logger, Level level, String message, Throwable throwable) {
            super();

            this.logger = logger;
            this.level = level;
            this.message = message;
            this.throwable = throwable;
        }

        @Override
        public void log() {
            switch (level) {
                case TRACE:
                    logger.trace(message, throwable);
                    break;
                case WARN:
                    logger.warn(message, throwable);
                    break;
                case DEBUG:
                    logger.debug(message, throwable);
                    break;
                case INFO:
                    logger.info(message, throwable);
                    break;
                case ERROR:
                    logger.error(message, throwable);
                    break;
            }
        }
    }

    private static class MarkerThrowableMessage implements Message {
        private final Logger logger;
        private final Level level;
        private final Marker marker;
        private final String message;
        private final Throwable throwable;

        private MarkerThrowableMessage(Logger logger, Level level, Marker marker, String message, Throwable throwable) {
            super();

            this.logger = logger;
            this.level = level;
            this.marker = marker;
            this.message = message;
            this.throwable = throwable;
        }

        @Override
        public void log() {
            switch (level) {
                case TRACE:
                    logger.trace(marker, message, throwable);
                    break;
                case WARN:
                    logger.warn(marker, message, throwable);
                    break;
                case DEBUG:
                    logger.debug(marker, message, throwable);
                    break;
                case INFO:
                    logger.info(marker, message, throwable);
                    break;
                case ERROR:
                    logger.error(marker, message, throwable);
                    break;
            }
        }
    }

    private static class MarkerMessage implements Message {
        private final Logger logger;
        private final Level level;
        private final Marker marker;
        private final String message;
        private final Object[] args;

        private MarkerMessage(Logger logger, Level level, Marker marker, String message, Object... args) {
            super();

            this.logger = logger;
            this.level = level;
            this.marker = marker;
            this.message = message;
            this.args = args;
        }

        @Override
        public void log() {
            switch (level) {
                case TRACE:
                    if (args.length == 0) {
                        logger.trace(marker, message);
                    } else if (args.length == 1) {
                        logger.trace(marker, message, args[0]);
                    } else if (args.length == 2) {
                        logger.trace(marker, message, args[0], args[1]);
                    } else {
                        logger.trace(marker, message, args);
                    }
                    break;
                case WARN:
                    if (args.length == 0) {
                        logger.warn(marker, message);
                    } else if (args.length == 1) {
                        logger.warn(marker, message, args[0]);
                    } else if (args.length == 2) {
                        logger.warn(marker, message, args[0], args[1]);
                    } else {
                        logger.warn(marker, message, args);
                    }
                    break;
                case DEBUG:
                    if (args.length == 0) {
                        logger.debug(marker, message);
                    } else if (args.length == 1) {
                        logger.debug(marker, message, args[0]);
                    } else if (args.length == 2) {
                        logger.debug(marker, message, args[0], args[1]);
                    } else {
                        logger.debug(marker, message, args);
                    }
                    break;
                case INFO:
                    if (args.length == 0) {
                        logger.info(marker, message);
                    } else if (args.length == 1) {
                        logger.info(marker, message, args[0]);
                    } else if (args.length == 2) {
                        logger.info(marker, message, args[0], args[1]);
                    } else {
                        logger.info(marker, message, args);
                    }
                    break;
                case ERROR:
                    if (args.length == 0) {
                        logger.error(marker, message);
                    } else if (args.length == 1) {
                        logger.error(marker, message, args[0]);
                    } else if (args.length == 2) {
                        logger.error(marker, message, args[0], args[1]);
                    } else {
                        logger.error(marker, message, args);
                    }
                    break;
            }
        }
    }

    private static class AsyncLogger implements Logger {
        private final Logger logger;

        static {
            Thread loggingThread = new Thread(new LoggingThread());
            //loggingThread.setDaemon(true);
            loggingThread.setName("Logging-Thread");
            loggingThread.start();

            Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownThread()));
        }

        private AsyncLogger(Logger logger) {
            super();

            this.logger = logger;
        }

        private static void addMessage(Logger logger, Level level, String message, Throwable throwable) {
            try {
                MESSAGES.put(new ThrowableMessage(logger, level, message, throwable));
            } catch (InterruptedException e) {
                //Will not occurs because queue is unbounded
                Thread.currentThread().interrupt();
            }
        }

        private static void addMessage(Logger logger, Level level, Marker marker, String message, Throwable throwable) {
            try {
                MESSAGES.put(new MarkerThrowableMessage(logger, level, marker, message, throwable));
            } catch (InterruptedException e) {
                //Will not occurs because queue is unbounded
                Thread.currentThread().interrupt();
            }
        }

        private static void addMessage(Logger logger, Level level, String message, Object... args) {
            try {
                MESSAGES.put(new SimpleMessage(logger, level, message, args));
            } catch (InterruptedException e) {
                //Will not occurs because queue is unbounded
                Thread.currentThread().interrupt();
            }
        }

        private static void addMessage(Logger logger, Level level, Marker marker, String message, Object... args) {
            try {
                MESSAGES.put(new MarkerMessage(logger, level, marker, message, args));
            } catch (InterruptedException e) {
                //Will not occurs because queue is unbounded
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public String getName() {
            return logger.getName();
        }

        @Override
        public boolean isTraceEnabled() {
            return logger.isTraceEnabled();
        }

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return logger.isTraceEnabled(marker);
        }

        @Override
        public boolean isDebugEnabled() {
            return logger.isDebugEnabled();
        }

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return logger.isDebugEnabled(marker);
        }

        @Override
        public boolean isInfoEnabled() {
            return logger.isInfoEnabled();
        }

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return logger.isInfoEnabled(marker);
        }

        @Override
        public boolean isWarnEnabled() {
            return logger.isWarnEnabled();
        }

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return logger.isWarnEnabled(marker);
        }

        @Override
        public boolean isErrorEnabled() {
            return logger.isErrorEnabled();
        }

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return logger.isErrorEnabled(marker);
        }

        @Override
        public void trace(String msg) {
            if (isTraceEnabled()) {
                addMessage(logger, Level.TRACE, msg);
            }
        }

        @Override
        public void trace(String format, Object arg) {
            if (isTraceEnabled()) {
                addMessage(logger, Level.TRACE, format, arg);
            }
        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {
            if (isTraceEnabled()) {
                addMessage(logger, Level.TRACE, format, arg1, arg2);
            }
        }

        @Override
        public void trace(String format, Object[] argArray) {
            if (isTraceEnabled()) {
                addMessage(logger, Level.TRACE, format, argArray);
            }
        }

        @Override
        public void trace(String msg, Throwable t) {
            if (isTraceEnabled()) {
                addMessage(logger, Level.TRACE, msg, t);
            }
        }

        @Override
        public void trace(Marker marker, String msg) {
            if (isTraceEnabled(marker)) {
                addMessage(logger, Level.TRACE, marker, msg);
            }
        }

        @Override
        public void trace(Marker marker, String format, Object arg) {
            if (isTraceEnabled(marker)) {
                addMessage(logger, Level.TRACE, marker, format, arg);
            }
        }

        @Override
        public void trace(Marker marker, String format, Object arg1, Object arg2) {
            if (isTraceEnabled(marker)) {
                addMessage(logger, Level.TRACE, marker, format, arg1, arg2);
            }
        }

        @Override
        public void trace(Marker marker, String format, Object[] argArray) {
            if (isTraceEnabled(marker)) {
                addMessage(logger, Level.TRACE, marker, format, argArray);
            }
        }

        @Override
        public void trace(Marker marker, String msg, Throwable t) {
            if (isTraceEnabled(marker)) {
                addMessage(logger, Level.TRACE, marker, msg, t);
            }
        }

        @Override
        public void debug(String msg) {
            if (isDebugEnabled()) {
                addMessage(logger, Level.DEBUG, msg);
            }
        }

        @Override
        public void debug(String format, Object arg) {
            if (isDebugEnabled()) {
                addMessage(logger, Level.DEBUG, format, arg);
            }
        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {
            if (isDebugEnabled()) {
                addMessage(logger, Level.DEBUG, format, arg1, arg2);
            }
        }

        @Override
        public void debug(String format, Object[] argArray) {
            if (isDebugEnabled()) {
                addMessage(logger, Level.DEBUG, format, argArray);
            }
        }

        @Override
        public void debug(String msg, Throwable t) {
            if (isDebugEnabled()) {
                addMessage(logger, Level.DEBUG, msg, t);
            }
        }

        @Override
        public void debug(Marker marker, String msg) {
            if (isDebugEnabled(marker)) {
                addMessage(logger, Level.DEBUG, marker, msg);
            }
        }

        @Override
        public void debug(Marker marker, String format, Object arg) {
            if (isDebugEnabled(marker)) {
                addMessage(logger, Level.DEBUG, marker, format, arg);
            }
        }

        @Override
        public void debug(Marker marker, String format, Object arg1, Object arg2) {
            if (isDebugEnabled(marker)) {
                addMessage(logger, Level.DEBUG, marker, format, arg1, arg2);
            }
        }

        @Override
        public void debug(Marker marker, String format, Object[] argArray) {
            if (isDebugEnabled(marker)) {
                addMessage(logger, Level.DEBUG, marker, format, argArray);
            }
        }

        @Override
        public void debug(Marker marker, String msg, Throwable t) {
            if (isDebugEnabled(marker)) {
                addMessage(logger, Level.DEBUG, marker, msg, t);
            }
        }

        @Override
        public void info(String msg) {
            if (isInfoEnabled()) {
                addMessage(logger, Level.INFO, msg);
            }
        }

        @Override
        public void info(String format, Object arg) {
            if (isInfoEnabled()) {
                addMessage(logger, Level.INFO, format, arg);
            }
        }

        @Override
        public void info(String format, Object arg1, Object arg2) {
            if (isInfoEnabled()) {
                addMessage(logger, Level.INFO, format, arg1, arg2);
            }
        }

        @Override
        public void info(String format, Object[] argArray) {
            if (isInfoEnabled()) {
                addMessage(logger, Level.INFO, format, argArray);
            }
        }

        @Override
        public void info(String msg, Throwable t) {
            if (isInfoEnabled()) {
                addMessage(logger, Level.INFO, msg, t);
            }
        }

        @Override
        public void info(Marker marker, String msg) {
            if (isInfoEnabled(marker)) {
                addMessage(logger, Level.INFO, marker, msg);
            }
        }

        @Override
        public void info(Marker marker, String format, Object arg) {
            if (isInfoEnabled(marker)) {
                addMessage(logger, Level.INFO, marker, format, arg);
            }
        }

        @Override
        public void info(Marker marker, String format, Object arg1, Object arg2) {
            if (isInfoEnabled(marker)) {
                addMessage(logger, Level.INFO, marker, format, arg1, arg2);
            }
        }

        @Override
        public void info(Marker marker, String format, Object[] argArray) {
            if (isInfoEnabled(marker)) {
                addMessage(logger, Level.INFO, marker, format, argArray);
            }
        }

        @Override
        public void info(Marker marker, String msg, Throwable t) {
            if (isInfoEnabled(marker)) {
                addMessage(logger, Level.INFO, marker, msg, t);
            }
        }

        @Override
        public void warn(String msg) {
            if (isWarnEnabled()) {
                addMessage(logger, Level.WARN, msg);
            }
        }

        @Override
        public void warn(String format, Object arg) {
            if (isWarnEnabled()) {
                addMessage(logger, Level.WARN, format, arg);
            }
        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {
            if (isWarnEnabled()) {
                addMessage(logger, Level.WARN, format, arg1, arg2);
            }
        }

        @Override
        public void warn(String format, Object[] argArray) {
            if (isWarnEnabled()) {
                addMessage(logger, Level.WARN, format, argArray);
            }
        }

        @Override
        public void warn(String msg, Throwable t) {
            if (isWarnEnabled()) {
                addMessage(logger, Level.WARN, msg);
            }
        }

        @Override
        public void warn(Marker marker, String msg) {
            if (isWarnEnabled(marker)) {
                addMessage(logger, Level.WARN, marker, msg);
            }
        }

        @Override
        public void warn(Marker marker, String format, Object arg) {
            if (isWarnEnabled(marker)) {
                addMessage(logger, Level.WARN, marker, format, arg);
            }
        }

        @Override
        public void warn(Marker marker, String format, Object arg1, Object arg2) {
            if (isWarnEnabled(marker)) {
                addMessage(logger, Level.WARN, marker, format, arg1, arg2);
            }
        }

        @Override
        public void warn(Marker marker, String format, Object[] argArray) {
            if (isWarnEnabled(marker)) {
                addMessage(logger, Level.WARN, marker, format, argArray);
            }
        }

        @Override
        public void warn(Marker marker, String msg, Throwable t) {
            if (isWarnEnabled(marker)) {
                addMessage(logger, Level.WARN, marker, msg);
            }
        }

        @Override
        public void error(String msg) {
            if (isErrorEnabled()) {
                addMessage(logger, Level.ERROR, msg);
            }
        }

        @Override
        public void error(String format, Object arg) {
            if (isErrorEnabled()) {
                addMessage(logger, Level.ERROR, format, arg);
            }
        }

        @Override
        public void error(String format, Object arg1, Object arg2) {
            if (isErrorEnabled()) {
                addMessage(logger, Level.ERROR, format, arg1, arg2);
            }
        }

        @Override
        public void error(String format, Object[] argArray) {
            if (isErrorEnabled()) {
                addMessage(logger, Level.ERROR, format, argArray);
            }
        }

        @Override
        public void error(String msg, Throwable t) {
            if (isErrorEnabled()) {
                addMessage(logger, Level.ERROR, msg, t);
            }
        }

        @Override
        public void error(Marker marker, String msg) {
            if (isErrorEnabled(marker)) {
                addMessage(logger, Level.ERROR, marker, msg);
            }
        }

        @Override
        public void error(Marker marker, String format, Object arg) {
            if (isErrorEnabled(marker)) {
                addMessage(logger, Level.ERROR, marker, format, arg);
            }
        }

        @Override
        public void error(Marker marker, String format, Object arg1, Object arg2) {
            if (isErrorEnabled(marker)) {
                addMessage(logger, Level.ERROR, marker, format, arg1, arg2);
            }
        }

        @Override
        public void error(Marker marker, String format, Object[] argArray) {
            if (isErrorEnabled(marker)) {
                addMessage(logger, Level.ERROR, marker, format, argArray);
            }
        }

        @Override
        public void error(Marker marker, String msg, Throwable t) {
            if (isErrorEnabled(marker)) {
                addMessage(logger, Level.ERROR, marker, msg, t);
            }
        }

        private static class LoggingThread implements Runnable {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();

                while (!thread.isInterrupted()) {
                    try {
                        Message message = MESSAGES.take();

                        if (message == DUMMY) {
                            thread.interrupt();
                        } else {
                            message.log();
                        }
                    } catch (InterruptedException e) {
                        thread.interrupt();
                    }
                }

                latch.countDown();
            }
        }

        private static class ShutdownThread implements Runnable {
            @Override
            public void run() {
                MESSAGES.add(DUMMY);
            }
        }
    }
}