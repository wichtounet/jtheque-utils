package org.jtheque.utils;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;
import ch.qos.logback.core.spi.AppenderAttachableImpl;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class AsyncAppender<E> extends UnsynchronizedAppenderBase<E> implements AppenderAttachable<E> {
    private final LoggingThread logger = new LoggingThread();
    private final Thread loggingThread = new Thread(logger);

    private BlockingQueue<E> MESSAGES;

    private int bufferSize = 50000;

    /**
     * The appenders we are forwarding events to
     */
    private final AppenderAttachableImpl<E> appenders = new AppenderAttachableImpl<E>();

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void start() {
        if (!isStarted()) {
            Iterator<Appender<E>> iter = appenders.iteratorForAppenders();

            int size = 0;
            while (iter.hasNext()) {
                iter.next().start();
                size++;
            }

            if (size == 0) {
                addError("At least one appender must be configured");
                return;
            }

            // Initialize event queue
            if (bufferSize < 1) {
                addError("Invalid buffer size " + bufferSize);

                return;
            }

            MESSAGES = new LinkedBlockingQueue<E>(bufferSize);

            loggingThread.setName("Logging-Thread");
            loggingThread.setDaemon(true);

            loggingThread.start();

            super.start();
        }
    }

    @Override
    public void stop() {
        if (isStarted()) {
            super.stop();

            loggingThread.interrupt();

            try {
                loggingThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Iterator<Appender<E>> iter = appenders.iteratorForAppenders();
            while (iter.hasNext()) {
                iter.next().stop();
            }
        }
    }

    @Override
    public void addAppender(Appender<E> newAppender) {
        appenders.addAppender(newAppender);
    }

    @Override
    public Iterator<Appender<E>> iteratorForAppenders() {
        return appenders.iteratorForAppenders();
    }

    @Override
    public Appender<E> getAppender(String name) {
        return appenders.getAppender(name);
    }

    @Override
    public boolean isAttached(Appender<E> eAppender) {
        return appenders.isAttached(eAppender);
    }

    @Override
    public void detachAndStopAllAppenders() {
        appenders.detachAndStopAllAppenders();
    }

    @Override
    public boolean detachAppender(Appender<E> eAppender) {
        return appenders.detachAppender(eAppender);
    }

    @Override
    public boolean detachAppender(String name) {
        return appenders.detachAppender(name);
    }

    @Override
    protected void append(E event) {
        if (!loggingThread.isInterrupted() && loggingThread.isAlive()) {
            if (event instanceof ILoggingEvent) {
                ILoggingEvent loggingEvent = (ILoggingEvent) event;

                loggingEvent.prepareForDeferredProcessing();
                //loggingEvent.getCallerData();
            }

            try {
                MESSAGES.put(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            appenders.appendLoopOnAppenders(event);
        }
    }

    private class LoggingThread implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();

            while (!thread.isInterrupted()) {
                try {
                    E event = MESSAGES.take();

                    appenders.appendLoopOnAppenders(event);
                } catch (InterruptedException e) {
                    thread.interrupt();
                }
            }
        }
    }
}