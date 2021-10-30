package users.info;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Ticket {
    private static int lastTicketNumber = 0;
    private final int ticketNumber;
    private final String from;
    private final String to;
    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;
    private final int carriageNumber;
    private final int place;
    private final String train;
    private final int price;

    @Builder
    public Ticket(@NotNull String from,
                  @NotNull String to,
                  @NotNull LocalDateTime departureDate,
                  @NotNull LocalDateTime arrivalDate,
                  int carriageNumber,
                  int place,
                  @NotNull String train,
                  int price) {
        this(lastTicketNumber++, from, to, departureDate, arrivalDate, carriageNumber, place, train, price);
    }
}
