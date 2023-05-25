package de.nordakademie.scenario.modell;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.smallrye.config.WithName;

public class RequestHeader {

    @JsonAlias(":authority")
    private String authority;

    @JsonAlias(":path")
    private String path;

    @JsonAlias(":method")
    private String method;

    @JsonAlias(":scheme")
    private String scheme;

    @JsonAlias("x-request-id")
    private String requestId;

    @JsonAlias("x-b3-traceid")
    private String traceId;

    @JsonAlias("x-b3-spanid")
    private String spanId;

    @JsonAlias("x-b3-parentspanid")
    private String parentSpanId;

    public RequestHeader(String authority, String path, String method, String scheme, String requestId, String traceId, String spanId, String parentSpanId) {
        this.authority = authority;
        this.path = path;
        this.method = method;
        this.scheme = scheme;
        this.requestId = requestId;
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
    }

    public RequestHeader() {
        //No args Constructor
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "authority='" + authority + '\'' +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", scheme='" + scheme + '\'' +
                ", requestId='" + requestId + '\'' +
                ", traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", parentSpanId='" + parentSpanId + '\'' +
                '}';
    }
}
