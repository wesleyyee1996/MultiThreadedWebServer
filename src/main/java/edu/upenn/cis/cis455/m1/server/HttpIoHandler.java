package edu.upenn.cis.cis455.m1.server;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.server.stubs.Request;

public class HttpIoHandler {
    
    // TODO:  make this actually send properly.  Beware the difference between Writers (and UTF-8) and Streams (and bytes)
    public static void sendException(Socket socket, Request request, HaltException halt) throws IOException {
        
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        
        writer.print("HTTP/1.1 404 Not Found\r\n");
        
        writer.flush();
        socket.getOutputStream().flush();
        writer.close();
        socket.getOutputStream().close();
    }
}
