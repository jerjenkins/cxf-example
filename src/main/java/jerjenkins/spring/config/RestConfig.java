package jerjenkins.spring.config;

import jerjenkins.web.resource.BarResource;
import jerjenkins.web.resource.FooResource;
import jerjenkins.web.resource.RootResource;
import jerjenkins.web.spring.WebProperties;
import jerjenkins.web.spring.WebPropertiesConfig;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@Import({ServiceConfig.class, WebPropertiesConfig.class})
public class RestConfig {
    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private WebProperties webProperties;

    @Bean(name="rootResource")
    public RootResource rootResource() {
        return new RootResource(fooResource(), barResource());
    }

    @Bean
    FooResource fooResource() {
        return new FooResource(serviceConfig.simpleService());
    }

    @Bean
    BarResource barResource() {
        return new BarResource(
                serviceConfig.simpleService(),
                webProperties.barIdRepetitions());
    }
}
