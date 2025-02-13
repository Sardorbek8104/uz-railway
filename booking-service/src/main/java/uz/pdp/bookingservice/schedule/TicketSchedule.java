package uz.pdp.bookingservice.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.List;
import java.util.Optional;

@Component
@EnableScheduling
public class TicketSchedule {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private  KafkaService<TicketCreatedEvent> kafkaService;
    @Scheduled(fixedRate = 60000)
    public void schedule() {
        Instant now = Instant.now();

        List<Ticket> tickets = ticketRepository.findAllByStatus(TicketStatus.CREATED);

        for (Ticket ticket : tickets) {
            if (ticket.getCreatedAt().plusSeconds(720).isBefore(now)) {
                ticket.setStatus(TicketStatus.ABORTED);
                Optional<Route> optionalRoute = routeRepository.findById(ticket.getRoute().getId());
                if (optionalRoute.isEmpty()) {
                    throw new RuntimeException("Route not found");
                }
               Route route = optionalRoute.get();
                Station fromStation = route.getFirstStation();
                while (fromStation != null) {
                    if (fromStation.getName().equals(ticket.getFromStationName())) {
                        break;
                    }
                    fromStation = fromStation.getNextStation();
                }

                while (fromStation != null) {
                    fromStation.setPassengers(fromStation.getPassengers() - 1);
                    if (fromStation.getName().equals(ticket.getToStationName())) {
                        break;
                    }
                    fromStation = fromStation.getNextStation();
                }

                routeRepository.save(route);
                Ticket save = ticketRepository.save(ticket);
                kafkaService.sendTicketEventToKafka(TicketCreatedEvent.of(save));
            }
        }
    }
}
