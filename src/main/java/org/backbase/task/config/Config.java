package org.backbase.task.config;

import com.codeborne.selenide.SelenideConfig;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.Properties;

public class Config extends SelenideConfig {

    private static Config config;
    private Properties properties;

    private Config() {
        loadProperties();
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    @SneakyThrows
    private void loadProperties() {
        var configFileName = "config.properties";
        try (var is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName)) {
            properties = new Properties();
            properties.load(is);

            // override values with system properties, if any are passed
            for (var propName : properties.stringPropertyNames()) {
                var systemPropValue = System.getProperty(propName);
                if (Objects.nonNull(systemPropValue)) {
                    properties.setProperty(propName, systemPropValue);
                }
            }
        }
        setSelenideProperties();
    }

    private void setSelenideProperties() {
        timeout(Long.parseLong(properties.getProperty("selenide.timeout")));
    }

    public String getApiBaseUrl() {
        return properties.getProperty("api.baseUrl");
    }
}
