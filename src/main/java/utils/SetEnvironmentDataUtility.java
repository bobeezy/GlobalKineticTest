package main.java.utils;

/**
 * @author lionel.mangoua
 * date: 09/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.config.GlobalEnums;

public class SetEnvironmentDataUtility extends DriverFactory {

    //region <setTestEnvironment>
    public void setTestEnvironment(String environmentName) {

        switch(environmentName) {
            case "dogs":
                env = GlobalEnums.Environment.DOGS;
                setUpBaseUrl();
                break;
            case "login":
                env = GlobalEnums.Environment.LOGIN;
                setUpBaseUrl();
                break;
        }

        log("Set Environment Name: '" + environmentName + "' successfully", "INFO", "text");
    }
    //endregion
}
