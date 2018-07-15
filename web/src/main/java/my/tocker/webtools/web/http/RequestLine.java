package my.tocker.webtools.web.http;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import my.tocker.webtools.web.util.HttpRequestUtils;

@Slf4j
public class RequestLine {

    private HttpMethod method;

    private String path;

    private Map<String, String> params;

    public RequestLine(String requestLine) {
        log.debug("request line : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException(requestLine + " 이 형식에 맞지 않습니다.");
        }
        method = HttpMethod.valueOf(tokens[0]);
        if (method.isPost()) {
            path = tokens[1];
            params = new HashMap<>();
            return;
        }

        int index = tokens[1].indexOf("?");
        if (index == -1) {
            path = tokens[1];
            params = new HashMap<>();
        }
        else {
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(index+1));
        }
    }

    public HttpMethod getMethod() {
        return method;
    }

    public boolean isPost() {
        return method.isPost();
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
