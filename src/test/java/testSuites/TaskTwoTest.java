package testSuites;

/**
 * @author lionel.mangoua
 * date: 08/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.mobile.UampPage;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TaskTwoTest extends DriverFactory {

    @Features("Global Kinetic Task 2")
    @Description("* Open UAMP Mobile App")
    @Stories("Task 2.a")
    @Test(priority = 6)
    public void OpenUAMPApp() {

        openMobileApp(uampAppPath); //Launch UAMP App

        log("\n=============== OpenUAMPApp test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 2")
    @Description("* Validate The Home Page")
    @Stories("Task 2.b")
    @Test(priority = 7)
    public void ValidateHomePage() {

        UampPage.validateHomePage();

        log("\n=============== ValidateHomePage test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 2")
    @Description("* Navigate to 'Recommended'")
    @Stories("Task 2.c")
    @Test(priority = 8)
    public void NavigateToRecommended() {

        UampPage.selectRecommended();

        log("\n=============== NavigateToRecommended test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 2")
    @Description("* Play 'Jazz in Paris'")
    @Stories("Task 2.d")
    @Test(priority = 9)
    public void PlayJazzInParis() {

        UampPage.playJazzInParis();

        log("\n=============== PlayJazzInParis test executed successfully ===============\n","INFO",  "text");
    }
}
