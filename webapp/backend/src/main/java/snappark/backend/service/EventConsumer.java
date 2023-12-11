
package snappark.backend.service;

import java.util.Optional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Light;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Temperature;

@Service
public class EventConsumer {
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
        Optional<Occupancy> o=park.getOccupancyByParkId(parkID);
        //check if parks exists
        if (o.isPresent())
        {
            if (json.get("entering").asText().equals("True"))
                o.get().setLotation(o.get().getLotation()+1);
        }

    }
    

    private void lightEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());
        Long sensorID=Long.valueOf(json.get("sensor").asText());
        Light newLight= new Light(Light.createLightId(park.getParkById(parkID), park.getSensorById(sensorID)), Integer.parseInt(json.get("intensity").asText())); 
        park.updateLight(newLight);
    }


    private void temperatureEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());
        Long sensorID=Long.valueOf(json.get("sensor").asText());
        Temperature newTemperature= new Temperature(Temperature.createTemperatureId(park.getParkById(parkID), park.getSensorById(sensorID)), Integer.parseInt(json.get("temperature").asText())); 
        park.updateTemperature(newTemperature);
    }


    private void airQualityEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").asText());
        Long sensorID=Long.valueOf(json.get("sensor").asText());
        AirQuality newAirQuality= new AirQuality(AirQuality.createAirQualityId(park.getParkById(parkID), park.getSensorById(sensorID)), Integer.parseInt(json.get("aqi").asText())); 
        park.updateAirQuality(newAirQuality);
    }
}
