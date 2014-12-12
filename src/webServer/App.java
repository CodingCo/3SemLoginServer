package webServer;

public class App {

    public static void main(String[] args) {

        try {
            Server server = new Server();
            server.start();
            
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }

    }

}
