package snappark.backend.service;

import java.util.List;

import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;

public interface ParkService {
    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park, String username);

    Park updatePark(Long parkId);

    void deletePark(Long parkId);

    List<OccupancyHistory> getParkMovements(Long parkId);

    List<Park> getParksByUsername(String username);

    User createUser(User user);
}
