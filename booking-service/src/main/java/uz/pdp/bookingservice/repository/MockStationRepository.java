package uz.pdp.bookingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.bookingservice.entity.MockStation;

import java.util.UUID;

@Repository
public interface MockStationRepository extends MongoRepository<MockStation, UUID> {
}
