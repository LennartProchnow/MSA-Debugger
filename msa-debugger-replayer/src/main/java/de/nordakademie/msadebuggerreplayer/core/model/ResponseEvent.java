package de.nordakademie.msadebuggerreplayer.core.model;

public class ResponseEvent {

    private String requestId;

    public ResponseEvent() {

    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return this.requestId;
    }

}
