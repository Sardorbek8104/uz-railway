package uz.pdp.bookingservice.service;

import org.springframework.stereotype.Service;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.repository.TicketRepository;
import uz.pdp.bookingservice.service.validation.TicketValidation;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketValidation ticketValidation;
    private final JobService jobService;

    public TicketService(TicketRepository ticketRepository,
                         TicketValidation ticketValidation,
                         JobService jobService) {
        this.ticketRepository = ticketRepository;
        this.ticketValidation = ticketValidation;
        this.jobService = jobService;
    }


    public Ticket createTicket(Ticket ticket) throws InterruptedException {
        if (ticket.getFromStationName().equals(ticket.getToStationName())) {
            throw new IllegalStateException("origin and destination are same stations");
        }
        ticketValidation.validateAvailableSeatsOrThrowNotFoundException(ticket);

        Ticket newTicket = ticketRepository.save(ticket);
        jobService.runJob(newTicket);
        return newTicket;
    }

}
