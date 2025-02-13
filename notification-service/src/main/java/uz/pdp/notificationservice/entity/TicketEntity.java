package uz.pdp.notificationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.notificationservice.entity.enums.TicketStatus;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class TicketEntity {

    @Id
    private String ticketId;

    private int userId;
    private String name;
    private String email;
    private String phone;
    private String fromStationName;
    private String toStationName;
    private Double price;
    private Instant createdDate;
    private Instant leavingDate;
    private Instant arrivalDate;
    private boolean messageSent;
    private TicketStatus status;
    private String routeId;
}
