package snappark.backend;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
    
    @MessageMapping("/alarms")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
      return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
