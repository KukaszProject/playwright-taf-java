package com.demo.taf.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigFactory {

    private ConfigFactory() {}

    public static EnvironmentConfig getConfig() {
        String env = System.getProperty("env", "qa").toLowerCase();
        Properties props = new Properties();

        String resourcePath = "environments/" + env + ".properties";
        try (InputStream input = ConfigFactory.class.getClassLoader().getResourceAsStream(resourcePath)) {
            
            if (input == null) {
                throw new RuntimeException("\n\nCRITICAL ERROR: Unable to find '" + resourcePath + "' on the classpath.\n");
            }
    
            props.load(input);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + e.getMessage(), e);
        }

        if (System.getenv("API_TOKEN") != null) {
            props.setProperty("api.token", System.getenv("API_TOKEN"));
        }
        if (System.getenv("DB_PASSWORD") != null) {
            props.setProperty("db.password", System.getenv("DB_PASSWORD"));
        }

        return org.aeonbits.owner.ConfigFactory.create(
                EnvironmentConfig.class, 
                props, 
                System.getProperties(), 
                System.getenv()
        );
    }
}