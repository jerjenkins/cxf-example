package jerjenkins.web.spring;

import jerjenkins.spring.config.FbbPropertyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FbbPropertyConfig.class)
public class WebPropertiesConfig {

    @Bean
    WebProperties webProperties() {
        return new WebProperties();
    }
}
