package webServer;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Server {

    private HttpServer server;
    private Gson json;
    private int port = 9090;
    private String address = "127.0.0.1";

    // Constructor
    public Server() throws IOException {
        this.json = new GsonBuilder().create();
    }

    public void start() throws IOException {

        server = HttpServer.create(new InetSocketAddress(address, port), 0);
        server.createContext("/", testHandler());
        server.createContext("/createUser", createUser());
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

    private HttpHandler createUser() {

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
