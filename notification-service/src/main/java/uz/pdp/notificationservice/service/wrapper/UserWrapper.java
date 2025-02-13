package uz.pdp.notificationservice.service.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserWrapper {
    private int userId;
    private String name;
    private String email;
    private String phone;
}
