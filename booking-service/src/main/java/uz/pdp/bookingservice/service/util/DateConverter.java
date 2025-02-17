package uz.pdp.bookingservice.service.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static Instant toInstantUtc(String date) {
        Instant instant;
        if (isToday(date)) {
            instant = Instant.now();
        } else {
            instant = getGivenDate(date);
        }
        return instant;
    }

    private static boolean isToday(String date) {
        LocalDate givenTime = LocalDate.parse(date, formatter);
        LocalDate currentTime = LocalDate.now();

        return givenTime.isEqual(currentTime);
    }

    private static Instant getGivenDate(String date) {
        return LocalDate.parse(date + "T00:00:00Z", DateTimeFormatter.ISO_DATE_TIME)
                .atStartOfDay()
                .atZone(ZoneId.of("Asia/Tashkent"))
                .toInstant();
    }

    public static LocalDateTime plus(Instant instant, long time) {
        instant = instant.plusMillis(time);
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Tashkent"));
    }

    public static LocalDateTime toLocalDateTime(String date) {
        Instant instant = toInstantUtc(date); //UTC + 5
        return LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Tashkent"));
    }

    public static Instant toInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"))
                .toInstant();
    }

    public static LocalDateTime nextDay(LocalDateTime date) {
        return date.plusDays(1).withHour(0).withMinute(0).withSecond(0);
    }
}
