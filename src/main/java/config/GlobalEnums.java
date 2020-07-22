package main.java.config;

/**
 * @author lionel.mangoua
 * date: 08/07/20
 */

import main.java.Engine.DriverFactory;

public class GlobalEnums extends DriverFactory {

    public static String httpsString = "https://";
    public static String dogsBaseUrl = httpsString + property.returnPropVal_api(api_fileName, "dogsBaseUrlPath");
    public static String dogsPath = property.returnPropVal_api(api_fileName, "dogsUrlPath");
    public static String loginBaseUrl = httpsString + property.returnPropVal_api(api_fileName, "loginBaseUrlPath");
    public static String loginPath = property.returnPropVal_api(api_fileName, "loginUrlPath");

    //region <API>
    public enum Environment {

        DOGS(dogsBaseUrl, dogsPath, "dogs"),
        LOGIN(loginBaseUrl, loginPath, "login");

        public final String baseUrl;
        public final String path;
        public final String environmentName;

        //Setters
        Environment(String baseUrl, String path, String environmentName) {

            this.baseUrl = baseUrl;
            this.path = path;
            this.environmentName = environmentName;
        }
    }
    //endregion

    //region <MOBILE>
    public enum Devices {

        NEXUS_6_EMULATOR("Nexus 6 API 27", "8.1", "emulator-5554");

        public final String deviceName;
        public final String platformVersion;
        public final String udid;

        Devices(String deviceName, String platformVersion, String udid) {
            this.deviceName = deviceName;
            this.platformVersion = platformVersion;
            this.udid = udid;
        }
    }
    //endregion
}
