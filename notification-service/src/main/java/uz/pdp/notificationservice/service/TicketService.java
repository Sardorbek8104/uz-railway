package uz.pdp.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uz.pdp.notificationservice.entity.TicketEntity;
import uz.pdp.notificationservice.repository.TicketRepository;
import uz.pdp.notificationservice.service.event.TicketCreatedEvent;
import uz.pdp.notificationservice.service.feign.UserClient;
import uz.pdp.notificationservice.service.wrapper.UserWrapper;

@Service
@RequiredArgsConstructor
public class TicketService {
    private static final String TOPIC = "ticket-topicc";
    private final TicketRepository ticketRepository;
    private final UserClient userClient;
    private final EmailService emailService;


    @KafkaListener(topics = TOPIC, groupId = "group-id1")
    public void consume(TicketCreatedEvent ticket) {
        UserWrapper user = userClient.getUser(ticket.getOwnerId());
        TicketEntity ticketEntity = TicketEntity.builder()
                .ticketId(ticket.getTicketId())
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .phone(user.getPhone())
                .routeId(ticket.getRouteId())
                .arrivalDate(ticket.getArrivalDate())
                .createdDate(ticket.getCreatedDate())
                .leavingDate(ticket.getLeavingDate())
                .status(ticket.getStatus())
                .messageSent(true)
                .build();

        try {
            emailService.sendMessage(user.getEmail(),
                    ticket.getFromStationName() + " -> " + ticket.getToStationName(),
                    ticket.getTicketId() + " CREATED please confirm it " +  ticket.getTicketId()
            );
        } catch (Exception e) {
            ticketEntity.setMessageSent(false);
        }
        ticketRepository.save(ticketEntity);
    }

}
