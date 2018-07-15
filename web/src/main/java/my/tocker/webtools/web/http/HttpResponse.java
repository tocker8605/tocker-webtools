package my.tocker.webtools.web.http;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpResponse {

    private DataOutputStream dataOutputStream;

    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        dataOutputStream = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            if (url.endsWith(".css")) {
                headers.put("Content-Type", "text/css");
            }
            else if (url.endsWith(".js")) {
                headers.put("Content-Type", "application/javascript");
            }
            else {
                headers.put("Content-Type", "text/html;charset=utf-8");
            }
            headers.put("Content-Length", body.length + "");
            response200Header(body.length);
            responseBody(body);
            flushResponse();
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public void forwardBody(String body) {
        byte[] contents = body.getBytes();
        headers.put("Content-Type", "text/html;charset=utf-8");
        headers.put("Content-Length", contents.length + "");
        response200Header(contents.length);
        responseBody(contents);
        flushResponse();
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            processHeaders();
            dataOutputStream.writeBytes("\r\n");
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.writeBytes("\r\n");
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 302 Found \r\n");
            processHeaders();
            dataOutputStream.writeBytes("Location: " + redirectUrl + " \r\n");
            dataOutputStream.writeBytes("\r\n");
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void flushResponse() {
        try {
            dataOutputStream.flush();
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dataOutputStream.writeBytes(key + ": " + headers.get(key) + " \r\n");
            }
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
