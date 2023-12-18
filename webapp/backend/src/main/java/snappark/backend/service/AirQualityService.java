package snappark.backend.service;

import snappark.backend.entity.AirQuality;

public interface AirQualityService {
    AirQuality getAirQuality(Long parkId, Long sensorId);

    AirQuality createAirQuality(AirQuality Air);
}
