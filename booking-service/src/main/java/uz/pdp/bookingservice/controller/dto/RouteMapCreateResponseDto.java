package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class RouteMapCreateResponseDto {
    private UUID id;
    private MockStationCreateResponseDto stations;
}
