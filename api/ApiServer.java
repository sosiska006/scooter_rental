package api;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class ApiServer {
    public static void main(String[] args) throws Exception{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/health", exchange -> {
            byte[] answer = "hello world)".getBytes();
            exchange.sendResponseHeaders(200, answer.length);
            exchange.getResponseBody().write(answer);
            exchange.close();
        });
        server.start();
        System.out.println("Server started on port 8080");
    }

}
 