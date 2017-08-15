package edu.upenn.cis.cis455;

import static edu.upenn.cis.cis455.webserver.HttpFramework.*;

public class WebServer {
    public static void main(String[] args) {
        port(8080);
        
        get("/test", (request, result) -> "Hello 555");
        
        awaitInitialization();
        
        System.out.println("Waiting to handle requests!");
    }

}
