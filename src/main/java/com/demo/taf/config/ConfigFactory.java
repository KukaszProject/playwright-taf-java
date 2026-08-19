package com.demo.taf.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigFactory {

    private ConfigFactory() {
        // Private constructor to prevent instantiation
    }

    public static EnvironmentConfig getConfig() {
        String env = System.getProperty("env", "qa").toLowerCase();
        System.setProperty("env", env);
        return ConfigCache.getOrCreate(EnvironmentConfig.class);
    }
}
