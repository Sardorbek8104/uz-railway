package uz.pdp.bookingservice.controller.converter;

import uz.pdp.bookingservice.controller.dto.MockStationCreateRequestDto;
import uz.pdp.bookingservice.controller.dto.RouteMapCreateRequestWithMockStationsDto;
import uz.pdp.bookingservice.entity.MockStation;
import uz.pdp.bookingservice.entity.RouteMap;

public class RouteMapConverter {
    public static RouteMap toEntity(RouteMapCreateRequestWithMockStationsDto request) {
        MockStationCreateRequestDto stations = request.getStations();
        MockStation mockStation = new MockStation();
        convertToEntity(mockStation, stations);
        return new RouteMap(mockStation);
    }

    private static void convertToEntity(MockStation mockStation, MockStationCreateRequestDto request) {
        if (request == null) {
            return;
        }
        if(request.getNext() != null) {
            mockStation.setNextStation(new MockStation());
        }
        setFields(mockStation, request);
        convertToEntity(mockStation.getNextStation(), request.getNext());
    }

    private static void setFields(MockStation mockStation, MockStationCreateRequestDto request) {
        mockStation.setName(request.getName());
        mockStation.setArrivalTime(request.getArrivalTime());
        mockStation.setWaitingTime(request.getWaitingTime());
    }
}
