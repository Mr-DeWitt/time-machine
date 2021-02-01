package com.szityu.oss.timemachine;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class implements the {@link TimeTraveler} functionality, which can be used for testing purposes.
 * This implementation is immutable.
 *
 * @author Szilard L. Fodor
 */
class RealTimeTraveler implements TimeTraveler {

    private final Clock clock;

    protected RealTimeTraveler(Clock clock) {
        this.clock = clock;
    }

    @Override
    public RealTimeTraveler travelAt(final ZoneId zone) {
        return new RealTimeTraveler(clock.withZone(zone));
    }

    @Override
    public RealTimeTraveler travelAt(final Instant time) {
        return new RealTimeTraveler(Clock.fixed(time, clock.getZone()));
    }

    @Override
    public RealTimeTraveler travelAt(final ZonedDateTime time) {
        return new RealTimeTraveler(Clock.fixed(time.toInstant(), time.getZone()));
    }

    @Override
    public RealTimeTraveler travelAt(final OffsetDateTime time) {
        return new RealTimeTraveler(Clock.fixed(time.toInstant(), time.getOffset()));
    }

    @Override
    public RealTimeTraveler travelAt(final OffsetTime time) {
        return new RealTimeTraveler(Clock.fixed(time.atDate(localDateOfNow()).toInstant(), time.getOffset()));
    }

    @Override
    public RealTimeTraveler travelAt(final LocalDateTime time) {
        return new RealTimeTraveler(Clock.fixed(time.atZone(clock.getZone()).toInstant(), clock.getZone()));
    }

    @Override
    public RealTimeTraveler travelAt(final LocalDate time) {
        return new RealTimeTraveler(Clock.fixed(time.atStartOfDay(clock.getZone()).toInstant(), clock.getZone()));
    }

    @Override
    public RealTimeTraveler travelAt(final LocalTime time) {
        return new RealTimeTraveler(Clock.fixed(time.atDate(localDateOfNow()).atZone(clock.getZone()).toInstant(), clock.getZone()));
    }

    @Override
    public Instant instantOfNow() {
        return Instant.now(clock);
    }

    @Override
    public ZonedDateTime zonedDateTimeOfNow() {
        return ZonedDateTime.now(clock);
    }

    @Override
    public OffsetDateTime offsetDateTimeOfNow() {
        return OffsetDateTime.now(clock);
    }

    @Override
    public OffsetTime offsetTimeOfNow() {
        return OffsetTime.now(clock);
    }

    @Override
    public LocalDateTime localDateTimeOfNow() {
        return LocalDateTime.now(clock);
    }

    @Override
    public LocalDate localDateOfNow() {
        return LocalDate.now(clock);
    }

    @Override
    public LocalTime localTimeOfNow() {
        return LocalTime.now(clock);
    }
}
