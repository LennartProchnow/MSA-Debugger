package de.nordakademie.msadebuggerreplayer.setup.export.model;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private Communication type;

    private int lamportTime;

    private List<Header> headers = new ArrayList<>();

    private EventBody body;

    private String target;

    public Event(Communication type, int lamportTime, List<Header> headers, EventBody body, String target) {
        this.type = type;
        this.lamportTime = lamportTime;
        this.headers = headers;
        this.body = body;
        this.target = target;
    }

    public Event() {
    }

    public Communication getType() {
        return type;
    }

    public void setType(Communication type) {
        this.type = type;
    }

    public int getLamportTime() {
        return lamportTime;
    }

    public void setLamportTime(int lamportTime) {
        this.lamportTime = lamportTime;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public EventBody getBody() {
        return body;
    }

    public void setBody(EventBody body) {
        this.body = body;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
