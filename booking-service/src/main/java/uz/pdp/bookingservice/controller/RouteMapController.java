package uz.pdp.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.bookingservice.controller.converter.RouteMapConverter;
import uz.pdp.bookingservice.controller.dto.RouteMapCreateRequestWithMockStationsDto;
import uz.pdp.bookingservice.entity.RouteMap;
import uz.pdp.bookingservice.service.RouteMapService;

@RestController
@RequestMapping("api/v1/booking/route-map")
@RequiredArgsConstructor
public class RouteMapController {
    private final RouteMapService routeMapService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public RouteMap save(
            @RequestBody RouteMapCreateRequestWithMockStationsDto request
            ) {
        RouteMap entity = RouteMapConverter.toEntity(request);
        return routeMapService.save(entity);
    }
}
