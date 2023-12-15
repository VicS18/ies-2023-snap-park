package snappark.backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Alert;
import snappark.backend.entity.Light;
import snappark.backend.entity.Manager;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.Temperature;
import snappark.backend.entity.User;
import snappark.backend.entity.AirQuality.AirQualityId;
import snappark.backend.entity.Light.LightId;
import snappark.backend.entity.Temperature.TemperatureId;
import snappark.backend.repository.AirQualityRepository;
import snappark.backend.repository.LightRepository;
import snappark.backend.repository.ManagerRepository;
import snappark.backend.repository.OccupancyHistoryRepository;
import snappark.backend.repository.OccupancyRepository;
import snappark.backend.repository.ParkRepository;
import snappark.backend.repository.SensorRepository;
import snappark.backend.repository.TemperatureRepository;
import snappark.backend.repository.TransactionRepository;
import snappark.backend.repository.UserRepository;
import snappark.backend.repository.AlertRepository;


@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class ParkServiceImpl implements ParkService {

    @Autowired(required = true)
    private ParkRepository parkRepository;

    @Autowired(required = true)
    private SensorRepository sensorRepository;

    @Autowired(required = true)
    private ManagerRepository managerRepository;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private OccupancyRepository occupancyRepository;

    @Autowired(required = true)
    private TemperatureRepository temperatureRepository;

    @Autowired(required = true)
    private LightRepository lightRepository;

    @Autowired(required = true)
    private AirQualityRepository airQualityRepository;    

    @Autowired(required = true)
    private OccupancyHistoryRepository occupancyHistoryRepository;

    @Autowired(required = true)
    private TransactionRepository transactionRepository;

    @Autowired(required = true)
    private AlertRepository alertRepository;

    //
    // Park entity operations
    // 
    
    public Park getParkById(Long id){
        return parkRepository.findParkById(id);
    }
    
    public Park getParkByName(String name){
        return parkRepository.findParkByName(name);    
    }

    // Shouldn't receive Park object argument
    public Park createPark(Park park, String username){        
        // TODO: Deal with situation where provided userId doesn't have a corresponding User
        
        Park savedPark = parkRepository.save(park);

        User user = userRepository.findUserByName(username);

        Manager manager = new Manager(user, savedPark);

        Manager savedManager = managerRepository.save(manager);
        
        occupancyRepository.save(new Occupancy(park.getId(), 0, savedPark));

        return savedManager.getPark();
    }

    public Park updatePark(Long parkId){
        return parkRepository.findParkById(parkId);
    }

    public void deletePark(Long parkId){
        parkRepository.deleteById(parkId);
    }

    // Park aggregations

    public Integer getSensorCount(Long parkId){
        Park park = parkRepository.findParkById(parkId);
        return sensorRepository.countByPark(park);
    }

    // TODO: Generalize to monthly revenue as well

    public Double getAnnualRevenue(Long parkId){
        // TODO: Check if park exists

        // 1st of January of next year to the current year
        return transactionRepository.sumByParkIdTime(parkId, getCurrYearStart(), getNextYearStart());
    }

    public Double getMonthlyRevenue(Long parkId){
        // TODO: Check if park exists

        return transactionRepository.sumByParkIdTime(parkId, getCurrMonthStart(), getNextMonthStart());
    }

    //
    // Manager entity operations
    //

    public List<Park> getParksByUsername(String username){
        List<Manager> managers = managerRepository.findByUserName(username);
        return managers.stream().map(Manager::getPark).collect(Collectors.toList());
    }

    public OccupancyHistory createParkMovement(OccupancyHistory movement){
        return occupancyHistoryRepository.save(movement);
    }

    public List<OccupancyHistory> getParkMovements(Long parkId){
        return occupancyHistoryRepository.findByPark(parkRepository.findParkById(parkId));
    }

    public List<OccupancyHistory> getParkMovementsByDate(long parkId, long startDate, long endDate){
        Park fPark=parkRepository.findParkById(parkId);
        return occupancyHistoryRepository.findByDateBetweenAndParkOrderByDateAsc(startDate,endDate,fPark);
    }

    //
    // Sensor entity operations
    //

    public Sensor getSensorById(Long id){
        return sensorRepository.findById(id).get();
    }


    //
    // User entity operations
    //

    public User createUser(User user){
        return userRepository.save(user);
    }
    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
    //
    // Occupancy entity operations
    //
    public Occupancy createOccupancy(Occupancy occupancy){
        return occupancyRepository.save(occupancy);
    }
    
    public Optional<Occupancy> getOccupancyByParkId(Long id){
        return occupancyRepository.findById(id);
    }

    public Occupancy updateOccupancy(Occupancy occupancy){
        return occupancyRepository.save(occupancy);
    }   

    //
    // Temperature entity operations
    //
    public Temperature createTemperature(Temperature temperature){
        return temperatureRepository.save(temperature);
    }
    
    public Optional<Temperature> getTemperatureByParkAndSensor(Long parkId, Long sensorId){
        Park park=parkRepository.findParkById(parkId);
        Sensor sensor=sensorRepository.findById(sensorId).get();

        TemperatureId temp= Temperature.createTemperatureId(park,sensor);
        return temperatureRepository.findById(temp);
    }

    public Temperature updateTemperature(Temperature temperature){
        return temperatureRepository.save(temperature);
    }
    //
    // Light entity operations
    //
    public Light createLight(Light light){
        return lightRepository.save(light);
    }

    public Double getAvgLightLevel(Long parkId){
        Park park = parkRepository.findParkById(parkId);
        return lightRepository.getAvgLightLevel(park);
    }
    
    public Optional<Light> getLightByParkAndSensor(Long parkId, Long sensorId){
        Park park = parkRepository.findParkById(parkId);
        Sensor sensor = sensorRepository.findById(sensorId).get();

        LightId temp = Light.createLightId(park,sensor);
        return lightRepository.findById(temp);
    }

    public Light updateLight(Light light){
        return lightRepository.save(light);
    } 
    //
    // Air Quality entity operations
    //
    public AirQuality createAirQuality(AirQuality airQuality){
        return airQualityRepository.save(airQuality);
    }
    
    public Optional<AirQuality> getAirQualityByParkAndSensor(Long parkId, Long sensorId){
        Park park=parkRepository.findParkById(parkId);
        Sensor sensor=sensorRepository.findById(sensorId).get();

        AirQualityId temp= AirQuality.createAirQualityId(park,sensor);
        return airQualityRepository.findById(temp);
    }

    public AirQuality updateAirQuality(AirQuality airQuality){
        return airQualityRepository.save(airQuality);
    }

    public static Long getNextYearStart(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currYear = calendar.get(Calendar.YEAR);

        calendar.set(currYear - 1901, 1, 1);
        
        return calendar.getTimeInMillis();
    }

    public static Long getNextMonthStart(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currMonth = calendar.get(Calendar.MONTH);

        calendar.set(calendar.get(Calendar.YEAR), currMonth, 1);
        
        return calendar.getTimeInMillis();
    }

    public static Long getCurrMonthStart(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currMonth = calendar.get(Calendar.MONTH);

        calendar.set(calendar.get(Calendar.YEAR), currMonth - 1, 1);
        
        return calendar.getTimeInMillis();
    }

    public static Long getCurrYearStart(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int currYear = calendar.get(Calendar.YEAR);

        calendar.set(currYear - 1900, 1, 1);
        
        return calendar.getTimeInMillis();
    }

    //
    // Alert entity operations
    //
    public Alert createAlert(Alert alert){
        return alertRepository.save(alert);
    }
    public List<Alert> getAllAlerts(){
        return alertRepository.findAll();
    }
}
