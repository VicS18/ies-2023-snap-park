package snappark.backend;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import snappark.backend.entity.AirQualityHistory;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.User;
import snappark.backend.service.ParkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class SPRestController {

    private ParkService parkService;

    //    
    // Park operations
    //
    
    @GetMapping("/parks/{userId}")
    public ResponseEntity<List<Park>> getParksByUser(@PathVariable Long userId){
        List<Park> retPark = parkService.getParksByUser(parkService.getUserById(userId).get());
        return new ResponseEntity<List<Park>>(retPark, HttpStatus.OK);
    } 

    @PostMapping("/parks/{userId}")
    public ResponseEntity<Park> postPark(@RequestBody Park park, @PathVariable Long userId){
        Optional<User> user= parkService.getUserById(userId);
        User nUser=new User();
        if(user.isEmpty()){
            nUser.setName("TestUser");
            nUser.setPassword("1234");
            parkService.createUser(nUser);
        }
        else
            nUser=user.get();
        Park savedPark = parkService.createPark(park,nUser);
    
        return new ResponseEntity<Park>(savedPark, HttpStatus.CREATED);
    }

    @GetMapping("/park/{parkId}")
    public ResponseEntity<Park> getPark(@PathVariable Long parkId){
        Park park = parkService.getParkById(parkId);
        return new ResponseEntity<Park>(park, HttpStatus.OK);
    }

    @PutMapping("/park/{parkId}")
    public ResponseEntity<Park> putPark(@PathVariable Long parkId){
        // TODO: Handle Park not found
        Park updatedPark = parkService.updatePark(parkId);
        return new ResponseEntity<Park>(updatedPark, HttpStatus.OK);
    }

    @DeleteMapping("/park/{parkId}")
    public HttpStatus deletePark(@PathVariable Long parkId){
        // TODO: Handle Park not found
        parkService.deletePark(parkId);
        return HttpStatus.OK;
    }

    // 
    // Park-Event operations
    //
    @GetMapping("/park/{parkId}/sensors")
    public ResponseEntity<List<Sensor>> getSensor(@PathVariable Long parkId){
        List<Sensor> sensors = parkService.getSensorsByPark(parkId);
        return new ResponseEntity<List<Sensor>>(sensors, HttpStatus.OK);
    }
    @PostMapping("/park/{parkId}/sensors")
    public ResponseEntity<Sensor> postSensor(@RequestBody Sensor sensor, @PathVariable Long parkId){
        Sensor savedSensor = parkService.createSensor(sensor, parkId);
        return new ResponseEntity<Sensor>(savedSensor, HttpStatus.OK);
    }

    @GetMapping("/park/{parkId}/movements")
    public ResponseEntity<List<OccupancyHistory>> getMovements(@PathVariable Long parkId){
        // TODO: Handle Park not found

        List<OccupancyHistory> movements = parkService.getParkMovements(parkId);
        return new ResponseEntity<List<OccupancyHistory>>(movements, HttpStatus.OK);
    }

    public class OccupancyRecord {
        private Long date;
        private int lotation;
    
        public OccupancyRecord(Long date, int lotation) {
            this.date = date;
            this.lotation = lotation;
        }
    
        public Long getDate() {
            return date;
        }
    
        public int getLotation() {
            return lotation;
        }

        public void setLotation(int lotation) {
            this.lotation=lotation;
        }
        //make sure this class is properly serialized for front-end... maybe not needed
        @Override
        public String toString() {
            return "OccupancyRecord{" +
                    "date=" + date +
                    ", lotation=" + lotation +
                    '}';
    }
    }

    @GetMapping("/park/{parkId}/occupancies/{startDate}/{finishDate}/{numPoints}")
    public ResponseEntity<List<OccupancyRecord>> getOccupancies(@PathVariable Long parkId, @PathVariable Long startDate, @PathVariable Long finishDate, @PathVariable int numPoints){
        //start and finish date must be in timestamp
        List<OccupancyHistory> movements = parkService.getParkMovementsByDate(parkId,startDate,finishDate);
        ArrayList<OccupancyRecord> points= new ArrayList<OccupancyRecord>();

        Long interval=(finishDate-startDate)/(numPoints-1); //Equal division of time to obtain numPoint-1 intervals
        Long ts=startDate; //current timestamp\\
        for (int i=1;i<movements.size();i++) {
            if (movements.get(i).getDate()>ts){
                OccupancyHistory previous=movements.get(i-1);
                OccupancyRecord newPoint= new OccupancyRecord(ts, previous.getLotation());
                points.add(newPoint);
                ts+=interval;
                if(points.size()==numPoints)
                    break;
                i-=1;
            }    
        }
        if (points.size()==0){
            Occupancy currentOccupancy=parkService.getOccupancyByParkId(parkId).get();
            for(int x=0;x<numPoints;x++){
                points.add(new OccupancyRecord(ts, currentOccupancy.getLotation()));
                ts+=interval;
            }
        }
        else if (points.size()<numPoints){
            OccupancyRecord lastPoint=points.get(points.size()-1);
            for(int x=0;x<(numPoints-points.size());x++){
                points.add(new OccupancyRecord(ts, lastPoint.getLotation()));
                ts+=interval;
            }
        }
        return new ResponseEntity<List<OccupancyRecord>>(points, HttpStatus.OK);
    }

    @GetMapping("/park/{parkId}/airqualities/{startDate}/{finishDate}/{numPoints}")
    public ResponseEntity<List<OccupancyRecord>> getAirQualities(@PathVariable Long parkId, @PathVariable Long startDate, @PathVariable Long finishDate, @PathVariable int numPoints){
        //start and finish date must be in timestamp
        ArrayList<OccupancyRecord> points= new ArrayList<OccupancyRecord>();
        List<Sensor> sensors=parkService.getSensorsByPark(parkId);
        Long interval=(finishDate-startDate)/(numPoints-1); //Equal division of time to obtain numPoint-1 intervals
        Long ts=startDate; //current timestamp\\
        ArrayList<Sensor> aqSensors=new ArrayList<Sensor>();
        for (Sensor s:sensors){
            if (s.getType().equals("AQ"))
                aqSensors.add(s);
        }
        for (Sensor s:aqSensors){
            List<AirQualityHistory> movements = parkService.getAirQualityByDate(parkId,s.getId(),startDate,finishDate);
            int counter = 0;
            for (int i=1;i<movements.size();i++) {
                if (movements.get(i).getDate()>ts){
                    AirQualityHistory previous=movements.get(i-1);
                    OccupancyRecord newPoint= new OccupancyRecord(ts, previous.getHumidity());
                    if (points.size()>counter){
                        newPoint.setLotation(newPoint.getLotation()+points.get(counter).getLotation());
                        points.set(counter, newPoint);
                    }
                    else
                        points.add(newPoint);
                    ts+=interval;
                    if(points.size()==numPoints)
                        break;
                    i--;
                    counter++;
                }    
            }
            if (points.size()==0){
                Occupancy currentOccupancy=parkService.getOccupancyByParkId(parkId).get();
                for(int x=0;x<numPoints;x++){
                    points.add(new OccupancyRecord(ts, currentOccupancy.getLotation()));
                    ts+=interval;
                }
            }
            else if (points.size()<numPoints){
                OccupancyRecord lastPoint=points.get(points.size()-1);
                for(int x=0;x<(numPoints-points.size());x++){
                    points.add(new OccupancyRecord(ts, lastPoint.getLotation()));
                    ts+=interval;
                }
            }
        }
        for (int i=0; i<points.size(); i++){ //do average of sensors
            OccupancyRecord record=points.get(i);
            record.setLotation(record.getLotation()/aqSensors.size());
            points.set(i, record);
        }
        return new ResponseEntity<List<OccupancyRecord>>(points, HttpStatus.OK);
    }
    // TODO: Consider using an @Entity for the return values of these two methods

    @GetMapping("/park/{parkId}/avgLight")
    public ResponseEntity<Map<String, Double>> getAvgLight(@PathVariable Long parkId) {
        // TODO: Handle park not found

        Double averageLightLevel = parkService.getAvgLightLevel(parkId);
        if (averageLightLevel == null)
            averageLightLevel = Double.valueOf(0);
        System.out.println("AVG LL: " + averageLightLevel);

        // We're meant to just return a double value, but javascript's fetch API doesn't like non-JSON response bodies
        Map<String, Double> avgLight = new HashMap<String, Double>();
        avgLight.put("avgLight", averageLightLevel);

        return new ResponseEntity<Map<String, Double>>(avgLight, HttpStatus.OK);
    }

    @GetMapping("/park/{parkId}/sensorCount")
    public ResponseEntity<Map<String, Integer>> getSensorCount(@PathVariable Long parkId) {
        Integer sensorCt = parkService.getSensorCount(parkId);
        System.out.println("SENSOR COUNT: " + sensorCt);

        // We're meant to just return a double value, but javascript's fetch API doesn't like non-JSON response bodies
        Map<String, Integer> sensorCount = new HashMap<String, Integer>();
        sensorCount.put("sensorCount", sensorCt);

        return ResponseEntity.ok(sensorCount);
    }

    @GetMapping("/park/{parkId}/revenue/annual")
    public ResponseEntity<Map<String, Double>> getAnnualRevenue(@PathVariable Long parkId) {

        Double anRev = parkService.getAnnualRevenue(parkId);
        if(anRev == null)
            anRev = Double.valueOf(0);
        System.out.println("ANNUAL REVENUE: " + anRev);

        Map<String, Double> annualRevenue = new HashMap<String, Double>();
        annualRevenue.put("annualRevenue", anRev);

        return ResponseEntity.ok(annualRevenue);
    }

    @GetMapping("/park/{parkId}/revenue/monthly")
    public ResponseEntity<Map<String, Double>> getMonthlyRevenue(@PathVariable Long parkId) {

        Double monRev = parkService.getMonthlyRevenue(parkId);
        if(monRev == null)
            monRev = Double.valueOf(0);
        System.out.println("MONTHLY REVENUE: " + monRev);

        Map<String, Double> monthlyRevenue = new HashMap<String, Double>();
        monthlyRevenue.put("monthlyRevenue", monRev);

        return ResponseEntity.ok(monthlyRevenue);
    }

    //
    // User operations
    //
    public class UserForm {
        private String name;
        private String password;
    
        public String getName(){
            return this.name;
        }
        public String getPassword(){
            return this.password;
        }
    }
    
    @PostMapping("/user")
    public ResponseEntity<User> postUser(@RequestBody UserForm user){
        User usr= new User();
        usr.setName(user.getName());
        usr.setPassword(user.getPassword());
        User savedUser = parkService.createUser(usr);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("/userAt")
    public ResponseEntity<User> postUserAt(@RequestBody long id, String password){
        Optional<User> savedUser = parkService.getUserById(id);
        if (savedUser.isPresent()){
            if (savedUser.get().getPassword().equals(password)){
                return new ResponseEntity<User>(savedUser.get(), HttpStatus.FOUND);
            }
            else {
                return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
}