package snappark.backend.service;

import java.util.List;

import snappark.backend.entity.Park;

public interface ParkService {
    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park);

    List<Park> getParksByUserId(Long id);
}
