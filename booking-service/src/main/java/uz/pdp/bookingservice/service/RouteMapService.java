package uz.pdp.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.bookingservice.entity.RouteMap;
import uz.pdp.bookingservice.repository.RouteMapRepository;

@Service
@RequiredArgsConstructor
public class RouteMapService {
    private final RouteMapRepository routeMapRepository;

    public RouteMap save(RouteMap routeMap) {
        return routeMapRepository.save(routeMap);
    }
}
