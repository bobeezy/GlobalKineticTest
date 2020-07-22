package main.java.api;

/**
 * @author lionel.mangoua
 * date: 07/07/20
 */

import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;
import main.java.Engine.DriverFactory;
import main.java.utils.JsonUtility;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static main.java.api.CustomHeaders.buildCustomHeaders;
import static main.java.api.CustomHeaders.customHeadersMap;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.hasItems;

public class TaskThreeMethods extends DriverFactory {

    APICommonMethods api = new APICommonMethods();

    public static List subBreedList = new ArrayList();

    String loginJsonBodyPath = property.returnPropVal_api(api_fileName, "loginJsonBodyPath");
    String loginPath = property.returnPropVal_api(api_fileName, "loginPath");
    String passwordPath = property.returnPropVal_api(api_fileName, "passwordPath");

    public ValidatableResponse getListOfAllBreeds() {

        log("========================== Get List Of All Breeds ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_all_breeds_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getListOfSubBreeds() {

        log("========================== Get List Of Retriever Sub-Breeds ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_all_subBreeds_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getListOfHoundSubBreeds(String subBreedName) {

        log("========================== Get List Of Hound Sub-Breeds ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_list_all_subBreeds_uri");
        uri_string = uri_string.replace("subBreedName", subBreedName);

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getImageRandom() {

        log("========================== Get Random Image ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_random_image_uri");

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse getListAllSubBreedImage(String subBreedName) {

        log("========================== Get List All Sub-BreedImage ============================", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "get_all_subBreed_image_uri");
        uri_string = uri_string.replace("subBreedName", subBreedName);

        response = api.getMethod(uri_string, customHeadersMap);
        return response;
    }

    public ValidatableResponse login(String username, String password) {

        log("\n================================== Login ==================================\n", "INFO", "text");

        //Build Headers
        buildCustomHeaders("Content-Type", contentTypeJson);

        String uri_string = property.returnPropVal_api(api_fileName, "login_uri");

        response = api.postMethod(loginPayload(username, password), uri_string, customHeadersMap);

        return response;
    }

    /**
     * Payloads
     */
    //region <loginPayload>
    public String loginPayload(String usr, String pwd) {

        try {
            DocumentContext jsonDoc;
            Object jsonO = JsonUtility.loadJson(loginJsonBodyPath);

            //update Json Payload values
            JsonUtility.updateJsonFileWithString(jsonO, loginPath, usr);
            jsonDoc = JsonUtility.updateJsonFileWithString(jsonO, passwordPath, pwd);

            JsonObject jso = JsonUtility.convertDocumentContextToJsonObject(jsonDoc);
            return jso + "";
        }
        catch(JSONException ex) {
            return "Failed to update Login body: " + ex + "\n";
        }
    }
    //endregion

    /**
     * validations
     */
    //region <validateGetLocationSuccessfully>
    public void validateGetListOfAllBreeds(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");
    }
    //endregion

    //region <validateVerifyRetriever>
    public void validateVerifyRetriever(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String retriever = APICommonMethods.getValueFromJson(response, "message.retriever");

        response.assertThat().body("message.retriever", hasItems("chesapeake", "curly", "flatcoated", "golden"));
        log("ASSERT: Verify retriever is within list \nEXPECTED: chesapeake, curly, flatcoated, golden \nACTUAL: " + retriever, "INFO", "text");
    }
    //endregion

    //region <validateGetListOfSubBreeds>
    public void validateGetListOfSubBreeds(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        subBreedList = response.extract().jsonPath().getList("message");
        String message = APICommonMethods.getValueFromJson(response, "message");

        Assert.assertNotNull(message);
        log("ASSERT: list of sub-breeds for is present \nEXPECTED: Must not be null \nACTUAL: " + message, "INFO", "text");
    }
    //endregion

    //region <validateGetImageRandom>
    public void validateGetImageRandom(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String imageLink = APICommonMethods.getValueFromJson(response, "message");

        response.assertThat().body("message", containsStringIgnoringCase(".jpg"));
        log("ASSERT: link of Random Image is present \nEXPECTED: Must have a .jpg file \nACTUAL: " + imageLink, "INFO", "text");
    }
    //endregion

    //region <validateGetListAllSubBreedImage>
    public void validateGetListAllSubBreedImage(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");
    }
    //endregion

    //region <validateLogin>
    public void validateLogin(ValidatableResponse response, int status) {

        response.assertThat().statusCode(status);
        log("ASSERT: StatusCode \nEXPECTED: 200 \nACTUAL: " + status, "INFO", "text");

        String success = APICommonMethods.getValueFromJson(response, "success");

        Assert.assertEquals(success, "true");
        log("ASSERT: success \nEXPECTED: true \nACTUAL: " + success, "INFO", "text");
    }
    //endregion
}
