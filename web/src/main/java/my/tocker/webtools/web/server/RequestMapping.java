package my.tocker.webtools.web.server;

import my.tocker.webtools.web.controller.Controller;
import my.tocker.webtools.web.controller.DummyController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/hello", new DummyController());
//        controllers.put("/user/login", new LoginController());
//        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }
}
