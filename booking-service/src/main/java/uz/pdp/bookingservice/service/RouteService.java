package uz.pdp.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.bookingservice.entity.*;
import uz.pdp.bookingservice.entity.enums.RouteState;
import uz.pdp.bookingservice.entity.enums.RouteStatus;
import uz.pdp.bookingservice.exception.RecordNotFoundException;
import uz.pdp.bookingservice.repository.RouteMapRepository;
import uz.pdp.bookingservice.repository.RouteRepository;
import uz.pdp.bookingservice.repository.TrainRepository;
import uz.pdp.bookingservice.service.predicates.RoutePredicate;
import uz.pdp.bookingservice.service.util.DateConverter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteMapRepository routeMapRepository;
    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;

    public Route save(String routeMapId, String startTime, String trainId) {
        Optional<Train> train = trainRepository.findById(trainId);
        if (train.isEmpty()) {
            throw new RecordNotFoundException(String.format("Train not found with id %s", trainId));
        }
        Instant instant = DateConverter.toInstant(startTime);
        Station firstStation = getStation(routeMapId);
        Route route = Route.builder()
                .startTime(instant)
                .firstStation(firstStation)
                .lastStation(getLastStation(firstStation))
                .state(RouteState.CREATED)
                .status(RouteStatus.DRAFT)
                .train(train.get())
                .build();

        return routeRepository.save(route);
    }

    private Station getStation(String routeId) {
        Optional<RouteMap> routeMap = routeMapRepository.findById(routeId);
        if (routeMap.isEmpty()) {
            throw new RecordNotFoundException(routeId + " routeMap not found");
        }
        MockStation firstStation = routeMap.get().getFirstStation();
        Station station = new Station();
        convertToStation(firstStation, station);
        return station;
    }

    private Station getLastStation(Station station) {
        while(station.getNextStation() != null) {
            station = station.getNextStation();
        }
        return station;
    }

    private void convertToStation(MockStation mockStation, Station station) {
        if (mockStation == null) {
            return;
        }

        if (mockStation.getNextStation() != null) {
            station.setNextStation(new Station());
        }

        station.setName(mockStation.getName());
        station.setArrivalTime(mockStation.getArrivalTime());
        station.setWaitingTime(mockStation.getWaitingTime());
        convertToStation(mockStation.getNextStation(), station.getNextStation());
    }

    public Route updatePrice(String routeId, UUID stationId, double price) {
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            throw new RecordNotFoundException(routeId + " route not found");
        }
        Route routeToUpdate = route.get();
        Station firstStation = routeToUpdate.getFirstStation();
        if (firstStation.getId().equals(stationId)) {
            throw new IllegalStateException("access denied for the firstStation");
        }
        setPrice(firstStation, stationId, price);
        setRouteStatus(routeToUpdate, firstStation.getNextStation());
        return routeRepository.save(routeToUpdate);
    }

    private void setPrice(Station station, UUID stationId, double price) {
        while (station != null) {
            if (station.getId().equals(stationId)) {
                station.setPrice(price);
                return;
            }
            station = station.getNextStation();
        }
    }

    private void setRouteStatus(Route route, Station station) {
        while (station != null) {
            if (station.getPrice() == 0.0) {
                route.setStatus(RouteStatus.NOT_COMPLETED);
                return;
            }
            station = station.getNextStation();
        }
        route.setStatus(RouteStatus.COMPLETED);
    }


    public List<Route> getRoutes(String fromStation, String toStation, String date) {
        if (fromStation.equals(toStation)) {
            throw new IllegalStateException("origin and destination are same stations");
        }
        List<Route> routes = routeRepository.findAllByStateIsNot(RouteState.COMPLETED);

        return routes.stream()
                .filter(RoutePredicate.routeFilter(fromStation, toStation, date))
                .toList();
    }
}
