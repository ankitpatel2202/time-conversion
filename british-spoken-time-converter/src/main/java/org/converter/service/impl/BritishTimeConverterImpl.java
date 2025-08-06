package org.converter.service.impl;

import org.converter.service.api.BritishTimeConverter;
import org.converter.utils.TimeConstants;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class BritishTimeConverterImpl implements BritishTimeConverter {

    private static final String[] HOURS = {
            "twelve", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve"
    };

    private static final String[] MINUTES = {
            "o'clock", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "quarter", "sixteen", "seventeen",
            "eighteen", "nineteen", "twenty", "twenty one", "twenty two",
            "twenty three", "twenty four", "twenty five", "twenty six",
            "twenty seven", "twenty eight", "twenty nine", "half",
            "thirty one", "thirty two", "thirty three", "thirty four",
            "thirty five", "thirty six", "thirty seven", "thirty eight",
            "thirty nine", "forty", "forty one", "forty two", "forty three",
            "forty four", "forty five", "forty six", "forty seven",
            "forty eight", "forty nine", "fifty", "fifty one", "fifty two",
            "fifty three", "fifty four", "fifty five", "fifty six",
            "fifty seven", "fifty eight", "fifty nine"
    };

    @Override
    public String convertToBritishSpoken(String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            throw new IllegalArgumentException("Time string cannot be null or empty");
        }

        LocalTime time = parseTime(timeString);
        return convertTimeToSpoken(time);
    }

    private LocalTime parseTime(String timeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.TIME_FORMAT);
            return LocalTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format. Please use HH:MM format (e.g., 12:00, 14:30)");
        }
    }

    private String convertTimeToSpoken(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        // Special cases
        if (hour == 12 && minute == 0) {
            return TimeConstants.NOON;
        }
        if (hour == 0 && minute == 0) {
            return TimeConstants.MIDNIGHT;
        }

        int displayHour = hour;
        if(displayHour > 12) {
            // Convert to 12-hour format
            displayHour = displayHour % 12;
        }

        String period = getPeriod(hour, minute);

        String hourWord = HOURS[displayHour];

        // Handle different minute cases
        if (minute == 0) {
            return hourWord + " " + MINUTES[0] + " " + period;
        } else if (minute <= 30) {
            return MINUTES[minute] + " past " + hourWord + " " + period;
        } else {
            int remainingMinutes = 60 - minute;
            String nextHour = HOURS[(displayHour % 12) + 1];
            if (displayHour == 12) {
                nextHour = HOURS[1];
            }

            if (remainingMinutes == 15) {
                return "quarter to " + nextHour + " " + period;
            } else if (remainingMinutes == 30) {
                return "half past " + hourWord + " " + period;
            } else {
                return MINUTES[remainingMinutes] + " to " + nextHour + " " + period;
            }
        }
    }

    private String getPeriod(int hour, int minute) {
        String period = (hour < 12) ? TimeConstants.AM : TimeConstants.PM;


        if(hour == 23 && minute > 30) {
            // case: time > 23:30. ex: 23:55  should be five to twelve AM
            period = TimeConstants.AM;
        } else if (hour == 11 && minute > 30) {
            // case: 11:30 < time < 12:00. ex: 11:55  should be five to twelve PM
            period = TimeConstants.PM;
        }


        return period;
    }
}
