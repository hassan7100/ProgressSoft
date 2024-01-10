package org.progresssoft.Utils;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class TimeSet<T> {
    private final Set<TimedElement<T>> set;
    private final long expirationTimeMillis;
    private final ScheduledExecutorService cleanupScheduler;

    public TimeSet(long expirationTimeMillis) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.expirationTimeMillis = expirationTimeMillis;
        this.cleanupScheduler = Executors.newScheduledThreadPool(1);
        scheduleCleanupTask();
    }
    public boolean add(T element) {
        long currentMillis = System.currentTimeMillis();
        synchronized (set) {
            return set.add(new TimedElement<>(element, currentMillis));
        }
    }
    public boolean contains(T element) {
        synchronized (set) {
            return set.stream()
                    .map(TimedElement::getElement)
                    .anyMatch(e -> Objects.equals(e, element));
        }
    }
    public int size() {
        synchronized (set) {
            return set.size();
        }
    }
    private void scheduleCleanupTask() {
        cleanupScheduler.scheduleAtFixedRate(
                this::cleanupExpiredElements,
                expirationTimeMillis,
                expirationTimeMillis,
                TimeUnit.MILLISECONDS
        );
    }

    private void cleanupExpiredElements() {
        long currentMillis = System.currentTimeMillis();
        synchronized (set) {
            set.removeIf(timedElement -> hasExpired(currentMillis, timedElement));
        }
    }
    private boolean hasExpired(long currentMillis, TimedElement<T> timedElement) {
        return (currentMillis - timedElement.getTimestamp()) > expirationTimeMillis;
    }
    @Getter
    private static class TimedElement<U> {
        private final U element;
        private final long timestamp;
        public TimedElement(U element, long timestamp) {
            this.element = element;
            this.timestamp = timestamp;
        }
    }
}