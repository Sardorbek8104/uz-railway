package uz.pdp.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uz.pdp.bookingservice.entity.enums.TicketStatus;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
@Document("ticket")
public class Ticket extends BaseModel{
    @Id
    private String ticketId;
    private int ownerId;
    private String fromStationName;
    private String toStationName;
    private Double price;
    private Instant createdDate;
    private Instant leavingDate;
    private Instant arrivalDate;
    private TicketStatus status;
    @DBRef
    private Route route;
}
