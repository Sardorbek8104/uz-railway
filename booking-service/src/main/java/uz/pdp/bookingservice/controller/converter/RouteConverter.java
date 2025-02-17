package uz.pdp.bookingservice.controller.converter;

import uz.pdp.bookingservice.controller.dto.RouteSearchingResponseDto;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.service.RouteSearchContext;

import java.util.List;

public class RouteConverter {
    public static List<RouteSearchingResponseDto> fromEntity(List<Route> routes) {
        return routes.stream().map(RouteConverter::fromEntity).toList();
    }

    private static RouteSearchingResponseDto fromEntity(Route route) {
        RouteSearchContext context = route.getContext();
        return RouteSearchingResponseDto.builder()
                .routeId(route.getId())
                .price(context.getPrice())
                .arrivalTime(context.getEndTime())
                .departureTime(context.getStartTime())
                .firstStationName(route.getFirstStation().getName())
                .lastStationName(route.getLastStation().getName())
                .trainNumber(route.getTrain().getTrainNumber())
                .availableSeats(route.getTrain().getCapacity() - context.getBusySeats())
                .build();
    }
}
