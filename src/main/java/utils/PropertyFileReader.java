package main.java.utils;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import main.java.Engine.DriverFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader extends DriverFactory {

    //Default constructor
    public PropertyFileReader() {
    }

    //region <To read properties files>
    public String returnPropVal_api(String fileName, final String key) {

        //get a new properties object:
        final Properties properties = new Properties();
        String value = null;

        try {
            properties.load(new FileInputStream("src/main/resources/api/" + fileName + ".properties"));
            //get property value based on key:
            value = properties.getProperty(key);
        }
        catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return value;
    }

    public String returnPropVal_mobi(String fileName, final String key) {

        //get a new properties object:
        final Properties properties = new Properties();
        String value = null;

        try {
            properties.load(new FileInputStream("src/main/resources/mobile/" + fileName + ".properties"));
            //get property value based on key:
            value = properties.getProperty(key);
        }
        catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return value;
    }

    public String returnPropVal_web(String fileName, final String key) {

        //get a new properties object:
        final Properties properties = new Properties();
        String value = null;

        try {
            properties.load(new FileInputStream("src/main/resources/web/" + fileName + ".properties"));
            //get property value based on key:
            value = properties.getProperty(key);
        }
        catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return value;
    }
    //endregion
}
