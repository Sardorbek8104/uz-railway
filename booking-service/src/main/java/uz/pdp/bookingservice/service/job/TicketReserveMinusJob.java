package uz.pdp.bookingservice.service.job;

import org.springframework.stereotype.Component;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.entity.enums.TicketStatus;
import uz.pdp.bookingservice.repository.RouteRepository;
import uz.pdp.bookingservice.repository.TicketRepository;
import uz.pdp.bookingservice.service.KafkaService;
import uz.pdp.bookingservice.service.event.TicketCreatedEvent;

import java.time.Instant;

@Component
public class TicketReserveMinusJob implements Job {
    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final KafkaService<TicketCreatedEvent> kafkaService;

    public TicketReserveMinusJob(TicketRepository ticketRepository, RouteRepository routeRepository, KafkaService<TicketCreatedEvent> kafkaService) {
        this.ticketRepository = ticketRepository;
        this.routeRepository = routeRepository;
        this.kafkaService = kafkaService;
    }


    @Override
    public ReserveType type() {
        return ReserveType.MINUS;
    }

    @Override
    public void execute(Ticket ticket) {
        Instant limit = ticket.getCreatedDate().plusSeconds(120);
        Instant now = Instant.now();
        if (now.isAfter(limit)) {

            unbindPassengersToStations(ticket);
            updateRoute(ticket.getRoute());

            ticket.setStatus(TicketStatus.ABORTED);
            ticketRepository.save(ticket);

            kafkaService.sendTicketEventToKafka(TicketCreatedEvent.of(ticket));
        }
    }

    public void unbindPassengersToStations(Ticket ticket) {
        Station station = ticket.getRoute().getFirstStation();
        startToUnBindPassengersFromStation(station,
                ticket.getFromStationName(),
                ticket.getToStationName());
    }

    private void startToUnBindPassengersFromStation(Station station,
                                                  String fromStationName,
                                                  String toStationName) {
        while (station != null) {
            if (fromStationName.equals(station.getName())) {
                station.setPassengers(station.getPassengers() - 1);
                station = station.getNextStation();
                finishToUnBindPassengersUntilToStation(station, toStationName);
                return;
            }
            station = station.getNextStation();
        }
    }

    private void finishToUnBindPassengersUntilToStation(Station station,
                                                      String toStationName) {
        while (station != null) {
            if (toStationName.equals(station.getName())) {
                return;
            }
            station.setPassengers(station.getPassengers() - 1);
            station = station.getNextStation();
        }
    }

    private void updateRoute(Route route) {
        routeRepository.save(route);
    }
}
