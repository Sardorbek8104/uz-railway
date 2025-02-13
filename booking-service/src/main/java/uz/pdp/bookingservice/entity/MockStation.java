package uz.pdp.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MockStation {
    private String name;
    private long arrivalTime;
    private long waitingTime;
    private MockStation nextStation;
}
