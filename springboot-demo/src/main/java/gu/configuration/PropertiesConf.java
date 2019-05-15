package gu.configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesConf {

    private static String gpUrl;

    static {
        InputStreamReader reader = new InputStreamReader(PropertiesConf.class.getClassLoader().getResourceAsStream("application.properties"));
        Properties props = new Properties();
        try {
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gpUrl = props.getProperty("gp.url");
    }
}
