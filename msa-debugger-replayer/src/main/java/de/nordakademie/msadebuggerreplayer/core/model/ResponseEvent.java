package de.nordakademie.msadebuggerreplayer.core.model;

public class ResponseEvent {

    private String requestId;

    private String status;

    private String body;

    public ResponseEvent() {

    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
