
package snappark.backend.service;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import snappark.backend.entity.Occupancy;
import snappark.backend.entity.Park;
import snappark.backend.entity.Occupancy.OccupancyId;

@Service
public class EventConsumer {
    OccupancyService occupancy;
    TemperatureService temperature;
    LightService light;
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
    private void trafficEvent(JsonNode json){
        Park p= park.getParkById(Long.valueOf(json.get("park").toString()));
        Occupancy o=new Occupancy();
        OccupancyId oId=o.new OccupancyId(p);
        //TODO: finish        

    }
    private void lightEvent(JsonNode json){
        
    }
    private void temperatureEvent(JsonNode json){
        
    }
    private void airQualityEvent(JsonNode json){
        
    }
}
