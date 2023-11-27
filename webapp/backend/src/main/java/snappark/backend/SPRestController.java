package snappark.backend;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import snappark.backend.entity.Park;
import snappark.backend.service.ParkService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Park> getPark(@RequestParam Long id, @RequestParam String name) {
        Park retPark = null;
        if(id != null)
            retPark = parkService.getParkById(id);
        if(name != null)
            retPark = parkService.getParkByName(name);
        if (retPark == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
        return new ResponseEntity<Park>(retPark, HttpStatus.CREATED);
    }

    @PostMapping("/park")
    public ResponseEntity<Park> postPark(@RequestBody Park park){
        Park savedPark = parkService.createPark(park);
        return new ResponseEntity<Park>(savedPark, HttpStatus.CREATED);   
    }


    @GetMapping("/greet/{name}")
    public String greetWithName(@RequestParam String name) {
        return "Hello, " + name + "!";
    }
}