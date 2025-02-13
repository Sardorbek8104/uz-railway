package uz.pdp.bookingservice.controller.filter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.bookingservice.controller.dto.TokenValidationRequestDto;
import uz.pdp.bookingservice.controller.dto.TokenValidationResponseDto;

@FeignClient(name = "user-service-server", url = "http://user-service-server:8080")
public interface JwtValidation {

    @PostMapping("/api/v1/user/token/validate")
    TokenValidationResponseDto validateToken(TokenValidationRequestDto requestDto);
}
