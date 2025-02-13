package uz.pdp.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.repository.RouteRepository;
import uz.pdp.bookingservice.repository.TicketRepository;
import uz.pdp.bookingservice.service.event.TicketCreatedEvent;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final KafkaService<TicketCreatedEvent> kafkaService;

    public Ticket createTicket(Ticket ticket) {
        if (ticket.getFromStationName().equals(ticket.getToStationName())) {
            throw new IllegalStateException("origin and destination are same stations");
        }
        Ticket newTicket = ticketRepository.save(ticket);

        Route route = ticket.getRoute();
        bindPassengersToStations(route, newTicket);
        updateRoute(route);
        kafkaService.sendTicketEventToKafka(TicketCreatedEvent.of(newTicket));

        return newTicket;
    }

    public void bindPassengersToStations(Route route, Ticket ticket) {
        Station station = route.getFirstStation();
        startToBindPassengersFromStation(station,
                ticket.getFromStationName(),
                ticket.getToStationName());
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
