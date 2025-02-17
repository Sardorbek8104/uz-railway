package uz.pdp.bookingservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("train")
public class Train extends BaseModel {
    @Id
    private String id;
    private String trainNumber;
    private int capacity;
}
