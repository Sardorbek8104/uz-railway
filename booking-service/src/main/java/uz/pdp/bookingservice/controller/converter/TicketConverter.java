package uz.pdp.bookingservice.controller.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.bookingservice.controller.dto.TicketCreateRequest;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.entity.enums.TicketStatus;
import uz.pdp.bookingservice.exception.RecordNotFoundException;
import uz.pdp.bookingservice.repository.RouteRepository;
import uz.pdp.bookingservice.service.util.DateConverter;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketConverter {
    private final RouteRepository routeRepository;

    public Ticket toEntity(TicketCreateRequest request) {
        Optional<Route> optionalRoute = routeRepository.findById(request.getRouteId());
        if (optionalRoute.isEmpty()) {
            throw new RecordNotFoundException(String.format("Route with id %s not found", request.getRouteId()));
        }
        return Ticket.builder()
                .route(optionalRoute.get())
                .ownerId(request.getOwnerId())
                .fromStationName(request.getFromStationName())
                .toStationName(request.getToStationName())
                .price(request.getPrice())
                .createdDate(Instant.now())
                .arrivalDate(DateConverter.toInstant(request.getArrivalDate()))
                .leavingDate(DateConverter.toInstant(request.getLeavingDate()))
                .status(TicketStatus.CREATED)
                .build();
    }
}
