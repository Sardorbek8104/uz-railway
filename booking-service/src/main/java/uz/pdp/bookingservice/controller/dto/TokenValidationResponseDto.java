package uz.pdp.bookingservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@AllArgsConstructor
@Builder
@Data
public class TokenValidationResponseDto {
    private String username;
    private Set<String> authorities;
}
