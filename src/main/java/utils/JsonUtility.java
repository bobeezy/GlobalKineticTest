package main.java.utils;

/**
 * @author lionel.mangoua
 * date: 09/07/20
 */

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import main.java.Engine.DriverFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtility extends DriverFactory {

    public static DocumentContext doc = null;

    //region <Load json file from directory>
    public static Object loadJson(String filePath) {

        try {
            JSONParser parser = new JSONParser();

            //need to use a variable to store a file name
            return parser.parse(new FileReader(filePath));
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion

    //region <updateJsonFileWithString>
    /**
     * To update Json file with a String value
     */
    public static DocumentContext updateJsonFileWithString(Object object, String jsonXpath, String value) {

        try {
            doc = JsonPath.parse(object).set(jsonXpath, value);

            return doc;
        }
        catch(Exception e) {
            e.printStackTrace();
            return doc;
        }
    }
    //endregion

    //region <Convert documentContext to json object>
    public static JsonObject convertDocumentContextToJsonObject(DocumentContext jsonObject) {

        try {
            return new GsonBuilder().create().toJsonTree(jsonObject.json()).getAsJsonObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //endregion
}
