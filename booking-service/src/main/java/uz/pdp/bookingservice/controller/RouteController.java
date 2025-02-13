package uz.pdp.bookingservice.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.bookingservice.controller.converter.RouteConverter;
import uz.pdp.bookingservice.controller.dto.RouteCreateRequestDto;
import uz.pdp.bookingservice.controller.dto.RouteSearchingResponseDto;
import uz.pdp.bookingservice.controller.dto.RouteUpdatePriceRequestDto;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("api/v1/booking/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN' , 'ADMIN')")
    @PostMapping
    public Route createRoute(@RequestBody RouteCreateRequestDto request) {
        return routeService.save(request.getRouteMapId(), request.getStartTime(), request.getTrainId());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN' , 'ADMIN')")
    @PutMapping
    public Route updateRoute(@RequestBody RouteUpdatePriceRequestDto request) {
        return routeService.updatePrice(request.getRouteId(), request.getStationId(), request.getPrice());
    }

    @GetMapping
    public List<RouteSearchingResponseDto> getAllRoutes(
            @RequestParam String fromStationName,
            @RequestParam String toStationName,
            @RequestParam String date
    ) {

        List<Route> routes = routeService.getRoutes(fromStationName, toStationName, date);
        return RouteConverter.fromEntity(routes, fromStationName, toStationName);
    }
}
