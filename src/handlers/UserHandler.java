package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import webinterfaces.UserFacadeInterface;

/**
 *
 * @author christophermortensen
 */
public class UserHandler implements HttpHandler {
    Gson transformer;
    UserFacadeInterface facade;
    ServerResponse sr;
    
    private String response;
    private int status;
    
    public UserHandler(Gson transformer, UserFacadeInterface facade, ServerResponse sr){
        this.transformer = transformer;
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

            case "POST":
                postRequest(he);
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
    
    private void getRequest(HttpExchange he) throws IOException{
        
    }
    
    private void postRequest(HttpExchange he) throws IOException{
        
    }
    
    private void deleteRequest(HttpExchange he) throws IOException{
        
    }
    
    private void putRequest(HttpExchange he) throws IOException{
        
    }
}
