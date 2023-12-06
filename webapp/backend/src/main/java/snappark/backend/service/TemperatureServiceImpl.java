package snappark.backend.service;

import org.springframework.stereotype.Service;

import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.Temperature;
import snappark.backend.entity.Temperature.TemperatureId;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.SensorRepository;
import snappark.backend.repository.TemperatureRepository;

@Service
public class TemperatureServiceImpl implements TemperatureService {
    private TemperatureRepository temps;
    private ParkRepository parks;
    private SensorRepository sensors;

    public Temperature getTemperature(Long parkId, Long sensorId){ 
        Park park=parks.findById(parkId).orElse(null);
        Sensor sensor=sensors.findById(sensorId).orElse(null);
        TemperatureId temperatureId =Temperature.createTemperatureId(park, sensor);

        return temps.findById(temperatureId).orElse(null);    
    }
    
    public Temperature createTemperature(Temperature Temperature){
        return temps.save(Temperature);
    }
}

 
