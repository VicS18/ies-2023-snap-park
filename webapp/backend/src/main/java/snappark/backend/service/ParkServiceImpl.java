package snappark.backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<Park> foundPark = parkRepository.findById(id);
        return foundPark.isPresent() ? foundPark.get() : null;
    }
    
    public Park getParkByName(String name){
        Optional<Park> foundPark = parkRepository.findParkByName(name);
        return foundPark.isPresent() ? foundPark.get() : null;    
    }

    public Park createPark(Park park, String username){        
        // TODO: Deal with situation where provided userId doesn't have a corresponding User
        
        Park savedPark = parkRepository.save(park);

        System.out.println("C_DEBUG_SAVED_PARK: " + savedPark);

        User user = userRepository.findUserByName(username);

        System.out.println("C_DEBUG_USER: " + user);

        Manager manager = new Manager(user, savedPark);

        System.out.println("C_DEBUG_MANAGER: " + manager.getPark() + "; " + manager.getUser());

        Manager savedManager = managerRepository.save(manager);

        System.out.println("C_DEBUG_SAVED_MANAGER: " + manager.getPark() + "; " + manager.getUser());

        System.out.println("C_DEBUG_PARKS: " + managerRepository.findByUserName(username));

        for(Manager m : managerRepository.findByUserName(username))
        {
            System.out.println("CDM_PARK: " + m.getPark() + "; USER: " + m.getUser());
        }

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
        System.out.println("==== D_PM_MANAGERS: " + managerRepository.findByUserName(username));

        for(Manager m : managers)
        {
            System.out.println("==== D_PM_PARK: " + m.getPark() + "; USER: " + m.getUser());
        }
        return managers.stream().map(Manager::getPark).collect(Collectors.toList());
    }

    public List<OccupancyHistory> getParkMovements(Long parkId){
        return occupancyHistoryRepository.findById_ParkId(parkId);
    }

    //
    // Sensor entity operations
    //


    public Sensor getSensorById(Long id){
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        return foundSensor.isPresent() ? foundSensor.get() : null;
    }

    // Prefer second method.

    public Sensor createSensor(Sensor sensor){
        return sensorRepository.save(sensor);
    }

    // TODO: Check if park exists

    public Sensor createSensor(Sensor sensor, Long parkId){
        sensor.setPark(getParkById(parkId));
        return sensorRepository.save(sensor);
    }

    //
    // User entity operations
    //

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    // TODO: ...Should just return an Optional object
    public User getUserById(Long id){
        Optional<User> foundUsr = userRepository.findUserById(id);
        return foundUsr.isPresent() ? foundUsr.get() : null;
    }

    //
    // Occupancy entity operations
    //
    public Occupancy createOccupancy(Occupancy occupancy){
        return occupancyRepository.save(occupancy);
    }
    
    public Optional<Occupancy> getOccupancyByParkId(Long id){
        return occupancyRepository.findByParkId(id);
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
