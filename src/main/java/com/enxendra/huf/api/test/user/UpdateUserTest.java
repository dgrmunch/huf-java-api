package com.enxendra.huf.api.test.user;

import com.enxendra.huf.api.Constants;
import com.enxendra.huf.api.RequestOptions;
import com.enxendra.huf.api.model.user.User;
import com.enxendra.huf.api.model.user.UserResponse;
import com.enxendra.huf.api.service.UserService;
import com.enxendra.huf.api.test.ServiceTest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UpdateUserTest extends ServiceTest {

    static RequestOptions requestOptions = new RequestOptions(API_KEY, Constants.API_STR_TEST, USR_TOKEN);

    public static void main(String[] args) {
        UserService service = new UserService(requestOptions);

        JsonObject body = new JsonObject();

        User user = new User();
        user.setName("Diego Prueba2");
        user.setLogin("pepep@pepe.com");
        user.setEmail("pepe@asa.es");

        JsonParser parser = new JsonParser();
        body = parser.parse((new Gson()).toJson(user, User.class)).getAsJsonObject();

        try {
            UserResponse userResponse = service.updateUser(new Long(397), body);

            if (userResponse.getResponseCode().equals(Constants.OK)) {
                System.out.println("User updated");

            } else {
                System.out.println("ERROR " + userResponse.getErrorCode() + ": " + userResponse.getErrorMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
