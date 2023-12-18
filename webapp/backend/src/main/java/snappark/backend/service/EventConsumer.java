
package snappark.backend.service;

import java.util.HashSet;
import java.util.Optional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Alert;
import snappark.backend.entity.Light;
import snappark.backend.entity.Manager;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Park;
import snappark.backend.entity.Sensor;
import snappark.backend.entity.Temperature;
import snappark.backend.entity.User;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class EventConsumer {
    @Autowired(required = true)
    WebSocketService socket;

    @Autowired(required = true)
    ParkService park;

    @RabbitListener(queues = "snap_park")
    public void handleMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            /*
             * DEBUG: CREATE DEFAULT USER JOHN
             * TODO: REMOVE
             */

            System.out.println("===== TEST =====");

            park.createUser(debugUser);

            /*
             * DEBUG OVER
             */

            switch (jsonNode.get("type").asText()) {
                case "TRF":
                    trafficEvent(jsonNode);
                    break;
                case "LGT":
                    lightEvent(jsonNode);
                    break;
                case "TMP":
                    temperatureEvent(jsonNode);
                    break;
                case "AQ":
                    airQualityEvent(jsonNode);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    // TODO: REMOVE
    // DEBUG: Assumes a default user 
    //

    private String debugUsername = "John";
    private Long debugUserId = Long.valueOf(1);
    private User debugUser = new User(debugUserId, debugUsername, debugUsername, new HashSet<Manager>());

    private Park debugPark(Long id){
        // Creates a park, if it doesn't exist, with default values

        Park p = park.getParkById(id); // TODO: Should be done with "exists" for greater performance
        if(p == null)
        {
            p = new Park(id, "PARK_" + id, "ADDR_" + id, 10, 10, 10, 100, new HashSet<Manager>());
            // In Debug Mode every park is associated to John
            park.createPark(p, "John");
        }
        return p;
    }

    //
    // TODO: STOP IGNORING VEHICLE IDs
    // 

    private void debugUser(Long id){
        // Creates a user, if it doesn't exist, with default values

        User usr = park.getUserById(id); // TODO: Should be using "exists" 
        if(usr == null)
        {
            usr = new User(id, "USR_" + id, "USR_" + id, new HashSet<Manager>());
            park.createUser(usr);
        }
    }

    private Sensor debugSensor(Long id, String type, Park p){
        // Creates a sensor, if it doesn't exist, ASSUMING its respective park already exists
        
        Sensor sensor = park.getSensorById(id);
        if(sensor == null)
        {
            sensor = new Sensor(id, type, "ADDR", p);
            sensor = park.createSensor(sensor);
        }
        return sensor;
    }

    private void trafficEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());

        System.out.println("==== TRAFFIC EVENT DEBUGGING ====");

        // DEBUG
        Park p = debugPark(parkID);

        System.out.println("==== TE_DEBUG_PARK: " + p);
        System.out.println("==== TE_DEBUG_GET_ID: " + p.getId());
        parkID = p.getId();
        System.out.println("==== TE_DEBUG_PARK_ID: " + parkID);

        // END DEBUG

        // TODO: CHECK IF PARK EXISTS

        Optional<Occupancy> o=park.getOccupancyByParkId(parkID);
        System.out.println("==== TE_DEBUG_OPT_OCCUP: " + o);

        //check if occupancy row for park exists
        if (o.isPresent()) // Update
        {
            Occupancy occup = o.get();
            System.out.println("==== TE_DEBUG_OCCUP - LOTATION: " + occup.getLotation() + "; PARK: " + occup.getPark() + "; PARK_ID: " + occup.getParkId());
            if (json.get("entering").asText().equals("True")){
                occup.setLotation(occup.getLotation()+1);
            }
            else if (json.get("entering").asText().equals("False")){
                if(occup.getLotation() > 0)
                    occup.setLotation(occup.getLotation() - 1);
            }
            park.updateOccupancy(occup);
        }
        else // Create occupancy
        {
            Occupancy occup = new Occupancy(parkID, p, 0);
            if (json.get("entering").asText().equals("True")){
                occup.setLotation(1);
            }
            System.out.println("==== TE_DEBUG_OCCUP - LOTATION: " + occup.getLotation() + "; PARK: " + occup.getPark() + "; PARK_ID: " + occup.getParkId());
            park.createOccupancy(occup);
        }
    }
    

    private void lightEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());

        // Verify park existence

        //
        // DEBUG: INSERT PARK AND SENSOR IF THEY DON'T EXIST 
        // TODO: REMOVE THIS
        //

        // !!!!!!! WARNING !!!!!!!! 
        // IDs CAN'T BE MANUALLY SET. THE RETURNED ID FROM THE DATABASE IS ALWAYS GENERATED AND MOST DEFINITELY DIFFERENT

        Park p = debugPark(parkID);

        parkID = p.getId();

        Long sensorID=Long.valueOf(json.get("sensor").asText());

        Sensor sens = debugSensor(sensorID, "LGT", p);

        sensorID = sens.getId();

        int intensity= (int)Math.floor(Double.parseDouble(json.get("intensity").asText()));

        if (intensity>1500){
            Alert newAlert=new Alert();
            newAlert.setText("High luminescence! Currently at "+intensity+"! Top limit is 1500 lux.");
            newAlert.setDate(System.currentTimeMillis());
            //newAlert.setPark(park.getParkById(parkID));
            park.createAlert(newAlert);
            socket.sendNotification(newAlert);
        }
        else if (intensity<40){
            Alert newAlert=new Alert();
            newAlert.setText("Low luminescence! Currently at "+intensity+"! Bottom limit is 40 lux.");
            newAlert.setDate(System.currentTimeMillis());
            //newAlert.setPark(park.getParkById(parkID));
            park.createAlert(newAlert);
            socket.sendNotification(newAlert);

        }

        Light newLight= new Light(Light.createLightId(park.getParkById(parkID), park.getSensorById(sensorID)), intensity); 
        park.updateLight(newLight);
    }


    private void temperatureEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());

        //
        // DEBUG: INSERT IF PARK DOESN'T EXIST 
        // TODO: REMOVE
        //
        Park p = debugPark(parkID);
        parkID = p.getId();

        Long sensorID=Long.valueOf(json.get("sensor").asText());

        Sensor sens = debugSensor(sensorID, "TMP", p);
        sensorID = sens.getId();
        
        int temperature= (int)Math.floor(Double.parseDouble(json.get("temperature").asText()));
        if (temperature>1){
            Alert newAlert=new Alert();
            newAlert.setText("High temperature! Currently at "+temperature+"! Top limit is 35ºC.");
            newAlert.setDate(System.currentTimeMillis());
            //newAlert.setPark(park.getParkById(parkID));
            park.createAlert(newAlert);
            socket.sendNotification(newAlert);
        }
        else if (temperature<5){
            Alert newAlert=new Alert();
            newAlert.setText("Low temperature! Currently at "+temperature+"! Bottom limit is 5ºC.");
            newAlert.setDate(System.currentTimeMillis());
            //newAlert.setPark(park.getParkById(parkID));
            park.createAlert(newAlert);
            socket.sendNotification(newAlert);
        }
        Temperature newTemperature= new Temperature(Temperature.createTemperatureId(park.getParkById(parkID), park.getSensorById(sensorID)),temperature); 
        park.updateTemperature(newTemperature);

    }


    private void airQualityEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());

        //
        // DEBUG: INSERT IF PARK DOESN'T EXIST 
        // TODO: REMOVE
        //
        Park p = debugPark(parkID);
        parkID = p.getId();

        Long sensorID=Long.valueOf(json.get("sensor").asText());

        Sensor sens = debugSensor(sensorID, "AQ", p);
        sensorID = sens.getId();
        
        int aq= Integer.parseInt(json.get("aqi").asText());
        if (aq >100){
            Alert newAlert=new Alert();
            newAlert.setText("Bad air quality! Currently at "+ aq + "! Top limit is 100.");
            newAlert.setDate(System.currentTimeMillis());
            //newAlert.setPark(park.getParkById(parkID));
            park.createAlert(newAlert);
            socket.sendNotification(newAlert);
        }

        AirQuality newAirQuality= new AirQuality(AirQuality.createAirQualityId(park.getParkById(parkID), park.getSensorById(sensorID)),aq); 
        park.updateAirQuality(newAirQuality);
    }
}
