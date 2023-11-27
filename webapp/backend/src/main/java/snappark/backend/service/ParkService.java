package snappark.backend.service;

import snappark.backend.entity.Park;

public interface ParkService {
    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park);
}
