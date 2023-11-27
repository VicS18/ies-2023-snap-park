package snappark.backend;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Park;
import snappark.backend.entity.User;
import snappark.backend.service.ParkService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0")
public class SPRestController {

    private ParkService parkService;

    //    
    // Park operations
    //

    @GetMapping("/park")
    public ResponseEntity<Park> getPark(@RequestParam(required = false) Long id, @RequestParam(required = false) String name) {
        Park retPark = null;
        if(id != null)
            retPark = parkService.getParkById(id);
        if(name != null)
            retPark = parkService.getParkByName(name);
        if (retPark == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        return new ResponseEntity<Park>(retPark, HttpStatus.CREATED);
    }

    @GetMapping("/parks/{userId}")
    public ResponseEntity<List<Park>> getParksByUser(@PathVariable Long userId, @RequestParam(required = false) Long id, @RequestParam(required = false) String name){
        // TODO: Handle case where userId isn't provided
        List<Park> retPark = parkService.getParksByUserId(id);

        if(id != null); // TODO: FILTER BY ID 
        if(name != null); // TODO: FILTER BY NAME

        return new ResponseEntity<List<Park>>(retPark, HttpStatus.CREATED);
    } 

    @PostMapping("/park/{userId}")
    public ResponseEntity<Park> postPark(@RequestBody Park park, @PathVariable Long userId){
        Park savedPark = parkService.createPark(park, userId);
        // TODO: Handle case where userId doesn't correspond to existing User (in Managers)

        return new ResponseEntity<Park>(savedPark, HttpStatus.CREATED);
    }

    //
    // User operations
    //

    @PostMapping("/user")
    public ResponseEntity<User> postUser(@RequestBody User user){
        User savedUser = parkService.createUser(user);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }
}