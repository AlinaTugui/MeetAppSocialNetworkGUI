package socialnetwork.domain;

import java.time.Duration;

public enum NotificationTimer {
    SUBSCRIBED("You subscribed to this event! Stay tuned!",Duration.ofMillis(1)),
    TWO_HOURS( "The event is about to start. Don't miss it!", Duration.ofHours(2)),
    ONE_DAY( "Don't forget about tomorrow's event!", Duration.ofDays(1));
    private final String message;
    private final Duration differenceInTime;

    NotificationTimer( String message1,Duration differenceInTime1) {
        this.differenceInTime = differenceInTime1;
        this.message = message1;
    }

    public String getMessage() {
        return message;
    }

    public Duration getDifferenceInTime() {
        return differenceInTime;
    }
}
