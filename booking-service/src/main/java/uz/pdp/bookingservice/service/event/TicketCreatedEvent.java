package uz.pdp.bookingservice.service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.entity.enums.TicketStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCreatedEvent {
    private String ticketId;
    private int ownerId;
    private String fromStationName;
    private String toStationName;
    private TicketStatus status;
    private String routeId;
    private Double price;
    private Instant createdDate;
    private Instant leavingDate;
    private Instant arrivalDate;

    public static TicketCreatedEvent of(Ticket ticket) {
        return TicketCreatedEvent.builder()
                .ticketId(ticket.getTicketId())
                .ownerId(ticket.getOwnerId())
                .fromStationName(ticket.getFromStationName())
                .toStationName(ticket.getToStationName())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .routeId(ticket.getRoute().getId())
                .createdDate(ticket.getCreatedDate())
                .leavingDate(ticket.getLeavingDate())
                .arrivalDate(ticket.getArrivalDate())
                .build();
    }
}
