package uz.pdp.notificationservice.service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.notificationservice.entity.enums.TicketStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCreatedEvent {
    private String ticketId;
    private int ownerId;
    private String fromStationName;
    private String toStationName;
    private Double price;
    private Instant createdDate;
    private Instant leavingDate;
    private Instant arrivalDate;
    private TicketStatus status;
    private String routeId;
}
