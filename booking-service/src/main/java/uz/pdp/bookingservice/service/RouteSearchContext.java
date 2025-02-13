package uz.pdp.bookingservice.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class RouteSearchContext {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
}
