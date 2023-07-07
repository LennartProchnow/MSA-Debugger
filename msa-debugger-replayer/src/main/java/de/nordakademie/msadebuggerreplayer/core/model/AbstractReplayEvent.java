package de.nordakademie.msadebuggerreplayer.core.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.nordakademie.msadebuggerreplayer.core.ScenarioQueue;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReplayEvent implements ReplayEvent {

    private int lamportTime;

    private String requestId;

    private boolean isCompleted;

    private String serviceName;

    private List<ReplayHeader> header = new ArrayList<>();

    private String path;

    private String httpMethod;

    @Override
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

    @Override
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    @Override
    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
