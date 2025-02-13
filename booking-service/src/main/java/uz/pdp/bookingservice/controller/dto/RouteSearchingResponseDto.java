package uz.pdp.bookingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class RouteSearchingResponseDto {
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String firstStationName;
    private String lastStationName;
    private int availableSeats;
    private double price;
    private String trainNumber;
    private String routeId;
}
