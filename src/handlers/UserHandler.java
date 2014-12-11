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
        response = "";
        status = 0;

        switch (method) {
            case "GET":
                getRequest(he);
                break;

            case "POST": // validates user
                postRequest(he);
                break;

            case "DELETE":
                deleteRequest(he);
                break;

            case "PUT": // creates user
                putRequest(he);
                break;
        }
        he.getResponseHeaders().add("Content-Type", "application/json");
        sr.sendMessage(he, status, response);
    }

    private void getRequest(HttpExchange he) throws IOException {

    }

    private void postRequest(HttpExchange he) throws IOException {
        String userAsJson = readFromJson(he);
        System.err.println("Inside postRequest");
        
        if (facade.validateUser(userAsJson)) {
            System.err.println("User exists");
            // user exists!
            //UserInfo user = gson.fromJson(userAsJson, UserInfo.class);
            //String userFromDb = facade.getOneUserAsJSON(user.getUsername());
            response = "{}";
            status = 200;

        } else {
            response = "Couldn't find user";
            status = 404;
            System.err.println("Send something");
        }

    }

    private void deleteRequest(HttpExchange he) throws IOException {

    }

    private void putRequest(HttpExchange he) throws IOException {

        UserInfo createdUser = facade.createUserFromJSON(readFromJson(he));

        if (createdUser != null) {
            status = 200;
            response = gson.toJson(createdUser);
        } else {
            status = 500;
            response = "Couldn't create requested credentials";
        }

    }

    private String readFromJson(HttpExchange he) throws IOException {
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        input = in.readLine();

        return input;
    }

}
