package in.reqres;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import groovy.json.JsonException;


public class UserHelper {
    private Faker faker = new Faker();
    public String userName;
    public String userEmail;
    public String userPassword;

    public String createRandomUser() throws JsonException{
        //create username
        userName = faker.name().firstName();
        //create payload
        JsonObject payload = new JsonObject();
        payload.addProperty("name", userName);
        payload.addProperty("job", faker.job().position());
        return payload.toString();
    }
    public String createRandomEmailPassword() throws JsonException{
        //create email
        userEmail = faker.internet().emailAddress();
        //create password
        userPassword = faker.internet().password();
        //create payload
        JsonObject payload = new JsonObject();
        payload.addProperty("email", userEmail);
        payload.addProperty("password",  userPassword);
        System.out.println(userEmail);
        System.out.println(userPassword);
        return payload.toString();
    }
    public String createNewJob(String newJob) throws JsonException{
        JsonObject payload = new JsonObject();
        payload.addProperty("job",newJob);
        return payload.toString();
    }
}
