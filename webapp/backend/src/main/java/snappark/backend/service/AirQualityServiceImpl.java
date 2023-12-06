package snappark.backend.service;

import org.springframework.stereotype.Service;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.AirQuality.AirQualityId;
import snappark.backend.repository.AirQualityRepository;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.SensorRepository;

@Service
public class AirQualityServiceImpl implements AirQualityService {
    private AirQualityRepository temps;
    private ParkRepository parks;
    private SensorRepository sensors;

    public AirQuality getAirQuality(Long parkId, Long sensorId){ 
        Park park=parks.findById(parkId).orElse(null);
        Sensor sensor=sensors.findById(sensorId).orElse(null);
        AirQualityId airQualityId = AirQuality.createAirQualityId(park, sensor);

        return temps.findById(airQualityId).orElse(null);    
    }
    
    public AirQuality createAirQuality(AirQuality airQuality){
        return temps.save(airQuality);
    }
}
  

 
