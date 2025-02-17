package uz.pdp.bookingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.bookingservice.controller.dto.TokenValidationRequestDto;
import uz.pdp.bookingservice.controller.dto.TokenValidationResponseDto;

@FeignClient(name = "user-service", url = "http://user-service:8080")
public interface UserClient {

    @PostMapping("/api/v1/user/token/validate")
    TokenValidationResponseDto validateToken(TokenValidationRequestDto requestDto);
}
