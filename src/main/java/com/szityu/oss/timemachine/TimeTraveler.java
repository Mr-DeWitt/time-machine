package com.szityu.oss.timemachine;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Implementations of this interface achieve time traveling functionality, and they should be <b>immutable</b>.
 *
 * @author Szilard L. Fodor
 */
interface TimeTraveler {

    /**
     * Time travel at the time zone(offset and region). Does NOT change the instant.
     * Just like {@link ZonedDateTime#withZoneSameLocal(ZoneId)}.
     *
     * @param zone The time zone(offset and region) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final ZoneId zone);

    /**
     * Time travel at the instant of time. Does NOT change the zone.
     *
     * @param time The instant of time what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final Instant time);

    /**
     * Time travel at the time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final ZonedDateTime time);

    /**
     * Time travel at the time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final OffsetDateTime time);

    /**
     * Time travel at the time with time zone(offset) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final OffsetTime time);

    /**
     * Time travel at the time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS). Does NOT change zone.
     * Does NOT change the previously set Zone.
     *
     * @param time The time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final LocalDateTime time);

    /**
     * Time travel at the time with date(yyyy-MM-dd). Does NOT change zone.
     *
     * @param time The time with date(yyyy-MM-dd) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final LocalDate time);

    /**
     * Time travel at the time with clock time(HH:mm:ss.SSS). Does NOT change zone.
     *
     * @param time The time with clock time(HH:mm:ss.SSS) what you want to travel
     * @return A new, modified {@link TimeTraveler} instance
     */
    TimeTraveler travelAt(final LocalTime time);

    /**
     * Returns the instant of time when you traveled
     *
     * @return The instant of time when you traveled
     */
    Instant instantOfNow();

    /**
     * Returns the time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    ZonedDateTime zonedDateTimeOfNow();

    /**
     * Returns the time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    OffsetDateTime offsetDateTimeOfNow();

    /**
     * Returns the time with time zone(offset) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset) and clock time(HH:mm:ss.SSS) when you traveled
     */
    OffsetTime offsetTimeOfNow();

    /**
     * Returns the time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    LocalDateTime localDateTimeOfNow();

    /**
     * Returns the time with date(yyyy-MM-dd) when you traveled
     *
     * @return The time with date(yyyy-MM-dd) when you traveled
     */
    LocalDate localDateOfNow();

    /**
     * Returns the time with clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with clock time(HH:mm:ss.SSS) when you traveled
     */
    LocalTime localTimeOfNow();

}