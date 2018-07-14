package my.tocker.webtools.web.controller;

import my.tocker.webtools.web.http.HttpRequest;
import my.tocker.webtools.web.http.HttpResponse;

public class DummyController extends AbstractController{

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.sendRedirect("/index.html");
    }
}
