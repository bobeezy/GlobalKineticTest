package main.java.utils;

/**
 * @author lionel.mangoua
 * Date: 08/07/2020
 */

import main.java.Engine.DriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumUtility extends DriverFactory {

    private static int WaitTimeout = 6;

    //region <clickOptionByXpath>
    /*Dynamic method to click an element by Xpath. Using a dynamic Xpath of the element, we just specify the value to click*/
    public static void clickOptionByXpath(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(Driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
            WebElement elementToClick = Driver.findElement(By.xpath(updatedXpath));
            elementToClick.click();

            System.out.println("Clicked element by Xpath : " + updatedXpath);
            Assert.assertTrue("Clicked element by Xpath : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <To update an Xpath value from the .properties file>
    public static String updateXpathValueWithString(String xp, String value) {
        return xp.replace("value", value);
    }
    //endregion

    //region <waitForElementByXpath>
    public static void waitForElementByXpath(String elementXpath, String errorMessage) {

        boolean elementFound = false;
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(Driver, 1);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath))) != null) {
                        elementFound = true;
                        System.out.println("Found element by Xpath : " + elementXpath);
                        Assert.assertTrue("Found element by Xpath : " + elementXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                    log("\nDid Not Find element by Xpath : " + elementXpath, "ERROR",  "text");
                }
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath --- " + elementXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath --- " + elementXpath + "' - " + e.getMessage());
        }

        GetElementFound(elementFound);
    }

    private static boolean GetElementFound(boolean elementFound) {
        return elementFound;
    }

    private static boolean GetElementFound1(boolean elementFound) {
        return elementFound;
    }
    //endregion

    //region <waitForOptionByXpath>
    /*Dynamic method to wait for an element Xpath. Using a dynamic Xpath of the element, we just specify the value to select*/
    public static void waitForOptionByXpath(String elementXpath, String valueToWaitFor, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(Driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToWaitFor); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        log("Found element by Xpath: " + updatedXpath, "INFO",  "text");
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath --- " + updatedXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }

        GetElementFound(elementFound);
    }
    //endregion
}