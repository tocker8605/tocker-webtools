package my.tocker.webtools.web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

import my.tocker.webtools.web.controller.Controller;
import my.tocker.webtools.web.http.HttpRequest;
import my.tocker.webtools.web.http.HttpResponse;
import my.tocker.webtools.web.http.HttpSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            // session cookie set
            if (request.getCookies().getCookie(HttpSessions.SESSION_ID_NAME) == null) {
                response.addHeader("Set-Cookie", HttpSessions.SESSION_ID_NAME + "=" + UUID.randomUUID());
            }

            // controller matching
            Controller controller = RequestMapping.getController(request.getPath());

            if (controller == null) {
                String path = getDefaultPath(request.getPath());
                response.forward(path);
            }
            else {
                controller.service(request, response);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    // TODO: auto config화
    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
