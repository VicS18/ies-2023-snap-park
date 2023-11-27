package snappark.backend.service;

import java.util.List;

import snappark.backend.entity.Light;

public interface LightService {
    Light getLight(Long id);

    Light getLightBySensor(Long id);

    Light createLight(Light Light);

}
