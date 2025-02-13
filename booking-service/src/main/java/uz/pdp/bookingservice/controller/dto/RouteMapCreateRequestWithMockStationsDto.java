package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RouteMapCreateRequestWithMockStationsDto {
    private MockStationCreateRequestDto stations;
}
