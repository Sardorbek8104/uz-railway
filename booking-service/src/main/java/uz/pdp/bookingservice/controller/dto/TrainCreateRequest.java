package uz.pdp.bookingservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainCreateRequest {
    private String trainNumber;
    private int capacity;
}