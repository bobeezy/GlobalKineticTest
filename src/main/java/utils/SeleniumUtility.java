package main.java.utils;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import main.java.Engine.DriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtility extends DriverFactory {

    public static int WaitTimeout = 6;

    //region <clearTextByXpath>
    public static void clearTextByXpath(String elementXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
            elementToTypeIn.clear();

            System.out.println("Cleared text on element by Xpath : " + elementXpath);
            Assert.assertTrue("Cleared text on element by Xpath : " + elementXpath, true);
        }
        catch (Exception e) {
            System.out.println("Error clearing text - " + elementXpath + " - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to clear text on element by Xpath  ---  " + elementXpath + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElementByXpath>
    public static void clickElementByXpath(String elementXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
            WebElement elementToClick = driver.findElement(By.xpath(elementXpath));
            elementToClick.click();

            System.out.println("Clicked element by Xpath : " + elementXpath);
            Assert.assertTrue("Clicked element by Xpath : " + elementXpath, true);
        }
        catch (Exception e) {
            System.out.println("Failed to click on element by Xpath - " + elementXpath + "' - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to click on element by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <enterTextByXpath>
    public static void enterTextByXpath(String elementXpath, String textToEnter, String errorMessage) {

        try {
            if (textToEnter.equals("")) {
                System.out.println("There is No text to enter");
                Assert.assertTrue("There is No text to enter", true);
            }
            else if(textToEnter.equalsIgnoreCase("Clear")) {
                waitForElementByXpath(elementXpath, errorMessage);
                WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
                elementToTypeIn.clear();
                Assert.assertTrue("Cleared text field", true);
            }
            else {
                waitForElementByXpath(elementXpath, errorMessage);
                Actions action = new Actions(driver);
                WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
                elementToTypeIn.click();
                action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
                elementToTypeIn.sendKeys(textToEnter);

                System.out.println("Entered text : \"" + textToEnter + "\" to : " + elementXpath);
                Assert.assertTrue("Entered text : \"" + textToEnter + "\" to : " + elementXpath, true);
            }
        }
        catch (Exception e) {
            System.out.println("[ERROR] Failed to enter text by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <pause>
    public static void pause(int millisecondsWait) {

        try {
            Thread.sleep(millisecondsWait);
        }
        catch (Exception e) {
        }
    }
    //endregion

    //region <selectFromDropDownListByXpath>
    public static void selectFromDropDownListByXpath(String dropdownlistXpath, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(dropdownlistXpath, valueToSelect); //update default value saved in .properties
            waitForElementByXpath(updatedXpath, errorMessage);
            Select dropDownList = new Select(driver.findElement(By.xpath(updatedXpath)));
            WebElement formxpath = driver.findElement(By.xpath(updatedXpath));
            formxpath.click();
            dropDownList.selectByVisibleText(valueToSelect);

            System.out.println("Selected Text : " + valueToSelect + " from : " + updatedXpath);
            Assert.assertTrue("Selected Text : " + valueToSelect + " from : " + updatedXpath, true);
        }
        catch (Exception e) {
            System.out.println("Failed to select from dropdown list by text using Xpath - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to select text : " + valueToSelect + " from dropdown list by Xpath  ---  " + e.getMessage());
        }
    }
    //endregion

    //region <sendKeys>
    public static void sendKeys(String choice) {

        try {
            Actions action = new Actions(driver);

            switch (choice.toUpperCase()) {
                case "ARROW DOWN": {
                    action.sendKeys(Keys.ARROW_DOWN);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "ENTER": {
                    action.sendKeys(Keys.ENTER);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "TAB": {
                    action.sendKeys(Keys.TAB);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "COPY ALL": {
                    action.sendKeys(Keys.CONTROL, "a"); //Ctrl+a
                    action.sendKeys(Keys.CONTROL, "c"); //Ctrl+c
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "REFRESH": {
                    action.keyDown(Keys.CONTROL);
                    action.sendKeys(Keys.F5);
                    action.keyUp(Keys.CONTROL);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to send keypress : \"" + choice + "\"");
            Assert.fail("\n[ERROR] Failed to send keypress : \"" + choice + "\" --- " + e.getMessage());
        }
    }
    //endregion

    //region <To update an Xpath value from the .properties file>
    public static String updateXpathValueWithString(String xp, String value) {
        String xpath = xp.replace("value", value);
        return xpath;
    }
    //endregion

    //region <waitForElementByXpath>
    public static void waitForElementByXpath(String elementXpath, String errorMessage) {

        boolean elementFound = false;
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

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
                    System.out.println(errorMessage);
                    Assert.fail("\n[ERROR] Did Not Find element by Xpath : " + elementXpath + "' - " + e.getMessage());
                }
                //Thread.sleep(500);
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                System.out.println("Reached TimeOut whilst waiting for element by Xpath : '" + elementXpath + "'");
            }

        }
        catch (Exception e) {
            System.out.println(errorMessage);
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

    //region <waitForElement>
    public static void waitForElement(By element, String errorMessage) {

        boolean elementFound = false;
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

                    wait.until(ExpectedConditions.presenceOfElementLocated(element));
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(element)) != null) {
                        elementFound = true;
                        System.out.println("Found element : " + element);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                    System.out.println(errorMessage);
                    Assert.fail("\n[ERROR] Did Not Find element : " + element + "' - " + e.getMessage());
                }
                //Thread.sleep(500);
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1_(elementFound);
                System.out.println("Reached TimeOut whilst waiting for element : '" + element + "'");
            }

        }
        catch (Exception e) {
            System.out.println(errorMessage);
            Assert.fail("\n[ERROR] Failed to wait for element --- " + element);
        }

        GetElementFound_(elementFound);
    }

    private static boolean GetElementFound_(boolean elementFound) {
        return elementFound;
    }

    private static boolean GetElementFound1_(boolean elementFound) {
        return elementFound;
    }
    //endregion

}
