package com.szityu.oss.timemachine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class TimeMachineTest {

    @AfterEach
    void tearDown() {
        TimeMachine.reset();
    }

    @Test
    void travelWith_zoneId() {
        //GIVEN
        ZoneOffset originalZone = ZoneOffset.ofHours(1);
        ZonedDateTime originalDateTime = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), originalZone);
        TimeMachine.travelAt(originalDateTime);
        ZoneOffset newZone = ZoneOffset.ofHours(2);

        // WHEN
        TimeMachine.travelAt(newZone);

        // THEN
        assertAllTimesCloseTo(
                originalDateTime.toInstant(),
                originalDateTime,
                originalDateTime.toOffsetDateTime(),
                originalDateTime.toOffsetDateTime().toOffsetTime(),
                LocalDateTime.now().withHour(13).truncatedTo(HOURS), // As we query for "local time" from point of TimeMachine view
                LocalDate.now(),
                LocalTime.of(13, 0));
        assertZoneIs(newZone);
    }

    @Test
    void travelWith_instant() {
        //GIVEN
        ZoneOffset originalZone = ZoneOffset.ofHours(1);
        ZonedDateTime originalDateTime = ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0)), originalZone);
        Instant newInstant = originalDateTime.plusHours(1).toInstant();

        // WHEN
        TimeMachine.travelAt(newInstant);

        // THEN
        ZonedDateTime expectedDateTime = originalDateTime.plusHours(1);
        assertAllTimesCloseTo(
                expectedDateTime.toInstant(),
                expectedDateTime,
                expectedDateTime.toOffsetDateTime(),
                expectedDateTime.toOffsetDateTime().toOffsetTime(),
                expectedDateTime.toLocalDateTime(),
                LocalDate.now(),
                expectedDateTime.toLocalTime());
        assertZoneIs(ZoneId.systemDefault());
    }

    @Test
    void travelWith_zonedDateTime() {
        //GIVEN
        int newOffsetInHours = getShiftedSystemZone(1);
        ZoneOffset newZone = ZoneOffset.ofHours(newOffsetInHours);
        ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(newZone); // + 1 hour + 1 offset = the same instant (except for local times)

        // WHEN
        TimeMachine.travelAt(zonedDateTime);

        // THEN
        assertAllTimesCloseTo(
                Instant.now(),
                ZonedDateTime.now(),
                OffsetDateTime.now(),
                OffsetTime.now(),
                LocalDateTime.now().plusHours(1), // As we query for "local time" from point of TimeMachine view
                LocalDate.now(),
                LocalTime.now().plusHours(1));
        assertZoneIs(newZone);
    }

    @Test
    void travelWith_offsetDateTime() {
        //GIVEN
        ZoneOffset originalZone = ZoneOffset.ofHours(1);
        TimeMachine.travelAt(ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 42)), originalZone));

        ZoneOffset newZone = ZoneOffset.ofHours(2);
        // + 2 hours + 1 offset = +1 hour in the same instant (except for local times = +2 hours)
        OffsetDateTime offsetDateTime = TimeMachine.offsetDateTimeOfNow().plusHours(1).withOffsetSameInstant(newZone);

        // WHEN
        TimeMachine.travelAt(offsetDateTime);

        // THEN
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.now(originalZone).withHour(13).withMinute(42).truncatedTo(MINUTES);
        assertAllTimesCloseTo(
                expectedZonedDateTime.toInstant(),
                expectedZonedDateTime,
                expectedZonedDateTime.toOffsetDateTime(),
                expectedZonedDateTime.toOffsetDateTime().toOffsetTime(),
                LocalDateTime.now().withHour(14).withMinute(42).truncatedTo(MINUTES), // As we query for "local time" from point of TimeMachine view
                LocalDate.now(),
                LocalTime.of(14, 42));
        assertOffsetIs(offsetDateTime.getOffset());
        assertZoneIs(newZone);
    }

    @Test
    void travelWith_offsetTime() {
        //GIVEN
        ZoneOffset originalZone = ZoneOffset.ofHours(1);
        TimeMachine.travelAt(ZonedDateTime.of(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 42)), originalZone));

        ZoneOffset newZone = ZoneOffset.ofHours(2);
        // + 2 hours + 1 offset = +1 hour in the same instant (except for local times = +2 hours)
        OffsetTime offsetTime = TimeMachine.localTimeOfNow().plusHours(2).atOffset(newZone);

        // WHEN
        TimeMachine.travelAt(offsetTime);

        // THEN
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.now(originalZone).withHour(13).withMinute(42).truncatedTo(MINUTES);
        assertAllTimesCloseTo(
                expectedZonedDateTime.toInstant(),
                expectedZonedDateTime,
                expectedZonedDateTime.toOffsetDateTime(),
                expectedZonedDateTime.toOffsetDateTime().toOffsetTime(),
                LocalDateTime.now().withHour(14).withMinute(42).truncatedTo(MINUTES), // As we query for "local time" from point of TimeMachine view
                LocalDate.now(),
                LocalTime.of(14, 42));
        assertOffsetIs(offsetTime.getOffset());
        assertZoneIs(newZone);
    }

    @Test
    void travelWith_localDateTime() {
        //GIVEN
        LocalDateTime goalDateTime = LocalDateTime.now().plusDays(3).plusHours(42);

        // WHEN
        TimeMachine.travelAt(goalDateTime);

        // THEN
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.now().plusDays(3).plusHours(42);
        assertAllTimesCloseTo(
                expectedZonedDateTime.toInstant(),
                expectedZonedDateTime,
                expectedZonedDateTime.toOffsetDateTime(),
                expectedZonedDateTime.toOffsetDateTime().toOffsetTime(),
                expectedZonedDateTime.toLocalDateTime(),
                expectedZonedDateTime.toLocalDateTime().toLocalDate(),
                expectedZonedDateTime.toLocalDateTime().toLocalTime());
        assertZoneIs(ZoneId.systemDefault());
    }

    @Test
    void travelWith_localDateTime_doesNotChangeZone() {
        //GIVEN
        int originalOffsetInHours = getShiftedSystemZone(2);
        ZoneId originalZone = ZoneOffset.ofHours(originalOffsetInHours);
        TimeMachine.travelAt(originalZone);
        LocalDateTime goalDateTime = LocalDateTime.now().plusDays(3).plusHours(42);

        // WHEN
        TimeMachine.travelAt(goalDateTime);

        // THEN
        assertZoneIs(originalZone);
    }

    @Test
    void travelWith_localDate() {
        //GIVEN
        LocalDate goalDate = LocalDate.now().plusDays(3);

        // WHEN
        TimeMachine.travelAt(goalDate);

        // THEN
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.now().plusDays(3).truncatedTo(DAYS);
        assertAllTimesCloseTo(
                expectedZonedDateTime.toInstant(),
                expectedZonedDateTime,
                expectedZonedDateTime.toOffsetDateTime(),
                expectedZonedDateTime.toOffsetDateTime().toOffsetTime(),
                expectedZonedDateTime.toLocalDateTime(),
                expectedZonedDateTime.toLocalDateTime().toLocalDate(),
                expectedZonedDateTime.toLocalDateTime().toLocalTime());
        assertZoneIs(ZoneId.systemDefault());
    }

    @Test
    void travelWith_localDate_doesNotChangeZone() {
        //GIVEN
        int originalOffsetInHours = getShiftedSystemZone(2);
        ZoneId originalZone = ZoneOffset.ofHours(originalOffsetInHours);
        TimeMachine.travelAt(originalZone);
        LocalDate goalDate = LocalDate.now().plusDays(3);

        // WHEN
        TimeMachine.travelAt(goalDate);

        // THEN
        assertZoneIs(originalZone);
    }

    @Test
    void travelWith_localTime() {
        // GIVEN
        LocalTime goalTime = LocalTime.now().plusHours(3);

        // WHEN
        TimeMachine.travelAt(goalTime);

        // THEN
        ZonedDateTime expectedZonedDateTime = ZonedDateTime.of(LocalDate.now(), goalTime, ZoneOffset.systemDefault());
        assertAllTimesCloseTo(
                expectedZonedDateTime.toInstant(),
                expectedZonedDateTime,
                expectedZonedDateTime.toOffsetDateTime(),
                expectedZonedDateTime.toOffsetDateTime().toOffsetTime(),
                expectedZonedDateTime.toLocalDateTime(),
                expectedZonedDateTime.toLocalDateTime().toLocalDate(),
                expectedZonedDateTime.toLocalDateTime().toLocalTime());
        assertZoneIs(ZoneId.systemDefault());
    }

    @Test
    void travelWith_localTime_doesNotChangeZone() {
        //GIVEN
        int originalOffsetInHours = getShiftedSystemZone(2);
        ZoneId originalZone = ZoneOffset.ofHours(originalOffsetInHours);
        TimeMachine.travelAt(originalZone);
        LocalTime goalTime = LocalTime.now().plusHours(3);

        // WHEN
        TimeMachine.travelAt(goalTime);

        // THEN
        assertZoneIs(originalZone);
    }

    @Test
    void whenNotTraveled() {
        // GIVEN
        TimeMachine.travelAt(LocalDateTime.now().plusDays(3));

        // WHEN
        TimeMachine.reset();

        // THEN
        assertAllTimesCloseTo(
                Instant.now(),
                ZonedDateTime.now(),
                OffsetDateTime.now(),
                OffsetTime.now(),
                LocalDateTime.now(),
                LocalDate.now(),
                LocalTime.now());
    }

    private void assertAllTimesCloseTo(
            Instant instant,
            ZonedDateTime zonedDateTime,
            OffsetDateTime offsetDateTime,
            OffsetTime offsetTime,
            LocalDateTime localDateTime,
            LocalDate localDate,
            LocalTime localTime) {
        assertThat(TimeMachine.instantOfNow()).describedAs("Instant").isCloseTo(instant, within(1, SECONDS));
        assertThat(TimeMachine.zonedDateTimeOfNow()).describedAs("ZonedDateTime").isCloseTo(zonedDateTime, within(1, SECONDS));
        assertThat(TimeMachine.offsetDateTimeOfNow()).describedAs("OffsetDateTime").isCloseTo(offsetDateTime, within(1, SECONDS));
        assertThat(TimeMachine.offsetTimeOfNow()).describedAs("OffsetTime").isCloseTo(offsetTime, within(1, SECONDS));
        assertThat(TimeMachine.localDateTimeOfNow()).describedAs("LocalDateTime").isCloseTo(localDateTime, within(1, SECONDS));
        assertThat(TimeMachine.localDateOfNow()).describedAs("LocalDate").isEqualTo(localDate);
        assertThat(TimeMachine.localTimeOfNow()).describedAs("LocalTime").isCloseTo(localTime, within(1, SECONDS));
    }

    private void assertZoneIs(ZoneId expectedZoneId) {
        ZoneOffset expectedOffset = expectedZoneId.getRules().getOffset(TimeMachine.instantOfNow());

        assertThat(TimeMachine.zonedDateTimeOfNow().getZone()).describedAs("ZonedDateTime").isEqualTo(expectedZoneId);
        assertThat(TimeMachine.offsetDateTimeOfNow().getOffset()).describedAs("OffsetDateTime").isEqualTo(expectedOffset);
        assertThat(TimeMachine.offsetTimeOfNow().getOffset()).describedAs("OffsetTime").isEqualTo(expectedOffset);
    }

    private void assertOffsetIs(ZoneOffset expectedOffset) {
        assertThat(TimeMachine.zonedDateTimeOfNow().getOffset()).describedAs("ZonedDateTime").isEqualTo(expectedOffset);
        assertThat(TimeMachine.offsetDateTimeOfNow().getOffset()).describedAs("OffsetDateTime").isEqualTo(expectedOffset);
        assertThat(TimeMachine.offsetTimeOfNow().getOffset()).describedAs("OffsetTime").isEqualTo(expectedOffset);
    }

    private int getShiftedSystemZone(int hourOffset) {
        return ZoneId.systemDefault().getRules().getOffset(Instant.now()).getTotalSeconds() / 3600 + hourOffset;
    }

}