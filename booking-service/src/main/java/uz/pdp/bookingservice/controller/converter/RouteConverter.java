package uz.pdp.bookingservice.controller.converter;

import uz.pdp.bookingservice.controller.dto.RouteSearchingResponseDto;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.service.RouteSearchContext;

import java.util.List;

public class RouteConverter {

    public static List<RouteSearchingResponseDto> fromEntity(List<Route> routes, String fromStationName, String toStationName) {
        return routes.stream().map(route -> fromEntity(route, fromStationName, toStationName)).toList();
    }

    private static RouteSearchingResponseDto fromEntity(Route route, String fromStationName, String toStationName) {
        RouteSearchContext context = route.getContext();
        return RouteSearchingResponseDto.builder()
                .routeId(route.getId())
                .price(context.getPrice())
                .arrivalTime(context.getEndTime())
                .departureTime(context.getStartTime())
                .firstStationName(route.getFirstStation().getName())
                .lastStationName(route.getLastStation().getName())
                .trainNumber(route.getTrain().getTrainNumber())
                .availableSeats(calculateAvailableSeats(route, fromStationName, toStationName))
                .build();
    }

    private static int calculateAvailableSeats(Route route, String fromStationName, String toStationName) {
        Station station = route.getFirstStation();
        int capacity = route.getTrain().getCapacity();
        return capacity - calculateMaxOfPassengersByStation(station, fromStationName, toStationName);
    }

    private static int calculateMaxOfPassengersByStation(Station station, String fromStationName, String toStationName) {
        while (!station.getName().equals(fromStationName)) {
            station = station.getNextStation();
        }
        return calculateMaxOfPassengersByStation(station, toStationName);
    }

    private static int calculateMaxOfPassengersByStation(Station fromStation, String toStationName) {
        Station station = fromStation;
        int max = station.getPassengers();
        while (!station.getName().equals(toStationName)) {
            if (max < station.getPassengers()) {
                max = station.getPassengers();
            }
            station = station.getNextStation();
        }
        return max;
    }
}
