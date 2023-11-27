
package snappark.backend.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventConsumer {
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
    private void lightEvent(JsonNode json){
        
    }
}
