package uz.pdp.bookingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.bookingservice.entity.Route;
import uz.pdp.bookingservice.entity.enums.RouteState;

import java.util.List;

@Repository
public interface RouteRepository extends MongoRepository<Route, String> {
    List<Route> findAllByStateIsNot(RouteState state);
}
