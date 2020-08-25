package com.horia.reminderapi.sms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileBasedConfigParser {

    private static final String PROP_FILE = "twilio.properties";

    public Properties loadPropertiesFromResourceFile() throws IOException {
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE);

        if (inputStream != null) {
            properties.load(inputStream);
        }else {
            throw new FileNotFoundException("property file '" + PROP_FILE + "' not found in the classpath");
        }

        return properties;
    }
}
