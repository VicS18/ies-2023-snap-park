package snappark.backend.service;

import java.util.List;
import java.util.Optional;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.AirQualityHistory;
import snappark.backend.entity.Alert;
import snappark.backend.entity.Light;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.Temperature;
import snappark.backend.entity.Transaction;
import snappark.backend.entity.User;

public interface ParkService {

    //
    // Park operations
    //

    Park getParkById(Long id);

    Park getParkByName(String name);

    Park createPark(Park park, String username);

    Park updatePark(Long parkId);

    void deletePark(Long parkId);

    OccupancyHistory createParkMovement(OccupancyHistory movement);
    
    List<OccupancyHistory> getParkMovements(Long parkId);

    List<OccupancyHistory> getParkMovementsByDate(long parkId, long startDate, long endDate);

    List<Park> getParksByUsername(String username);

    Sensor getSensorById(Long id);

    List<Sensor> getSensorsByParkId(Long parkId);

    Sensor createSensor(Sensor sensor);

    Sensor createSensor(Sensor sensor, Long parkId);

    //
    // User operations
    //

    User createUser(User user);

    Optional<User> getUserById(Long id);


    Occupancy createOccupancy(Occupancy occupancy);
   
    Optional<Occupancy> getOccupancyByParkId(Long parkId);

    Occupancy updateOccupancy(Occupancy occupancy);

    Temperature createTemperature(Temperature temperature);

    Optional<Temperature> getTemperatureByParkAndSensor(Long parkID, Long SensorID);

    Temperature updateTemperature(Temperature temperature);

    Light createLight(Light Light);

    Transaction createTransaction(Transaction transaction);
    
    // Aggregations

    Double getAvgLightLevel(Long parkId);

    Integer getSensorCount(Long parkId);

    Double getAnnualRevenue(Long parkId);

    Double getMonthlyRevenue(Long parkId);

    // Light ops

    Optional<Light> getLightByParkAndSensor(Long parkID, Long SensorID);

    Light updateLight(Light Light);

    AirQuality createAirQuality(AirQuality airQuality);
    
    AirQualityHistory createAirQualityHistory(AirQualityHistory airQuality);

    Optional<AirQuality> getAirQualityByParkAndSensor(Long parkID, Long SensorID);
    
    List<Sensor> getSensorsByPark(Long parkID);

    List<AirQualityHistory> getAirQualityByDate(long parkId, long sensorId, long startDate, long endDate);

    AirQuality updateAirQuality(AirQuality airQuality);

    // Alert ops

    Alert createAlert(Alert alert);
    List<Alert> getAllAlerts();

}
