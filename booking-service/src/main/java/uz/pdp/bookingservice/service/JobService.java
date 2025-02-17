package uz.pdp.bookingservice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.entity.enums.TicketStatus;
import uz.pdp.bookingservice.repository.TicketRepository;
import uz.pdp.bookingservice.service.event.TicketCreatedEvent;
import uz.pdp.bookingservice.service.job.Job;
import uz.pdp.bookingservice.service.job.ReserveType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final KafkaService<TicketCreatedEvent> kafkaService;
    private final Map<ReserveType, Job> jobMap;
    private final TicketRepository ticketRepository;


    public JobService(KafkaService<TicketCreatedEvent> kafkaService,
                      List<Job> jobs, TicketRepository ticketRepository) {
        this.kafkaService = kafkaService;
        this.jobMap = jobs.stream().collect(Collectors.toMap(Job::type, job -> job));
        this.ticketRepository = ticketRepository;
    }

    @Async
    public void runJob(Ticket ticket) throws InterruptedException {
        Job job = jobMap.get(ReserveType.PLUS);
        job.execute(ticket);

        Thread.sleep(60000);
        kafkaService.sendTicketEventToKafka(TicketCreatedEvent.of(ticket));
    }


    @Scheduled(fixedRate = 60000)
    public void execute() {
        List<Ticket> ticketList = ticketRepository.findAllByStatusIs(TicketStatus.CREATED);
        Job job = jobMap.get(ReserveType.MINUS);
        ticketList.forEach(job::execute);
    }
}
