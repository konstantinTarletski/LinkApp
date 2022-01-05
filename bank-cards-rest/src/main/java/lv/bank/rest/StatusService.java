package lv.bank.rest;

import javax.ejb.Stateless;
import java.io.*;
import java.util.Properties;

@Stateless
public class StatusService {

    public static final String VERSION_FILE = "version.properties";
    private static String applicationVersion = "application version";

    static {
        Properties version = new Properties();
        try {
            InputStream inputStream = StatusService.class.getClassLoader().getResourceAsStream(VERSION_FILE);
            version.load(inputStream);
            applicationVersion = version.getProperty("app.version");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApplicationVersion(){
        return applicationVersion;
    }
}
