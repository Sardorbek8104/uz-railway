package uz.pdp.bookingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uz.pdp.bookingservice.entity.enums.RouteState;
import uz.pdp.bookingservice.entity.enums.RouteStatus;
import uz.pdp.bookingservice.service.RouteSearchContext;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("route")
public class Route extends BaseModel {
    @Id
    private String id;
    private Station firstStation;

    @JsonIgnore
    private Instant startTime;
    private RouteStatus status;
    private RouteState state;
    private Station lastStation;

    @Transient
    private RouteSearchContext context;

    @DBRef
    private Train train;

    @JsonProperty("startTime")
    public String returnStartTimeToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(startTime);
    }
}
