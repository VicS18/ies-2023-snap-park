package snappark.backend.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {

    @Bean
    public Queue Snap_Park() {
        return new Queue("snap_park", true);
    }
}
