package com.szityu.oss.timemachine;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

/**
 * This implementation won't travel in time. All of it's getters are return dates/times based on the default system settings.
 * If we want to travel in time this implementation will return a {@link RealTimeTraveler} instance.
 * This implementation is immutable.
 *
 * @author Szilard L. Fodor
 */
class NoopTimeTraveler extends RealTimeTraveler {

    NoopTimeTraveler() {
        super(Clock.systemDefaultZone());
    }

    public static NoopTimeTraveler getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Instant instantOfNow() {
        return Instant.now();
    }

    @Override
    public ZonedDateTime zonedDateTimeOfNow() {
        return ZonedDateTime.now();
    }

    @Override
    public OffsetDateTime offsetDateTimeOfNow() {
        return OffsetDateTime.now();
    }

    @Override
    public OffsetTime offsetTimeOfNow() {
        return OffsetTime.now();
    }

    @Override
    public LocalDateTime localDateTimeOfNow() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate localDateOfNow() {
        return LocalDate.now();
    }

    @Override
    public LocalTime localTimeOfNow() {
        return LocalTime.now();
    }

    // Bill Pugh Singleton
    private static final class InstanceHolder {
        private static final NoopTimeTraveler INSTANCE = new NoopTimeTraveler();
    }
}
