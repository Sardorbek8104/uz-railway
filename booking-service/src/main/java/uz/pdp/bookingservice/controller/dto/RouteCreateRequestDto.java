package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouteCreateRequestDto {
    private String routeMapId;
    private String startTime;
    private String trainId;
}
