package jerjenkins.web.spring;

import jerjenkins.spring.ConfigurationKeys;
import org.springframework.beans.factory.annotation.Value;

public class WebProperties {
    @Value("${" + ConfigurationKeys.BAR_ID_REPETITIONS + "}")
    private int barIdRepetitions;

    public int barIdRepetitions() { return barIdRepetitions; }
}
