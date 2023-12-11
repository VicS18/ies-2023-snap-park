package snappark.backend.service;

import java.util.List;
import java.util.Optional;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Light;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.Temperature;
import snappark.backend.entity.User;

public interface ParkService {
    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park, String username);

    Park updatePark(Long parkId);

    void deletePark(Long parkId);

    List<OccupancyHistory> getParkMovements(Long parkId);

    List<Park> getParksByUsername(String username);

    Sensor getSensorById(Long id);

    User createUser(User user);

    Occupancy createOccupancy(Occupancy occupancy);
   
    Optional<Occupancy> getOccupancyByParkId(Long parkId);

    Occupancy updateOccupancy(Occupancy occupancy);

    Temperature createTemperature(Temperature temperature);

    Optional<Temperature> getTemperatureByParkAndSensor(Long parkID, Long SensorID);

    Temperature updateTemperature(Temperature temperature);

    Light createLight(Light Light);

    // Aggregations

    Double getAvgLightLevel(Long parkId);

    Integer getSensorCount(Long parkId);

    Double getAnnualRevenue(Long parkId);

    Double getMonthlyRevenue(Long parkId);

    // Light ops

    Optional<Light> getLightByParkAndSensor(Long parkID, Long SensorID);

    Light updateLight(Light Light);

    AirQuality createAirQuality(AirQuality airQuality);

    Optional<AirQuality> getAirQualityByParkAndSensor(Long parkID, Long SensorID);

    AirQuality updateAirQuality(AirQuality airQuality);

}
