# Global Kinetic Assessment Automation framework#

## FEATURES ##

The **Global Kinetic Assessment Automation framework** implements a 'Page Object Model' approach.

Page Object Model(POM) Framework has recently become a very popular test automation framework since it enables easy test maintenance 
and reduces the duplication of code ie. maximizes re-usabilty of code.
The main advantage of POM is that if the UI changes for any page, no tests need to be updated.  
Instead, we just need to change only the code within the page objects ie. only at one place.

Another new feature is the implementation of a new build system called Gradle which replaces 'Ant'.
Gradle nicely combines both Ant and Maven, taking the best from both tools. 
Flexibility from Ant and convention over configuration, dependency management and plugins from Maven.

This Automation framework allows you to run tests on almost any browser and OS, as well as API and Android App. 

It also provides the ability to run locally on Windows, Mac, Ubuntu with minimal changes to any config files.

## TOOLS USED ##

IntelliJ IDE, Java(version 14), Gradle(version 5.2.1), RestAssured, Allure Report, TestNG, SDK(1.8), Android Studio 4.x, Appium 1.9.x.

---

## FRAMEWORK STRUCTURE ##

### build.gradle files ###
The build.gradle file found on the **Automation framework root folder** contains all the common dependencies that are used in the project

### Engine ###
Contains functionality that form the backbone of the framework
**DriverFactory.java**
- Contains configurations to setup and connect to webdriver locally
- Specifies the desired capabilities for each browser instance i.e. Browser version, OS/Platform, timeouts etc
- Functionality to quit browser/app on completion or failure of a test
- Setup BaseUrl for API

### pageObjects ###
Provides the base structure and properties of a web page object which extend onto the tests

### utils ###
Contains logic or functionality needed to run tests with efficiency:

**PropertyFileReader.java** - reads element id's, xpaths and other identifier types that are specified in the ".properties" files

**SeleniumUtility.java** - where the web custom action methods are stored

**AppiumUtility.java** - where the mobile custom action methods are stored

**ExcelUtility.java** - where we setup the excel file used to store test data

**SetEnvironmentDataUtility.java** - where set up the environment for the different API baseUrls and Paths

**JsonUtility** - where we have methods to read and update Json files

### testSuites ###
Contains test cases to be executed

---

## EXECUTING TEST SUITE ##
1. Go to top menu bar and navigate to Run > Edit Configurations...
2. On the 'Run/Debug Configurations' window, 'Add New Configuration' by clicking the (+) at the top-left corner
3. Select 'Gradle'
4. Then fill in the fields below:
	* Gradle projects: GlobalKineticTest (Navigate to the project location)
	* Tasks: clean run_test
	* Arguments: --stacktrace
5. Press 'Apply', then 'Ok' button
6. Finally to run the test
	* Press the Green Play button
	* Or navigate to Run > Run 'GlobalKineticTest [clean run_test]'
	
## GENERATE ALLURE REPORT on Mac ##
Reference: https://docs.qameta.io/allure/

1. Execute a test

2. From the terminal, navigate to the project root:

	e.g. $ cd ~/Documents/Projects/Java/GlobalKineticTest
	
3. Execute the terminal command: 

	$ allure serve ./build/allure-results/ 

### TIME TAKEN BY SECTION  ###
1. Task1: 5h
2. Task2: 3h
3. Task3: 3h
