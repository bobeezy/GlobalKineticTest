package testSuites;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import main.java.Engine.DriverFactory;
import main.java.api.TaskThreeMethods;
import main.java.utils.SetEnvironmentDataUtility;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import static main.java.api.TaskThreeMethods.subBreedList;

public class TaskThreeTest extends DriverFactory {

    TaskThreeMethods taskOne = new TaskThreeMethods();
    SetEnvironmentDataUtility setEnv = new SetEnvironmentDataUtility();

    public static String afghanDog = "";
    String login = property.returnPropVal_api(api_fileName, "login");
    String password = property.returnPropVal_api(api_fileName, "password");

    @Features("Global Kinetic Task 3")
    @Description("Perform an API request to produce a list of all dog breeds")
    @Stories("Task 3.a")
    @Test(priority = 10)
    public void GetListOfAllBreeds() {

        response = taskOne.getListOfAllBreeds();
        taskOne.validateGetListOfAllBreeds(response, 200);

        System.out.println("\n=============== GetListOfAllBreeds test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Verify “retriever” breed is within the list")
    @Stories("Task 3.b")
    @Test(priority = 11)
    public void VerifyRetriever() {

        response = taskOne.getListOfAllBreeds();
        taskOne.validateVerifyRetriever(response, 200);

        System.out.println("\n=============== VerifyRetriever test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Perform an API request to produce a list of sub-breeds for “retriever”")
    @Stories("Task 3.c")
    @Test(priority = 12)
    public void GetListOfSubBreeds() {

        response = taskOne.getListOfSubBreeds();
        taskOne.validateGetListOfSubBreeds(response, 200);

        System.out.println("\n=============== GetListOfSubBreeds test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Perform an API request to random image/link for the sub-breed “golden”")
    @Stories("Task 3.d")
    @Test(priority = 13)
    public void GetImageRandom() {

        response = taskOne.getImageRandom();
        taskOne.validateGetImageRandom(response, 200);

        System.out.println("\n=============== GetImageRandom test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Perform an API request to produce a list of sub-breeds for “hound”")
    @Stories("Task 3.e")
    @Test(priority = 14)
    public void GetListOfHoundSubBreeds() {

        response = taskOne.getListOfHoundSubBreeds("hound");

        subBreedList.clear();
        taskOne.validateGetListOfSubBreeds(response, 200);

        System.out.println("\n=============== GetListOfHoundSubBreeds test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Perform an API request to list all sub-breed images for the sub-breed “afghan”")
    @Stories("Task 3.f")
    @Test(priority = 15)
    public void GetListAllSubBreedImage() {

        afghanDog = subBreedList.get(0).toString();

        response = taskOne.getListAllSubBreedImage(afghanDog);
        taskOne.validateGetListAllSubBreedImage(response, 200);

        System.out.println("\n=============== GetImageRandom test executed successfully ===============\n");
    }

    @Features("Global Kinetic Task 3")
    @Description("Perform an API Post request to Login")
    @Stories("Task 3.g")
    @Test(priority = 16)
    public void Login() {

        setEnv.setTestEnvironment("login"); //set up environment

        response = taskOne.login(login, password);
        taskOne.validateLogin(response, 200);

        System.out.println("\n=============== Login test executed successfully ===============\n");
    }
}
