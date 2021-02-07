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
 * <p>This class is used during testing to cover several time base scenarios.</p>
 * <p>This implementation based on <a href=" https://github.com/perfectacle/time-machine">Perfectable</a>'s code, so many
 * thanks to <b>Gwonseong Yang</b>.</p>
 *
 * @author Szilard L. Fodor
 */
public class TimeMachine {

    private static TimeTraveler timeTraveler = NoopTimeTraveler.getInstance();

    private TimeMachine() {
    }

    /**
     * Time travel at the time zone(offset and region). Does NOT change the instant.
     * Just like {@link ZonedDateTime#withZoneSameInstant(ZoneId)} (ZoneId)}.
     *
     * @param zone The time zone(offset and region) what you want to travel
     * @return A zone you want to travel
     */
    public static ZoneId travelAt(final ZoneId zone) {
        timeTraveler = timeTraveler.travelAt(zone);
        return zone;
    }

    /**
     * Time travel at the instant of time. Does NOT change the zone.
     *
     * @param time The instant of time what you want to travel
     * @return The instant of time what you want to travel
     */
    public static Instant travelAt(final Instant time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return The time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     */
    public static ZonedDateTime travelAt(final ZonedDateTime time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return The time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     */
    public static OffsetDateTime travelAt(final OffsetDateTime time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with time zone(offset) and clock time(HH:mm:ss.SSS)
     *
     * @param time The time with time zone(offset) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return The time with time zone(offset) and clock time(HH:mm:ss.SSS) what you want to travel
     */
    public static OffsetTime travelAt(final OffsetTime time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS). Does NOT change zone.
     *
     * @param time The time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     * @return The time with time date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) what you want to travel
     */
    public static LocalDateTime travelAt(final LocalDateTime time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with date(yyyy-MM-dd). Does NOT change zone.
     *
     * @param time The time with date(yyyy-MM-dd) what you want to travel
     * @return The time with time date(yyyy-MM-dd) what you want to travel
     */
    public static LocalDate travelAt(final LocalDate time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Time travel at the time with clock time(HH:mm:ss.SSS). Does NOT change zone.
     *
     * @param time The time with clock time(HH:mm:ss.SSS) what you want to travel
     * @return The time with clock time(HH:mm:ss.SSS) what you want to travel
     */
    public static LocalTime travelAt(final LocalTime time) {
        timeTraveler = timeTraveler.travelAt(time);
        return time;
    }

    /**
     * Returns the instant of time when you traveled
     *
     * @return The instant of time when you traveled
     */
    public static Instant instantOfNow() {
        return timeTraveler.instantOfNow();
    }

    /**
     * Returns the time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset and region) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    public static ZonedDateTime zonedDateTimeOfNow() {
        return timeTraveler.zonedDateTimeOfNow();
    }

    /**
     * Returns the time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset) and date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    public static OffsetDateTime offsetDateTimeOfNow() {
        return timeTraveler.offsetDateTimeOfNow();
    }

    /**
     * Returns the time with time zone(offset) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with time zone(offset) and clock time(HH:mm:ss.SSS) when you traveled
     */
    public static OffsetTime offsetTimeOfNow() {
        return timeTraveler.offsetTimeOfNow();
    }

    /**
     * Returns the time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with date(yyyy-MM-dd) and clock time(HH:mm:ss.SSS) when you traveled
     */
    public static LocalDateTime localDateTimeOfNow() {
        return timeTraveler.localDateTimeOfNow();
    }

    /**
     * Returns the time with date(yyyy-MM-dd) when you traveled
     *
     * @return The time with date(yyyy-MM-dd) when you traveled
     */
    public static LocalDate localDateOfNow() {
        return timeTraveler.localDateOfNow();
    }

    /**
     * Returns the time with clock time(HH:mm:ss.SSS) when you traveled
     *
     * @return The time with clock time(HH:mm:ss.SSS) when you traveled
     */
    public static LocalTime localTimeOfNow() {
        return timeTraveler.localTimeOfNow();
    }

    /**
     * Reset time of now for real world
     */
    public static void reset() {
        timeTraveler = NoopTimeTraveler.getInstance();
    }
}