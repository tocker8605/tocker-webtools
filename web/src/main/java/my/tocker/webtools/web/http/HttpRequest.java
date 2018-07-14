package my.tocker.webtools.web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import my.tocker.webtools.web.util.HttpRequestUtils;
import my.tocker.webtools.web.util.IOUtils;

@Slf4j
public class HttpRequest {

    private Map<String, String> headers = new HashMap<>();

    private Map<String, String> params;

    private RequestLine requestLine;

    public HttpRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if (line == null) {
                return;
            }

            requestLine = new RequestLine(line);

            while(!line.equals("")) {
                line = br.readLine();
                log.debug("header : {}", line);
                if (!line.equals("")){
                    String[] tokens = line.split(":");
                    headers.put(tokens[0], tokens[1].trim());
                }
            }

            if (requestLine.isPost()) {
                String body = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                params = HttpRequestUtils.parseQueryString(body);
            } else {
                params = requestLine.getParams();
            }
        } catch (IOException io) {
            log.error(io.getMessage());
        }
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public HttpCookie getCookies() {
        return new HttpCookie(getHeader("Cookie"));
    }

    public HttpSession getSession() {
        return HttpSessions.getSession(getCookies().getCookie("JSESSIONID"));
    }
}
