package snappark.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import snappark.backend.entity.Alert;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Alert alert) {
        messagingTemplate.convertAndSend("/alerts/1", alert.getText());
    }
}
