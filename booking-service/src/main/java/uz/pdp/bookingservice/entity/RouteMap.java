package uz.pdp.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("route-map")
public class RouteMap extends BaseModel {
    @Id
    private String id;
    private MockStation firstStation;

    public RouteMap(MockStation firstStation) {
        this.firstStation = firstStation;
    }
}
