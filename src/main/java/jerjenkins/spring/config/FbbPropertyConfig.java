package jerjenkins.spring.config;

import jerjenkins.spring.FbbPropertyPlaceholderFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FbbPropertyConfig {

    @Bean
    public PropertyPlaceholderConfigurer defaultPropertiesConfigurer() {
        return propertyPlaceholderFactory().defaultPropertiesConfigurer();
    }

    @Bean
    public PropertyPlaceholderConfigurer deploymentPropertiesConfigurer() {
        return propertyPlaceholderFactory().deploymentPropertiesConfigurer();
    }

    @Bean
    public FbbPropertyPlaceholderFactory propertyPlaceholderFactory() {
        return FbbPropertyPlaceholderFactory.defaultedBuilder().build();
    }

}

