package main.java.web.pageClasses;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.utils.SeleniumUtility;
import main.java.web.pageObjects.HomePage;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class Home extends DriverFactory {

    static List userNameList = new ArrayList();

    public static final int numberOfUsers = 2;

    public static String firstNameLabel = "First Name";
    public static String lastNameLabel = "Last Name";
    public static String userNameLabel = "User Name";
    public static String customerLabel = "Customer";
    public static String roleLabel = "Role";
    public static String emailLabel = "E-mail";
    public static String cellPhoneLabel = "Cell Phone";
    public static String lockedLabel = "Locked";

    public static void navigateToUrl() {

        launchBrowser();

        navigateToUrl(url);
    }

    public static void validateTable() {

        validateUserListTable(firstNameLabel, lastNameLabel, userNameLabel, customerLabel,
                roleLabel, emailLabel, cellPhoneLabel, lockedLabel);
    }

    //region <launchBrowser>
    public static void launchBrowser() {

        setupLocalDriver();

        log("Launched Browser successfully","INFO",  "text");
    }
    //endregion

    //region <navigateToUrl>
    public static void navigateToUrl(String url) {

        driver.get(url);

        log("Navigated to " + url + " successfully","INFO",  "text");
    }
    //endregion

    //region <validateUserListTable>
    public static void validateUserListTable(String firstName, String lastName, String userName, String customer, String role, String email, String cellPhone, String locked) {

        //Validate User List Table
        /*Validate presence of the table column name*/
        SeleniumUtility.waitForElementByXpath(HomePage.columnNameXpath(firstName, lastName, userName, customer, role, email, cellPhone, locked), "Failed to validate table column name");

        /*Validate presence of "Add User" button*/
        SeleniumUtility.waitForElementByXpath(HomePage.buttonXpath("Add User"), "Failed to validate 'Add User' button");

        log("Validated User List Table successfully","INFO",  "text");
    }
    //endregion

    //region <clickAddUser>
    public static void clickAddUser() {

        SeleniumUtility.clickElementByXpath(HomePage.buttonXpath("Add User"), "Failed to click 'Add user' button");

        log("Clicked 'Add User' button successfully","INFO",  "text");
    }
    //endregion

    //region <addUsersToTable>
    public static void addUsersToTable() {

        /**
         * add USER1 & USER2
         * */
        addUser();

        log("Added Users To Table successfully","INFO",  "text");
    }
    //endregion

    //region <addUser>
    public static void addUser() {

        for(int i = 1; i <= numberOfUsers; i++) {
            StringBuilder sb = new StringBuilder("User");
            String userNumber = sb.append(i).toString();

            setTestDataForTest(userNumber); //set Data to extract from sheet

            //Enter "First Name"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("FirstName"), "Failed to clear user" + i + " 'First Name' field: " + currentTestData.get("FirstName"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("FirstName"), currentTestData.get("FirstName"), "Failed to add user" + i + " 'First Name': " + currentTestData.get("FirstName"));

            //Enter "Last Name"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("LastName"), "Failed to clear user" + i + " 'Last Name' field: " + currentTestData.get("LastName"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("LastName"), currentTestData.get("LastName"), "Failed to add user" + i + " 'Last Name': " + currentTestData.get("LastName"));

            //Enter "User Name"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("UserName"), "Failed to clear user" + i + " 'User Name' field: " + currentTestData.get("Username"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("UserName"), currentTestData.get("Username"), "Failed to add user" + i + " 'User Name': " + currentTestData.get("Username"));
            userNameList.add(currentTestData.get("Username"));

            //Enter "Password"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("Password"), "Failed to clear user" + i + " 'Password' field: " + currentTestData.get("Password"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("Password"), currentTestData.get("Password"), "Failed to add user" + i + " 'Password': " + currentTestData.get("Password"));

            //click "Customer"
            SeleniumUtility.clickElementByXpath(HomePage.radioButtonXpath(currentTestData.get("Customer")), "Failed to click user" + i + " 'Customer': " + currentTestData.get("Customer"));

            //Select "Role"
            SeleniumUtility.selectFromDropDownListByXpath(HomePage.dropdownListBoxXpath(), currentTestData.get("Role"), "Failed to select user" + i + " 'Role': " + currentTestData.get("Role"));

            //Enter "Email"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("Email"), "Failed to clear user" + i + " 'Email' field: " + currentTestData.get("Email"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("Email"), currentTestData.get("Email"), "Failed to add user" + i + " 'Email': " + currentTestData.get("Email"));

            //Enter "Cell Phone"
            SeleniumUtility.clearTextByXpath(HomePage.textBoxXpath("Mobilephone"), "Failed to clear user" + i + " 'Cell Phone field: " + currentTestData.get("Cellphone"));
            SeleniumUtility.enterTextByXpath(HomePage.textBoxXpath("Mobilephone"), currentTestData.get("Cellphone"), "Failed to add user" + i + " 'Cell Phone: " + currentTestData.get("Cellphone"));

            //Click "Save" button
            SeleniumUtility.clickElementByXpath(HomePage.buttonXpath("Save"), "Failed to click 'Save' button");

            if(i < numberOfUsers) {
                //Click "Add User" button
                SeleniumUtility.clickElementByXpath(HomePage.buttonXpath("Add User"), "Failed to click 'Add User' button");

                log("Added User #" + i + " To Table successfully\n","INFO",  "text");
            }
            else {
                log("Added User #" + i + " To Table successfully\n","INFO",  "text");
                break;
            }
        }
    }
    //endregion

    //region <validateUserNameIsUnique>
    public static void validateUserNameIsUnique() {

        for(int i = 0; i < userNameList.size(); i++) {
            String extractedUserName = (String) userNameList.get(i);
            if(extractedUserName.equalsIgnoreCase(userNameList.get(0).toString())) {
                log("The first user name " + userNameList.get(0).toString() + " is Unique", "INFO", "text");
            }
            else if(extractedUserName.equalsIgnoreCase(userNameList.get(1).toString())) {
                if(userNameList.get(1).toString().equalsIgnoreCase(userNameList.get(0).toString())) {
                    Assert.fail("\n[ERROR] The user name '" + userNameList.get(1).toString() + "' IS NOT UNIQUE!!!");
                }
                else {
                    log("The second user name " + userNameList.get(1).toString() + " is Unique", "INFO", "text");
                }
            }
            else {
                log("The user name IS UNIQUE", "INFO", "text");
            }
        }

        log("Validated User Name Is Unique successfully","INFO",  "text");
    }
    //endregion

    //region <validateUserAreAddedToList>
    public static void validateUserAreAddedToList() {

        for(int i = 1; i <= numberOfUsers; i++) {
            StringBuilder sb = new StringBuilder("User");
            String userNumber = sb.append(i).toString();

            setTestDataForTest(userNumber); //set Data to extract from sheet

            //Verify user1 created are in the list
            SeleniumUtility.waitForElementByXpath(HomePage.userRowXpath(currentTestData.get("FirstName"), currentTestData.get("LastName"),
                    currentTestData.get("Username"), currentTestData.get("Customer"), currentTestData.get("Role"), currentTestData.get("Email"),
                    currentTestData.get("Cellphone")), "Failed to validate User" + i + " was created");
            log("Validated User" + i + " Is Added To List successfully","INFO",  "text");
        }

        log("Validated Users Are Added To List successfully","INFO",  "text");
    }
}
