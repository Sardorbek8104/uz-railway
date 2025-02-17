package uz.pdp.bookingservice.service.predicates;

import lombok.RequiredArgsConstructor;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.Station;
import uz.pdp.bookingservice.service.RouteSearchContext;
import uz.pdp.bookingservice.service.util.DateConverter;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class RoutePredicate {

    public static RouteFilter routeFilter(String fromStation, String toStation, String date) {
        return new RouteFilter(fromStation, toStation, date);
    }

    @RequiredArgsConstructor
    private static class RouteFilter implements Predicate<Route> {
        private final String fromStation;
        private final String toStation;
        private final String date;

        @Override
        public boolean test(Route route) {
            Station station = route.getFirstStation();
            boolean validRoute = isValidRoute(station);
            if (!validRoute) {
                return false;
            }

            setContextToRoute(fromStation, toStation, route);
            RouteSearchContext context = route.getContext();
            LocalDateTime searchingTime = DateConverter.toLocalDateTime(date); //2025-10:15

            return context.getStartTime().isAfter(searchingTime)
                    && context.getStartTime().isBefore(DateConverter.nextDay(searchingTime));
        }

        private boolean isValidRoute(Station station) {
            while (station != null) {
                if (station.getName().equals(fromStation)) {
                    while (station !=null) {
                        if (station.getName().equals(toStation)) {
                            return true;
                        }
                        station = station.getNextStation();
                    }
                    return false;
                }
                station = station.getNextStation();
            }
            return false;
        }

        private void setContextToRoute(String fromStation,
                                              String toStation,
                                              Route route) {
            RouteSearchContext context = RouteSearchContext.builder().build();

            Station station = route.getFirstStation();
            long time = 0L;
            double price = 0;
            int max = 0;

            boolean firstStationFound = false;
            while (!station.getName().equals(toStation)) {
                time += getTotalTime(station);

                if (firstStationFound) {
                    price += station.getPrice();
                    max = Math.max(station.getPassengers(), max);
                }

                if (station.getName().equals(fromStation)) {
                    context.setStartTime(DateConverter.plus(route.getStartTime(), time));
                    firstStationFound = true;
                    max = station.getPassengers();
                }

                station = station.getNextStation();
            }
            context.setEndTime(DateConverter.plus(route.getStartTime(), time + station.getArrivalTime()));
            context.setPrice(price + station.getPrice());
            context.setBusySeats(max);

            route.setContext(context);
        }

        private long getTotalTime(Station station) {
            return station.getArrivalTime() + station.getWaitingTime();
        }
    }
}
