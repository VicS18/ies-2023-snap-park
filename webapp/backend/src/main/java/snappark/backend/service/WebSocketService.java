package snappark.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import snappark.backend.entity.Alert;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Alert alert) {
        try{
            messagingTemplate.convertAndSend("/alerts/1", new ObjectMapper().writeValueAsString(alert));
        }
        catch(Exception e){
            System.err.println(e);
        }
    }
}
