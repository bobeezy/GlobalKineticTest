package main.java.Engine;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import main.java.config.GlobalEnums;
import main.java.utils.ExcelUtility;
import main.java.utils.PropertyFileReader;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;

public class DriverFactory {

    public static PropertyFileReader property = new PropertyFileReader();
    public static String api_fileName = "API";
    public static String web_fileName = "WEB";
    public static String mobi_fileName = "MOBILE";
    public static String testName;
    public static String[][] excelData = null;
    public static String[][] userInfoData = null;
    public static Map<String, String> currentTestData = null; //to get test data from excel sheet
    public static String USER_DATA_XLSX_FILE_PATH = property.returnPropVal_web(web_fileName, "userDataSheetPath");
    public static GlobalEnums.Environment env; //setup Environment
    //Mobile
    public static AndroidDriver Driver; //For appium
    public static String appAbsolutePath = "";
    public static DesiredCapabilities capabilities;
    public static GlobalEnums.Devices deviceType;
    public static String DEVICE = System.getProperty("DEVICE");
    public static String HOST = System.getProperty("HOST");
    public static String OperatingSystem = System.getProperty("OS");
    public static String uampAppPath = property.returnPropVal_mobi(mobi_fileName, "uampAppPath");
    //Web
    public static RemoteWebDriver driver;
    public static WebDriverWait wait = null;
    public static final int WAIT_TIME = 30;
    public static String BROWSER = System.getProperty("BROWSER");
    public static ChromeOptions options;
    public static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    public static String geckoPath = property.returnPropVal_web(web_fileName, "geckoPath");
    public static String chromePath = property.returnPropVal_web(web_fileName, "chromePath");
    public static String url = property.returnPropVal_web(web_fileName, "url");
    //Headers
    public static ValidatableResponse response = null;
    public static RequestSpecBuilder builder;
    public static RequestSpecification requestSpec; //used with ValidatableResponse
    public static ResponseSpecification responseSpec; //used with ValidatableResponse
    public static ResponseSpecBuilder respec;
    public static String contentTypeJson = property.returnPropVal_api(api_fileName, "content_type_json");
    public static String cacheControl = property.returnPropVal_api(api_fileName, "cache_control");

    @BeforeClass
    //region <startEngine>
    public void startEngine() {

        //Read excel sheets
        setUpExcelDataSheets();

        //region <To remove the warning: "log4j:WARN No appenders could be found for logger">
        Properties prop = new Properties();
        prop.setProperty("log4j.rootLogger", "WARN");
        PropertyConfigurator.configure(prop);
        //endregion

        //Set Up default environment for API tests
        env = GlobalEnums.Environment.DOGS;

        //Set Up Base Url
        setUpBaseUrl();
    }
    //endregion

    @BeforeMethod(alwaysRun = true)
    //region <testSetUp>
    /**
     * This method is used to pull all the testName rows and
     * their respective data
     */
    public void testSetUp(Method method) {

        testName = method.getName();
        setTestDataForTest(testName);
    }
    //endregion

    //API
    //region <Set Up Base Url>
    public static void setUpBaseUrl() {

        RestAssured.baseURI = env.baseUrl;
        RestAssured.basePath = env.path;

        builder = new RequestSpecBuilder();
        respec = new ResponseSpecBuilder();

        builder.addFilter(new AllureRestAssured());//To setup Filter that gonna attach Request/Response logs to report
        respec.expectResponseTime(lessThan(10L), TimeUnit.SECONDS);

        requestSpec = builder.build();
        responseSpec = respec.build();

        log("Setup Base URL successfully \n", "INFO", "text");
    }
    //endregion

    //MOBILE
    //region <openMobileApp>
    public void openMobileApp(String appName) {

        createLocalDriver(appName);

        Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(Driver, WAIT_TIME);
    }
    //endregion

