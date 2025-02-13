package uz.pdp.bookingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.bookingservice.entity.RouteMap;

@Repository
public interface RouteMapRepository extends MongoRepository<RouteMap, String> {
}
