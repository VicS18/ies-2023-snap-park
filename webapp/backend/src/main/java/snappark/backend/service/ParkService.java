package snappark.backend.service;

import java.util.List;

import snappark.backend.entity.Park;
import snappark.backend.entity.User;

public interface ParkService {
    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park, Long userId);

    List<Park> getParksByUserId(Long id);

    User createUser(User user);
}
