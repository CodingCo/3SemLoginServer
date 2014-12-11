package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.UserInfo;
import webinterfaces.UserFacadeInterface;

public class UserHandler implements HttpHandler {

    Gson gson;
    UserFacadeInterface facade;
    ServerResponse sr;

    private String response;
    private int status;

    public UserHandler(Gson gson, UserFacadeInterface facade, ServerResponse sr) {
        this.gson = gson;
        this.facade = facade;
        this.sr = sr;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        String method = he.getRequestMethod().toUpperCase();

        String userRequest = he.getRequestURI().toString().substring(7);

        response = "";
        status = 0;

        switch (method) {
            case "GET":
                getRequest(he);
                break;

            case "POST": // Both user validation and user creation comes with a post 

                if (userRequest.equals("validateUser")) {
                    validateUserCredentials(he);
                } else {
                    createUser(he);
                }

                break;

            case "DELETE":
                deleteRequest(he);
                break;

            case "PUT":
                putRequest(he);
                break;
        }
        he.getResponseHeaders().add("Content-Type", "application/json");
        sr.sendMessage(he, status, response);
    }

    private void getRequest(HttpExchange he) throws IOException {

    }

    private void putRequest(HttpExchange he) throws IOException {
    }

    private void deleteRequest(HttpExchange he) throws IOException {
    }

    private void validateUserCredentials(HttpExchange he) throws IOException {
        String userAsJson = readFromJson(he);

        if (facade.validateUser(userAsJson)) {
            String username = gson.fromJson(userAsJson, UserInfo.class).getUsername();
            String userFromDb = facade.getOneUserAsJSON(username);
            
            // Wont send hashed password back
            UserInfo user = gson.fromJson(userFromDb, UserInfo.class);
            user.setPassword("");
            
            response = gson.toJson(user);
            status = 200;

        } else {
            response = "{\"err\": \"true\", \"msg\":\"Password or username not correct!\"}";
            status = 401;
            System.err.println("Someone tried to log in, but failed");
        }

    }

    private void createUser(HttpExchange he) throws IOException {
        System.err.println("Trying to create user");
        UserInfo createdUser = facade.createUserFromJSON(readFromJson(he));

        if (createdUser != null) {
            status = 200;
            response = gson.toJson(createdUser);
        } else {
            status = 409;
            response = "{\"err\": \"true\", \"msg\":\"Username or email already exists!\"}";
        }

    }

    private String readFromJson(HttpExchange he) throws IOException {
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        input = in.readLine();

        return input;
    }

}
