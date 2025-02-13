package uz.pdp.notificationservice.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.notificationservice.service.wrapper.UserWrapper;

@FeignClient(name = "user", url = "http://user-service-server:8080")
public interface UserClient {

    @GetMapping("/api/v1/user/{id}")
    UserWrapper getUser(@PathVariable("id") int userId);
}
