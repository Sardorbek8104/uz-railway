package uz.pdp.bookingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.bookingservice.controller.converter.RouteMapConverter;
import uz.pdp.bookingservice.controller.dto.RouteMapCreateRequestWithMockStationsDto;
import uz.pdp.bookingservice.controller.dto.TokenValidationResponseDto;
import uz.pdp.bookingservice.entity.RouteMap;
import uz.pdp.bookingservice.service.RouteMapService;
import uz.pdp.bookingservice.service.UserService;

@RestController
@RequestMapping("api/v1/booking/route-map")
@RequiredArgsConstructor
public class RouteMapController {
    private final RouteMapService routeMapService;
    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public RouteMap save(
            @RequestBody RouteMapCreateRequestWithMockStationsDto request
            ) {
        RouteMap entity = RouteMapConverter.toEntity(request);
        return routeMapService.save(entity);
    }

    @PostMapping("/test")
    public TokenValidationResponseDto test(
            @RequestBody RouteMapCreateRequestWithMockStationsDto requesttt
    ) {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI5NDk0MTg1NDU4IiwiaWF0IjoxNzM5MjcyNDgyLCJleHAiOjE3MzkzNTg4ODIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1NVUEVSX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV19.1--excWHH-_k1msdOHIlmnUhJdjJ8k5DLkpppyPLrNbA5olY2JQo4w05L5gXh-ea";
        return userService.validateToken(token);
    }
}
