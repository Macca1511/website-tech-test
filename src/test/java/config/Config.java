package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static String getBrowser() {
        String browser = System.getProperty("browser");

        if (browser != null && !browser.isEmpty()) {
            return browser;
        }

        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            props.load(fis);
            return props.getProperty("browser", "chrome"); // default is chrome
        } catch (IOException e) {
            e.printStackTrace();
            return "chrome";
        }
    }
}


