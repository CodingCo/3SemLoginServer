package webServer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.UserFacade;
import handlers.ServerResponse;
import handlers.UserHandler;
import webinterfaces.UserFacadeInterface;

public class Server {

    private HttpServer server;
    private Gson gson;
    private int port = 9090;
    private String address = "127.0.0.1";
    
    private UserFacadeInterface userFacade;
    private ServerResponse sr;
    private HashCreator hash;
    
    // Constructor
    public Server() throws IOException {
        this.gson = new GsonBuilder().create(); 
        this.hash = new HashCreator();
        this.userFacade = new UserFacade(gson);
        this.sr = new ServerResponse();
    }

    public void start() throws IOException {

        server = HttpServer.create(new InetSocketAddress(address, port), 0);
        server.createContext("/", testHandler());
        server.createContext("/validateUser", new UserHandler(gson, userFacade, sr));
        server.setExecutor(null);
        server.start();
        System.out.println("Server startet on: " + server.getAddress());
        
    }

    private HttpHandler testHandler() {
        return new HttpHandler() {

            @Override
            public void handle(HttpExchange he) throws IOException {

                String contentType = "text/html";
                String response = "Hello World, again!";
                byte[] bytesToSend = response.getBytes();

                Headers h = he.getResponseHeaders();
                h.add("Content-Type", contentType);
                h.add("charset", "UTF-8");

                he.sendResponseHeaders(200, 0);
                try (OutputStream os = he.getResponseBody()) {
                    os.write(bytesToSend, 0, bytesToSend.length);
                }

            }

        };
    }

}
