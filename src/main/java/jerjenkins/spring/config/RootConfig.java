package jerjenkins.spring.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({RestConfig.class})
public class RootConfig {
}
