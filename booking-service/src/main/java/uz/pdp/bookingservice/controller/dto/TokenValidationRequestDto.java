package uz.pdp.bookingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TokenValidationRequestDto {
    private String token;
}