    //region <createLocalDriver>
    public void createLocalDriver(String appName) {

        try {
            log("Mobile test is Starting ... \n", "INFO", "text");

            File appFile = new File(appName);

            log("Local App driver directory - " + appFile.getAbsolutePath(), "INFO", "text");
            appAbsolutePath = appFile.getAbsolutePath();

            capabilities = new DesiredCapabilities();

            capabilities.setCapability("name", testName);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.APP, appAbsolutePath + "");

            log("Device is: '" + DEVICE +"'", "INFO", "text");
            deviceType = GlobalEnums.Devices.NEXUS_6_EMULATOR; //Set device type
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceType.deviceName);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceType.platformVersion);
            capabilities.setCapability(MobileCapabilityType.UDID, deviceType.udid); //Android Studio emulator
            capabilities.setCapability("unicodeKeyboard", true); //To remove virtual keyboard
            capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.9.1");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false); //To reset app before each session
            capabilities.setCapability("newCommandTimeout", 15); //To make test cases fail fast in order to quickly get an error message
            capabilities.setCapability("autoAcceptAlerts", true); //To accept popup alerts
            capabilities.setCapability("autoGrantPermissions", true); //To accept popup alerts
            capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true); //To accept popup alerts
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 700); //For the App to go to sleep after 700s
            capabilities.setCapability("ignoreUnimportantViews", true); //helps to reduce the time of inputs on Android
            capabilities.setCapability("disableAndroidWatchers", true); //helps to reduce the time of inputs on Android

            log("Driver = " + HOST + ":4723/wd/hub \n", "INFO", "text");

            Driver = new AndroidDriver(new URL("http://" + HOST + ":4723/wd/hub"), capabilities);
            log(OperatingSystem + " on local machine initiated \n", "INFO", "text"); //Setup Android Virtual Device's name
        }
        catch (Exception e) {
            log("Failed to setup Local Mobile Driver --- " + e.getMessage(), "ERROR", "text");
        }
    }
    //endregion

    //WEB
    //region <setupLocalDriver>
    public static void setupLocalDriver() {

        switch(BROWSER) {
            case("Firefox"):
                LOGGER.info("Web test is Starting ... \n");

                String geckoAbsolutePath;
                File geckoFile = new File(geckoPath);
                System.out.println("Gecko driver directory - " + geckoFile.getAbsolutePath());
                geckoAbsolutePath = geckoFile.getAbsolutePath();

                System.setProperty("webdriver.gecko.driver", geckoAbsolutePath + "");

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                driver = new FirefoxDriver(firefoxOptions);
                LOGGER.info(BROWSER + " on local machine initiated \n");
                break;
            case("Chrome"):
                LOGGER.info("Web test is Starting ... \n");

                String chromeAbsolutePath;
                File chromeFile = new File(chromePath);
                System.out.println("Chrome driver directory - " + chromeFile.getAbsolutePath());
                chromeAbsolutePath = chromeFile.getAbsolutePath();

                System.setProperty("webdriver.chrome.driver", chromeAbsolutePath + "");

                options = new ChromeOptions();

                options.addArguments("--disable-extensions");
                options.addArguments("disable-infobars");
                options.addArguments("test-type");
                options.addArguments("enable-strict-powerful-feature-restrictions");
                options.setCapability(ChromeOptions.CAPABILITY, options);
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                driver = new ChromeDriver(options);
                LOGGER.info(BROWSER + " on local machine initiated \n");
                break;
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, WAIT_TIME);
    }
    //endregion

    //Test Data Setup
    //region <xlsxFilePath>
    /**
     * Set up file path for "UserData" excel sheet
     */
    public static String xlsxFilePath() {

        String path = "";

        path = USER_DATA_XLSX_FILE_PATH;

        log("Setup .xlsx File Path: '" + path + "' successfully \n", "INFO", "text");
        return path;
    }
    //endregion

    //region <setUpExcelDataSheets>
    /**
     * This method is used to read the excel file and store
     * all data from each sheet into a set up variable.
     * Every time we create a new excel sheet, we need to set it
     * up here.
     */
    public static void setUpExcelDataSheets() {

        try {
            //Read excel sheets based on the Sheet Name
            userInfoData = ExcelUtility.readExcelFile("UserInfo"); //UserInfo = sheet name
            if(userInfoData == null || userInfoData.equals("")) {
                LOGGER.info("[ERROR] userInfoData value is: " + userInfoData);
            }
        }
        catch(Exception e) {
            log("Something went wrong reading the excel sheet --- " + e, "INFO", "text");
        }
    }
    //endregion

    //region <setTestDataForTest>
    /**
     * This method is used to set a test method
     * so that its data can be used within a different test method.
     * It stores every data based on the excel sheet name into currentTestData..
     * Every time we create a new Sheet in the .xlsx file, this function should be updated
     */
    public static void setTestDataForTest(String test_name) {

        testName = test_name;

        if(checkTestOnArray(testName, userInfoData)) {
            currentTestData = getSpecificTestData(testName, userInfoData);
        } //UserInfo sheet in 'UserData.xlsx' file

        log("Setup test data for test '" + testName + "' successfully \n", "INFO", "text");
    }
    //endregion

    //region <getSpecific TestData>
    /**
     * This method gets a specific test case data from the excelData 2D array
     */
    public static Map<String, String> getSpecificTestData(String testName, String[][] excelData) {

        currentTestData = new LinkedHashMap<>();

        int numRows = excelData.length; //Get number of rows
        int numCols = excelData[0].length; //Get number of columns

        for(int i = 0; i < numRows; i++) {
            if(excelData[i][0].equalsIgnoreCase(testName)) {
                for(int j = 0; j < numCols; j++) {
                    currentTestData.put(excelData[0][j], excelData[i][j]);
                }
            }
        }

        log("Get specific data for test case: '" + testName + "' successfully \n", "INFO", "text");
        return currentTestData;
    }
    //endregion

    //region <checkTestOnArray>
    /**
     * This method checks that the testName extracted from the excel sheet
     * is stored in the excelData array
     */
    public static boolean checkTestOnArray(String testName, String[][] excelData) {

        int numRows = excelData.length;

        for(int i = 0; i < numRows; i++) {
            if(excelData[i][0].equalsIgnoreCase(testName)) {
                return true;
            }
        }

        return false;
    }
    //endregion

    //region <logger>
    public static void logger(final String message, final String level, String format) {

        if(format.equalsIgnoreCase(("json"))) {
            String json = (new JSONObject(message)).toString(4); //To convert into pretty Json format
            LOGGER.info("\n" + json); //To print on the console
        }
        else {
            LOGGER.info(message); //To print on the console
        }
    }

    public static void log(final String message, final String level, String format) {

        try {
            logger(message, level, format);
        }
        catch (JSONException err) {
            logger(message, level, "text");
        }
    }
    //endregion

    @AfterSuite(alwaysRun = true)
    //region <tearDown>
    public void tearDown() {

        try {
            if(driver != null) {
                //To check if the browser is opened
                LOGGER.info("Test - WEB is Ending ...\n");
                driver.quit();
            }
            if(Driver != null) {
                //To check if the mobile app is opened
                LOGGER.info("Test - MOBILE is Ending ...\n");
                Driver.closeApp(); //Close the app which was provided in the capabilities at session creation
                Driver.quit(); //Quit the session created between the client and the server
            }
        }
        catch(Exception ex) {
            log("Something went wrong on test suite tear down!!! ---> " + ex, "INFO", "text");
        }
    }
    //endregion
}
