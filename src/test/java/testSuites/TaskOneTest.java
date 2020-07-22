package testSuites;

/**
 * @author lionel.mangoua
 * date: 03/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.web.pageClasses.Home;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

public class TaskOneTest extends DriverFactory {

    @Features("Global Kinetic Task 1")
    @Description("* Navigate to link")
    @Stories("Task 1.a")
    @Test(priority = 0)
    public void NavigateToUrl() {

        Home.navigateToUrl();

        log("\n=============== NavigateToUrl test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 1")
    @Description("* Validate that you are on the User List Table")
    @Stories("Task 1.b")
    @Test(priority = 1)
    public void ValidateUserListTable() {

        Home.validateTable();

        log("\n=============== ValidateUserListTable test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 1")
    @Description("* Click Add user")
    @Stories("Task 1.c")
    @Test(priority = 2)
    public void ClickAddUser() {

        Home.clickAddUser();

        log("\n=============== ClickAddUser test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 1")
    @Description("* Add users")
    @Stories("Task 1.d")
    @Test(priority = 3)
    public void AddUsersToTable() {

        Home.addUsersToTable();

        log("\n=============== AddUsersToTable test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 1")
    @Description("* Ensure that User Name is unique on each run")
    @Stories("Task 1.e")
    @Test(priority = 4)
    public void ValidateUserNameIsUnique() {

        Home.validateUserNameIsUnique();

        log("\n=============== ValidateUserNameIsUnique test executed successfully ===============\n","INFO",  "text");
    }

    @Features("Global Kinetic Task 1")
    @Description("* Ensure that your users are added to the list")
    @Stories("Task 1.f")
    @Test(priority = 5)
    public void ValidateUserAreAddedToList() {

        Home.validateUserAreAddedToList();

        log("\n=============== ValidateUserAreAddedToList test executed successfully ===============\n","INFO",  "text");
    }
}
