package uz.pdp.bookingservice.service.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.repository.RouteRepository;

@Component
@RequiredArgsConstructor
public class TicketReservePlusJob implements Job {
    private final RouteRepository routeRepository;

    @Override
    public ReserveType type() {
        return ReserveType.PLUS;
    }

    @Override
    public void execute(Ticket ticket) {
        bindPassengersToStations(ticket);
    }

    public void bindPassengersToStations(Ticket ticket) {
        Route route = ticket.getRoute();
        Station station = route.getFirstStation();
        startToBindPassengersFromStation(station,
                ticket.getFromStationName(),
                ticket.getToStationName());

        updateRoute(route);
    }

    private void startToBindPassengersFromStation(Station station,
                                                  String fromStationName,
                                                  String toStationName) {
        while (station != null) {
            if (fromStationName.equals(station.getName())) {
                station.setPassengers(station.getPassengers() + 1);
                station = station.getNextStation();
                finishToBindPassengersUntilToStation(station, toStationName);
                return;
            }
            station = station.getNextStation();
        }
    }

    private void finishToBindPassengersUntilToStation(Station station,
                                                      String toStationName) {
        while (station != null) {
            if (toStationName.equals(station.getName())) {
                return;
            }
            station.setPassengers(station.getPassengers() + 1);
            station = station.getNextStation();
        }
    }

    private void updateRoute(Route route) {
        routeRepository.save(route);
    }
}
