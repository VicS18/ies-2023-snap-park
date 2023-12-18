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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class SPRestController {

    private ParkService parkService;

    //    
    // Park operations
    //
    
    @GetMapping("/parks/manager/{username}")
    public ResponseEntity<List<Park>> getParksByUser(@PathVariable String username, @RequestParam(required = false) Long id, @RequestParam(required = false) String name){
        // TODO: Handle case where username isn't provided or doesn't exist
        System.out.println("==== GET /parks/manager/{username}");
        List<Park> retPark = parkService.getParksByUsername(username);

        System.out.println("==== GM_USERNAME: " + username);
        System.out.println("==== GM_RETPARK: " + retPark);

        if(id != null); // TODO: FILTER BY ID 
        if(name != null); // TODO: FILTER BY NAME

        return new ResponseEntity<List<Park>>(retPark, HttpStatus.OK);
    } 

    @PostMapping("/parks/manager/{username}")
    public ResponseEntity<Park> postPark(@RequestBody Park park, @PathVariable String username){
        System.out.println("==== POST /parks/manager/{username}");
        System.out.println("==== PM_PARK: " + park);
        System.out.println("==== PM_USERNAME: " + username);
        Park savedPark = parkService.createPark(park, username);
        System.out.println("==== PM_SAVED_PARK: " + savedPark);
        // TODO: Handle case where username doesn't correspond to existing User (in Managers)

        return new ResponseEntity<Park>(savedPark, HttpStatus.CREATED);
    }

    @GetMapping("/parks/{parkId}")
    public ResponseEntity<Park> getPark(@PathVariable Long parkId){
        Park park = parkService.getParkById(parkId);
        return new ResponseEntity<Park>(park, HttpStatus.OK);
    }

    @PutMapping("/parks/{parkId}")
    public ResponseEntity<Park> putPark(@PathVariable Long parkId){
        // TODO: Handle Park not found
        Park updatedPark = parkService.updatePark(parkId);
        return new ResponseEntity<Park>(updatedPark, HttpStatus.OK);
    }

    @DeleteMapping("/parks/{parkId}")
    public HttpStatus deletePark(@PathVariable Long parkId){
        // TODO: Handle Park not found
        parkService.deletePark(parkId);
        return HttpStatus.OK;
    }

    //
    // Sensor operations
    //

    @PostMapping("/parks/{parkId}/sensors")
    public ResponseEntity<Sensor> postSensor(@RequestBody Sensor sensor, @PathVariable Long parkId){
        // TODO: Handle Park not found
        Sensor savedSensor = parkService.createSensor(sensor, parkId);
        return new ResponseEntity<Sensor>(savedSensor, HttpStatus.OK);
    }

    @GetMapping("/parks/{parkId}/sensors")
    public ResponseEntity<List<Sensor>> getMethodName(@PathVariable Long parkId) {
        // TODO: Handle Park not found
        List<Sensor> parkSensors = parkService.getSensorsByParkId(parkId);
        return new ResponseEntity<List<Sensor>>(parkSensors, HttpStatus.OK);
    }
    
    // 
    // Park-Event operations
    //

    @GetMapping("/parks/{parkId}/movements")
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

    @GetMapping("/parks/{parkId}/occupancies/{startDate}/{finishDate}/{numPoints}")
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

    @GetMapping("/parks/{parkId}/airqualities/{startDate}/{finishDate}/{numPoints}")
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

    @GetMapping("/parks/{parkId}/avgLight")
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

    @GetMapping("/parks/{parkId}/sensorCount")
    public ResponseEntity<Map<String, Integer>> getSensorCount(@PathVariable Long parkId) {
        Integer sensorCt = parkService.getSensorCount(parkId);
        System.out.println("SENSOR COUNT: " + sensorCt);

        // We're meant to just return a double value, but javascript's fetch API doesn't like non-JSON response bodies
        Map<String, Integer> sensorCount = new HashMap<String, Integer>();
        sensorCount.put("sensorCount", sensorCt);

        return ResponseEntity.ok(sensorCount);
    }

    @GetMapping("/parks/{parkId}/revenue/annual")
    public ResponseEntity<Map<String, Double>> getAnnualRevenue(@PathVariable Long parkId) {

        Double anRev = parkService.getAnnualRevenue(parkId);
        if(anRev == null)
            anRev = Double.valueOf(0);
        System.out.println("ANNUAL REVENUE: " + anRev);

        Map<String, Double> annualRevenue = new HashMap<String, Double>();
        annualRevenue.put("annualRevenue", anRev);

        return ResponseEntity.ok(annualRevenue);
    }

    @GetMapping("/parks/{parkId}/revenue/monthly")
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

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User user){
        User savedUser = parkService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
}