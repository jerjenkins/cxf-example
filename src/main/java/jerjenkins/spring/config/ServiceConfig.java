package jerjenkins.spring.config;

import jerjenkins.service.SimpleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FbbPropertyConfig.class})
public class ServiceConfig {
    private final SimpleService simpleService = new SimpleService();

    @Bean
    public SimpleService simpleService() {
        return simpleService;
    }
}
