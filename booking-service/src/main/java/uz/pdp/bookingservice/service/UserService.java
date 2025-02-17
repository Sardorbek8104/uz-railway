package uz.pdp.bookingservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.bookingservice.client.UserClient;
import uz.pdp.bookingservice.controller.dto.TokenValidationRequestDto;
import uz.pdp.bookingservice.controller.dto.TokenValidationResponseDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;
    private final RestTemplate restTemplate;
    private static final String AUTH_TOKEN_VALIDATION_URL = "http://user-service/api/v1/user/token/validate";

    @CircuitBreaker(name = "booking-service", fallbackMethod = "fallbackMethod")
    public TokenValidationResponseDto validateToken(String token) {
        TokenValidationRequestDto requestBody = new TokenValidationRequestDto(token);
        ResponseEntity<TokenValidationResponseDto> res = restTemplate.exchange(
                AUTH_TOKEN_VALIDATION_URL,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                new ParameterizedTypeReference<>() {
                }
        );
        TokenValidationResponseDto data = res.getBody();
        return data;
    }

    private String fallbackMethod(Exception e) {
        e.printStackTrace();
        return "User-Service is not working temporary";
    }
}
