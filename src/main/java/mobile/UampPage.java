package main.java.mobile;

/**
 * @author lionel.mangoua
 * date: 08/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.utils.AppiumUtility;
import org.openqa.selenium.By;

public class UampPage extends DriverFactory {

    static String pageLabel = property.returnPropVal_mobi(mobi_fileName, "pageLabelXpath");

    static By playingTime = By.id(property.returnPropVal_mobi(mobi_fileName, "playingTimeId"));

    //region <validateHomePage>
    public static void validateHomePage() {

        //validate UAMP label
        AppiumUtility.waitForOptionByXpath(pageLabel, "UAMP", "Failed to wait for 'UAMP' title label");

        //validate Recommended label
        AppiumUtility.waitForOptionByXpath(pageLabel, "Recommended", "Failed to wait for 'Recommended' label");

        //validate Albums label
        AppiumUtility.waitForOptionByXpath(pageLabel, "Albums", "Failed to wait for 'Albums' label");

        log("Validate Home Page executed successfully","INFO",  "text");
    }
    //endregion

    //region <selectRecommended>
    public static void selectRecommended() {

        //click Recommended label
        AppiumUtility.clickOptionByXpath(pageLabel, "Recommended", "Failed to click 'Recommended' label");

        //validate Recommended label is clicked
        AppiumUtility.waitForOptionByXpath(pageLabel, "Jazz in Paris", "Failed to wait for 'Jazz in Paris' label");

        log("Select 'Recommended' executed successfully","INFO",  "text");
    }
    //endregion

    //region <playJazzInParis>
    public static void playJazzInParis() {

        //click 'Jazz in Paris' label
        AppiumUtility.clickOptionByXpath(pageLabel, "Jazz in Paris", "Failed to click 'Jazz in Paris' label");

        log("Play 'Jazz in Paris' executed successfully","INFO",  "text");
    }
    //endregion
}
