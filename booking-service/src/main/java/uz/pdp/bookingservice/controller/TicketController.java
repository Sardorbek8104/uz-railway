package uz.pdp.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.bookingservice.controller.converter.TicketConverter;
import uz.pdp.bookingservice.controller.dto.TicketCreateRequest;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.service.TicketService;

@RestController
@RequestMapping("api/v1/booking/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketConverter ticketConverter;
    private final TicketService ticketService;

    @PostMapping
    public Ticket createTicket(
            @RequestBody TicketCreateRequest request
            ) throws InterruptedException {
        Ticket entity = ticketConverter.toEntity(request);
        return ticketService.createTicket(entity);
    }
}
