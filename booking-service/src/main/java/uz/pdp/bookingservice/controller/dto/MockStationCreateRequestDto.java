package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MockStationCreateRequestDto {
    private String name;
    private long arrivalTime;
    private long waitingTime;
    private MockStationCreateRequestDto next;
}
