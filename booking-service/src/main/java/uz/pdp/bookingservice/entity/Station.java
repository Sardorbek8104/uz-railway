package uz.pdp.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Station {
    private UUID id;
    private String name;
    private long arrivalTime;
    private long waitingTime;
    private double price;
    private Station nextStation;
    private int passengers;

    public Station() {
        id = UUID.randomUUID();
    }
}
