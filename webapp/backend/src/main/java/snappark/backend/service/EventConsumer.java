
package snappark.backend.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Alert;
import snappark.backend.entity.Light;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.OccupancyHistory;
import snappark.backend.entity.Temperature;

@Service
public class EventConsumer {
    @Autowired
    WebSocketService socket;

    @Autowired
    ParkService park;

    @RabbitListener(queues = "snap_park")
    public void handleMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);
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

    private void trafficEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());
        Occupancy o=park.getOccupancyByParkId(parkID).get();
        //check if parks exists
        OccupancyHistory occupancyHistory = new OccupancyHistory();
        OccupancyHistory.OccupancyHistoryId occupancyHistoryId =  occupancyHistory.new OccupancyHistoryId();
        occupancyHistoryId.setPark(park.getParkById(parkID));
        occupancyHistory.setId(occupancyHistoryId);
        occupancyHistory.setUser(park.getUserById(Long.valueOf(json.get("vehicle").asText())));
        occupancyHistory.setType(json.get("entering").asBoolean());
        occupancyHistory.setDate(Long.valueOf(json.get("ts").asText()));
        if (json.get("entering").asBoolean()==true){
            o.setLotation(o.getLotation()+1);
            occupancyHistory.setLotation(o.getLotation()+1);
        }
        else{
            o.setLotation(o.getLotation()-1);
            occupancyHistory.setLotation(o.getLotation()-1);
        }
        park.updateOccupancy(o);
        park.createParkMovement(occupancyHistory);
        

    }
    

    private void lightEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());
        Long sensorID=Long.valueOf(json.get("sensor").asText());
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
        Long sensorID=Long.valueOf(json.get("sensor").asText());
        int temperature= (int)Math.floor(Double.parseDouble(json.get("temperature").asText()));
        if (temperature>35){
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
        Long sensorID=Long.valueOf(json.get("sensor").asText());
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
