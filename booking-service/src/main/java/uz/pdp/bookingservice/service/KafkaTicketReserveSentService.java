package uz.pdp.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.bookingservice.service.event.TicketCreatedEvent;

@Component
@RequiredArgsConstructor
public class KafkaTicketReserveSentService {
    private final KafkaService<TicketCreatedEvent> kafkaService;

}
