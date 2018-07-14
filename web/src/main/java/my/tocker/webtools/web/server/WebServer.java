package my.tocker.webtools.web.server;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class WebServer {

    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        }
        else {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server Started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                // todo: do anything

            }
        }
        catch (Exception ex) {
            log.error("Web Application Server Start failure", ex);
        }

    }

}
