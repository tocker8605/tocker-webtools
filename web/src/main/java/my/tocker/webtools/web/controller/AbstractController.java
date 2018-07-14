package my.tocker.webtools.web.controller;

import my.tocker.webtools.web.http.HttpMethod;
import my.tocker.webtools.web.http.HttpRequest;
import my.tocker.webtools.web.http.HttpResponse;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        HttpMethod method = request.getMethod();

        if (method.isPost()) {
            doPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) {
    }

    protected void doGet(HttpRequest request, HttpResponse response) {
    }
}