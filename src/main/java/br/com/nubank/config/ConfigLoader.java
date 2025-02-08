package br.com.nubank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Classe responsável por carregar as configurações do sistema
 */
public final class ConfigLoader {

    private static final String FILE_NAME = "config.properties";

    private static ConfigLoader instance;

    private final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    private Properties properties;

    private ConfigLoader() {
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
            instance.load();
        }
        return instance;
    }

    private void load() {
        if(properties ==  null) {
            properties = new Properties();
            try {
                properties.load(ConfigLoader.class.getClassLoader().getResourceAsStream(FILE_NAME));
            } catch (IOException e) {
                logger.error("Failed to load file ["+ FILE_NAME +"]", e);
                throw new RuntimeException(e);
            }
        }
    }

    public Optional<String> getConfigAsString(String configName) {
        return Optional.ofNullable(properties.getProperty(configName));
    }

    public Optional<Integer> getConfigAsInteger(ConfigNames name) {
        return getConfigAsInteger(name.toString());
    }

    public Optional<Integer> getConfigAsInteger(String configName) {
        Optional<String> value = Optional.ofNullable(properties.getProperty(configName));
        if (value.isPresent() && !value.get().isBlank())
            return Optional.of(Integer.valueOf(value.get()));
        return Optional.empty();
    }

    public Optional<Double> getConfigAsDouble(ConfigNames name) {
        return getConfigAsDouble(name.toString());
    }

    public Optional<Double> getConfigAsDouble(String configName) {
        Optional<String> value = Optional.ofNullable(properties.getProperty(configName));
        if (value.isPresent() && !value.get().isBlank())
            return Optional.of(Double.valueOf(value.get()));
        return Optional.empty();
    }

}
