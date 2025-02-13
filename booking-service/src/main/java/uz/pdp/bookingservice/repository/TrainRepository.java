package uz.pdp.bookingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.bookingservice.entity.Train;

@Repository
public interface TrainRepository extends MongoRepository<Train, String> {
}
