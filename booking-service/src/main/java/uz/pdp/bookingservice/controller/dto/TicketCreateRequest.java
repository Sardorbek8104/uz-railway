package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TicketCreateRequest {
    private int ownerId;
    private String fromStationName;
    private String toStationName;
    private double price;
    private String leavingDate;
    private String arrivalDate;
    private String routeId;
}
