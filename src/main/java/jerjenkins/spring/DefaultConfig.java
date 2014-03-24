package jerjenkins.spring;

import java.util.Properties;

public enum DefaultConfig {
    FOO_PROPERTY(
            ConfigurationKeys.FOO_PROPERTY, "Foo"),
    BAR_ID_REPETITIONS(
            ConfigurationKeys.BAR_ID_REPETITIONS, "2");

    private final String key;
    private final String defaultValue;

    DefaultConfig(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String key() {
        return key;
    }

    public String defaultValue() {
        return defaultValue;
    }

    public static Properties defaultProperties() {
        Properties properties = new Properties();
        for (DefaultConfig config : values()) {
            properties.setProperty(config.key(), config.defaultValue());
        }
        return properties;
    }

}
