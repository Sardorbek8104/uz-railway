package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class MockStationCreateResponseDto {
    private UUID id;
    private String name;
    private long arrivalTime;
    private long waitingTime;
    private MockStationCreateResponseDto next;
}
