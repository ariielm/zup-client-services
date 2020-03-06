package br.com.zup.zupclientservices.infrastructure.log;

import org.springframework.util.StopWatch;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

public class LogBuilder {

    private static final String X_REQUEST_ID = "X-Request-ID";

    private Map<String, String> logMessages;

    protected LogBuilder() {
        logMessages = new LinkedHashMap<>();
    }

    protected LogBuilder withRequest(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        this.withURI(request);
        this.withHttpMethod(request);
        this.withRemoteHost(request);
        this.withRequestId(request);

        return this;
    }

    protected LogBuilder withResponse(ServletResponse servletResponse) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        this.withHttpStatus(response);

        return this;
    }

    protected LogBuilder withTime(StopWatch watch) {
        this.logMessages.put("time", String.valueOf(watch.getTotalTimeMillis()));
        return this;
    }

    private void withURI(HttpServletRequest request) {
        this.logMessages.put("uri", request.getRequestURI());
    }

    private void withHttpMethod(HttpServletRequest request) {
        this.logMessages.put("http_method", request.getMethod());
    }

    private void withRemoteHost(HttpServletRequest request) {
        this.logMessages.put("remote_host", request.getRemoteHost());
    }

    private void withRequestId(HttpServletRequest request) {
        String requestId = request.getHeader(X_REQUEST_ID);

        if (isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }

        this.logMessages.put("request_id", requestId);
    }

    private void withHttpStatus(HttpServletResponse response) {
        this.logMessages.put("http_status", String.valueOf(response.getStatus()));
    }


    protected String build() {
        return this.logMessages.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(" "));
    }
}
