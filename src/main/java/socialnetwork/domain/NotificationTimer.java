package socialnetwork.domain;

import java.time.Duration;

public enum NotificationTimer {
    TWO_HOURS("%s will start shorly. Don't miss it!", Duration.ofHours(2)),
    ONE_DAY("Don't forget about tomorrow's event: %s", Duration.ofDays(1));
    private final String message;
    private final Duration differenceInTime;

    NotificationTimer( String message1, Duration differenceInTime1) {
        this.message = message1;
        this.differenceInTime = differenceInTime1;
    }

    public String getMessage() {
        return message;
    }

    public Duration getDifferenceInTime() {
        return differenceInTime;
    }
}
