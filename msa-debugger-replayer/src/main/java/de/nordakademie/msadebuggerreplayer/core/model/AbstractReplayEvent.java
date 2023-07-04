package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.ScenarioQueue;

import java.util.List;

public abstract class AbstractReplayEvent implements ReplayEvent {

    private int lamportTime;

    private String requestId;

    private boolean isCompleted;

    private String serviceName;

    private List<ReplayHeader> header;

    private String body;

    private ScenarioQueue queue;

    public boolean isCompleted () {
        return true;
    }

    public ScenarioQueue getQueue(){
        return queue;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public List<ReplayHeader> getHeader() {
        return header;
    }

    @Override
    public String getCommunicationBody() {
        return body;
    }

    public void setCommunicationBody(String body) {
        this.body = body;
    }

    public int getLamportTime() {
        return lamportTime;
    }

    public void setLamportTime(int lamportTime) {
        this.lamportTime = lamportTime;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setHeader(List<ReplayHeader> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
