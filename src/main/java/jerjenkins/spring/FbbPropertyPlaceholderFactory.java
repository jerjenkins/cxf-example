package jerjenkins.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.util.Properties;

public final class FbbPropertyPlaceholderFactory {

    public static final String DEPLOYMENT_PROPERTIES_LOCATION_DEFAULT =
            "classpath:app.properties";
    public static final String DEPLOYMENT_PROPERTIES_LOCATION_OVERRIDE_SYSTEM_KEY_DEFAULT =
            "app.config";

    public static final class DefaultedFactoryBuilder {

        private String _configDeploymentLocation =
                DEPLOYMENT_PROPERTIES_LOCATION_DEFAULT;
        private String _configDeploymentLocationOverrideSystemKey =
                DEPLOYMENT_PROPERTIES_LOCATION_OVERRIDE_SYSTEM_KEY_DEFAULT;
        private final Properties _overrides = new Properties();
        private Properties _defaults = DefaultConfig.defaultProperties();

        public DefaultedFactoryBuilder withConfigDefaultsLocation(Properties defaults) {
            _defaults = defaults;
            return this;
        }

        public DefaultedFactoryBuilder withConfigDeploymentLocation(String location) {
            _configDeploymentLocation = location;
            return this;
        }

        public DefaultedFactoryBuilder withConfigDeploymentLocationOverrideSystemKey(String systemKey) {
            _configDeploymentLocationOverrideSystemKey = systemKey;
            return this;
        }

        public DefaultedFactoryBuilder withOverrides(Properties overrides) {
            for (String name : overrides.stringPropertyNames()) {
                _overrides.setProperty(name, overrides.getProperty(name));
            }
            return this;
        }

        public DefaultedFactoryBuilder withOverride(String key, String value) {
            _overrides.setProperty(key, value);
            return this;
        }

        public FbbPropertyPlaceholderFactory build() {
            return new FbbPropertyPlaceholderFactory(
                    _defaults,
                    _configDeploymentLocation,
                    _configDeploymentLocationOverrideSystemKey,
                    _overrides);
        }

        public AnnotationConfigApplicationContext buildInto(AnnotationConfigApplicationContext context) {
            FbbPropertyPlaceholderFactory factory = build();
            context.addBeanFactoryPostProcessor(factory.overridesPropertiesConfigurer());
            context.addBeanFactoryPostProcessor(factory.deploymentPropertiesConfigurer());
            context.addBeanFactoryPostProcessor(factory.defaultPropertiesConfigurer());
            return context;
        }

    }

    public static DefaultedFactoryBuilder defaultedBuilder() {
        return new DefaultedFactoryBuilder();
    }

    private final Properties configDefaults;
    private final String configDeploymentLocation;
    private final String configDeploymentLocationOverrideSystemKey;
    private final Properties overrides;

    private FbbPropertyPlaceholderFactory(
            Properties configDefaults,
            String configDeploymentLocation,
            String configDeploymentLocationOverrideSystemKey,
            Properties overrides) {
        this.configDefaults = configDefaults;
        this.configDeploymentLocation = configDeploymentLocation;
        this.configDeploymentLocationOverrideSystemKey = configDeploymentLocationOverrideSystemKey;
        this.overrides = overrides;
    }

    public PropertyPlaceholderConfigurer defaultPropertiesConfigurer() {

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setProperties(configDefaults);
        configurer.setOrder(Ordered.LOWEST_PRECEDENCE - 1);

        // Ideally, we'd have this set to "false" to catch parameters not
        // resolved, but DSC has other configurers which by default
        // are /also/ set to the lowest precedence (because that's what Spring
        // has made the default in their no-arg constructor).  Therefore, even
        // though we're at the lowest precedence, we can't assure that there
        // isn't a later configurer of the same precedence that has yet to
        // process a value.
        //
        // Note, Spring could implement better logic to handle checks like this
        // for multiple configurers of the same precedence.  Someone should
        // file a bug report.
        //
        configurer.setIgnoreUnresolvablePlaceholders(true);

        return configurer;

    }

    public PropertyPlaceholderConfigurer deploymentPropertiesConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader(getClass().getClassLoader());
        String location = resolvedConfigLocation();
        Resource resource = resourceLoader.getResource(location);
        configurer.setLocation(resource);
        configurer.setOrder(0);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setIgnoreResourceNotFound(true);
        return configurer;
    }

    public PropertyPlaceholderConfigurer overridesPropertiesConfigurer() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setProperties(overrides);
        configurer.setOrder(Ordered.HIGHEST_PRECEDENCE);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    private String resolvedConfigLocation() {
        String location = System.getProperty(configDeploymentLocationOverrideSystemKey);
        return location == null ? configDeploymentLocation : location;
    }

}

