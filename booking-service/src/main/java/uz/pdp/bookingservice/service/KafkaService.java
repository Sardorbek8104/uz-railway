package uz.pdp.bookingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService<T> {
    private static final String TOPIC = "ticket-topicc";
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void sendTicketEventToKafka(T object) {
        kafkaTemplate.send(TOPIC, object);
    }
}
