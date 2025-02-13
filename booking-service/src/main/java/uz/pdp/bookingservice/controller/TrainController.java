package uz.pdp.bookingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.bookingservice.controller.dto.TrainCreateRequest;
import uz.pdp.bookingservice.entity.Train;
import uz.pdp.bookingservice.repository.TrainRepository;

@RestController
@RequestMapping("api/v1/booking/train")
@RequiredArgsConstructor
public class TrainController {
    private final TrainRepository trainRepository;

    @PostMapping
    public Train saveTrain(@RequestBody TrainCreateRequest trainCreateRequest) {
        return trainRepository.save(Train.builder()
                .trainNumber(trainCreateRequest.getTrainNumber())
                .capacity(trainCreateRequest.getCapacity()).build());
    }
}
