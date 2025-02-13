package uz.pdp.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@Builder
@Document("train")
public class Train extends BaseModel {
    @Id
    private String id;
    private String trainNumber;
    private int capacity;
}
