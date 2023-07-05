package de.nordakademie.msadebuggerreplayer.core.model;

public class ResponseEvent {

    private String requestId;

    private String body;

    public ResponseEvent() {

    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getCommunicationBody() {
        return body;
    }

    public void setCommunicationBody(String body) {
        this.body = body;
    }
}
