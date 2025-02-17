package uz.pdp.bookingservice.service.validation;

import org.springframework.stereotype.Component;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.entity.Ticket;
import uz.pdp.bookingservice.exception.RecordNotFoundException;

@Component
public class TicketValidation {

    public void validateAvailableSeatsOrThrowNotFoundException(Ticket ticket) {
        Route route = ticket.getRoute();

        String fromStationName = ticket.getFromStationName();
        Station station = route.getFirstStation();
        while (!station.getName().equals(fromStationName)) {
            station = station.getNextStation();
        }

        int capacity = route.getTrain().getCapacity();
        int reservedSeats = calculateMaxPassBetweenFromAndToStation(station, ticket.getToStationName());

        if(reservedSeats == capacity) {
            throw new RecordNotFoundException(String.format("all seats are reserved for %s", ticket.getFromStationName()));
        }
    }

    private int calculateMaxPassBetweenFromAndToStation(Station fromStation, String toStationName) {
        Station station = fromStation;
        int max = station.getPassengers();
        while (!station.getName().equals(toStationName)) {
            if (max < station.getPassengers()) {
                max = station.getPassengers();
            }
            station = station.getNextStation();
        }
        return max;
    }
}
