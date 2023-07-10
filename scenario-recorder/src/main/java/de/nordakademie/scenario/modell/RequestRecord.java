package de.nordakademie.scenario.modell;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * This class is the representation of a recorded request between two services in the cluster
 */
public class RequestRecord {

    //ToDo: this header should be in a key value construct - so the communication is easy to extend
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

    @JsonAlias("x-communication-id")
    private String communicationId;

    @JsonAlias("x-source-service-name")
    private String source;

    @JsonAlias("body")
    private String body;

    public RequestRecord(String authority, String path, String method, String scheme, String requestId, String traceId, String spanId, String parentSpanId, String body) {
        this.authority = authority;
        this.path = path;
        this.method = method;
        this.scheme = scheme;
        this.requestId = requestId;
        this.traceId = traceId;
        this.spanId = spanId;
        this.parentSpanId = parentSpanId;
        this.body = body;
    }

    public RequestRecord() {
        //empty Default Constructor
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommunicationId() {
        return communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
