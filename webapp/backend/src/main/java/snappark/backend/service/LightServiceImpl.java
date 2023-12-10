package snappark.backend.service;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Light;
import snappark.backend.entity.Light.LightId;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.repository.LightRepository;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.SensorRepository;

@Service
@AllArgsConstructor
public class LightServiceImpl implements LightService {
    private LightRepository lights;
    private ParkRepository parks;
    private SensorRepository sensors;
    public Light getLight(Long parkId, Long sensorId){ 
        Park park=parks.findById(parkId).orElse(null);
        Sensor sensor=sensors.findById(sensorId).orElse(null);
        LightId lightId = Light.createLightId(park, sensor);

        return lights.findById(lightId).orElse(null);    
    }

    public Light createLight(Light Light){
        return lights.save(Light);
    };

}
