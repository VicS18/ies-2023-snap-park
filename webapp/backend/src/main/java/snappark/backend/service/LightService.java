package snappark.backend.service;

import snappark.backend.entity.Light;

public interface LightService {
    Light getLight(Long parkId, Long sensorId);

    Light createLight(Light Light);

}
