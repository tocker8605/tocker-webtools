package my.tocker.webtools.web.controller;

import my.tocker.webtools.web.http.HttpRequest;
import my.tocker.webtools.web.http.HttpResponse;

public interface Controller {
    void service(HttpRequest request, HttpResponse response);
}
