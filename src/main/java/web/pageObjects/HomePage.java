package main.java.web.pageObjects;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

public class HomePage {

    public static String buttonXpath(String value) {
        return "//button[contains(text(), '" + value + "')]";
    }

    public static String columnNameXpath(String firstName, String lastName, String userName, String customer, String role, String email, String cellPhone, String locked) {
        return "//tr//span[contains(text(), '" + firstName + "')]/../..//span[contains(text(), '" + lastName + "')]" +
                "/../..//span[contains(text(), '" + userName + "')]/../..//span[contains(text(), '" + customer + "')]" +
                "/../..//span[contains(text(), '" + role + "')]/../..//span[contains(text(), '" + email + "')]" +
                "/../..//span[contains(text(), '" + cellPhone + "')]/../..//span[contains(text(), '" + locked + "')]";
    }

    public static String dropdownListBoxXpath() {
        return "//select[@name='RoleId']";
    }

    public static String radioButtonXpath(String value) {
        return "//label[normalize-space()='" + value + "']/input[@type='radio']";
    }

    public static String textBoxXpath(String value) {
        return "//input[@name='" + value + "']";
    }

    public static String tableXpath() {
        return "//table/tbody";
    }

    public static String userRowXpath(String firstName, String lastName, String userName, String customer, String role, String email, String cellPhone) {
        return "//td[contains(text(), '" + firstName + "')]/..//td[contains(text(), '" + lastName + "')]/..//td[contains(text(), '" + userName + "')]" +
                "/..//td[contains(text(), '" + role + "')]/..//td[contains(text(), '" + email + "')]" +
                "/..//td[contains(text(), '" + cellPhone + "')]";
    }

    public static String userRow2Xpath(String firstName, String lastName, String userName, String customer, String role, String email, String cellPhone) {
        return "//td[contains(text(), '" + firstName + "')]/..//td[contains(text(), '" + lastName + "')]/..//td[contains(text(), '" + userName + "')]" +
                "/..//td[contains(text(), '" + customer + "')]/..//td[contains(text(), '" + role + "')]/..//td[contains(text(), '" + email + "')]" +
                "/..//td[contains(text(), '" + cellPhone + "')]";
    }
}
