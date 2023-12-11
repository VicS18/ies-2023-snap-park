package snappark.backend;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;
import snappark.backend.service.ParkService;

import java.util.List;

import javax.swing.RepaintManager;

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
        List<Park> retPark = parkService.getParksByUsername(username);

        if(id != null); // TODO: FILTER BY ID 
        if(name != null); // TODO: FILTER BY NAME

        return new ResponseEntity<List<Park>>(retPark, HttpStatus.OK);
    } 

    @PostMapping("/parks/manager/{username}")
    public ResponseEntity<Park> postPark(@RequestBody Park park, @PathVariable String username){
        Park savedPark = parkService.createPark(park, username);
        // TODO: Handle case where username doesn't correspond to existing User (in Managers)

        return new ResponseEntity<Park>(savedPark, HttpStatus.CREATED);
    }

    @GetMapping("/parks/{parkId}")
    public ResponseEntity<Park> getPark(@PathVariable Long id){
        Park park = parkService.getParkById(id);
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
    // Park-Event operations
    //

    @GetMapping("/parks/{parkId}/movements")
    public ResponseEntity<List<OccupancyHistory>> getMovements(@PathVariable Long parkId){
        // TODO: Handle Park not found

        List<OccupancyHistory> movements = parkService.getParkMovements(parkId);
        return new ResponseEntity<List<OccupancyHistory>>(movements, HttpStatus.OK);
    }

    @GetMapping("/parks/{parkId}/occupancies")
    public ResponseEntity<List<OccupancyHistory>> getOccupancies(@PathVariable Long parkId){
        // TODO: all
        List<OccupancyHistory> movements = parkService.getParkMovements(parkId);
        return new ResponseEntity<List<OccupancyHistory>>(movements, HttpStatus.OK);
    }

    // TODO: Consider using an @Entity for the return values of these two methods

    @GetMapping("/parks/{parkId}/avgLight")
    public ResponseEntity<Double> getAvgLight(@PathVariable Long parkId) {
        // TODO: Handle park not found

        Double averageLightLevel = parkService.getAvgLightLevel(parkId);
        if (averageLightLevel == null)
            averageLightLevel = Double.valueOf(0);

        return new ResponseEntity<Double>(averageLightLevel, HttpStatus.OK);
    }

    @GetMapping("/parks/{parkId}/sensorCount")
    public ResponseEntity<Integer> getSensorCount(@PathVariable Long parkId) {
        Integer sensorCount = parkService.getSensorCount(parkId);
        return ResponseEntity.ok(sensorCount);
    }

    @GetMapping("/parks/{parkId}/revenue/annual")
    public ResponseEntity<Double> getAnnualRevenue(@PathVariable Long parkId) {
        Double annualRevenue = parkService.getAnnualRevenue(parkId);
        return ResponseEntity.ok(annualRevenue);
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