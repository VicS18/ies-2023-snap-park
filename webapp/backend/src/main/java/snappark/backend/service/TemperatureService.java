package snappark.backend.service;

import snappark.backend.entity.Temperature;

public interface TemperatureService {
    Temperature getTemperature(Long parkId, Long sensorId);

    Temperature createTemperature(Temperature Temperature);
}
