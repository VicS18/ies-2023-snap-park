
package snappark.backend.service;

import java.util.Optional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import snappark.backend.entity.AirQuality;
import snappark.backend.entity.Light;
import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Temperature;

@Service
public class EventConsumer {
    ParkService park;
    @RabbitListener(queues = "snap_park")
    public void handleMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);
            switch (jsonNode.get("type").toString()) {
                case "TRF":
                    trafficEvent(jsonNode);
                case "LGT":
                    lightEvent(jsonNode);
                case "TMP":
                    temperatureEvent(jsonNode);
                case "AQ":
                    airQualityEvent(jsonNode);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Transactional
    private void trafficEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").toString());
        Optional<Occupancy> o=park.getOccupancyByParkId(parkID);
        //check if parks exists
        if (o.isPresent())
        {
            if (json.get("entering").asBoolean())
                o.get().setLotation(o.get().getLotation()+1);
        }

    }
    
    @Transactional
    private void lightEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").toString());
        Long sensorID=Long.valueOf(json.get("sensor").toString());
        Light newLight= new Light(Light.createLightId(park.getParkById(parkID), park.getSensorById(sensorID)), Double.parseDouble(json.get("intensity").toString())); 
        park.updateLight(newLight);
    }

    @Transactional
    private void temperatureEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").toString());
        Long sensorID=Long.valueOf(json.get("sensor").toString());
        Temperature newTemperature= new Temperature(Temperature.createTemperatureId(park.getParkById(parkID), park.getSensorById(sensorID)), Integer.parseInt(json.get("temperature").toString())); 
        park.updateTemperature(newTemperature);
    }

    @Transactional
    private void airQualityEvent(JsonNode json){
        Long parkID=Long.valueOf(json.get("park").toString());
        Long sensorID=Long.valueOf(json.get("sensor").toString());
        AirQuality newAirQuality= new AirQuality(AirQuality.createAirQualityId(park.getParkById(parkID), park.getSensorById(sensorID)), Integer.parseInt(json.get("aqi").toString())); 
        park.updateAirQuality(newAirQuality);
    }
}
