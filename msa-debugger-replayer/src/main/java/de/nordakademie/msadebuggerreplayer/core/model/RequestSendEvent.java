package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.RequestSender;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Header;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestSendEvent extends AbstractReplayEvent {

    private RequestSender sender;

    private String path;

    private HttpMethod httpMethod;


    //hier müsste dann irgendwie das Recorded Item mit reingehängt werden

    public RequestSendEvent(RequestSender sender) {
        this.sender = sender;
    }

    @Override
    public void apply(NextEventExecution exe) {
        sender.send(this);
        exe.apply();
    }

    @Override
    public String getCommunicationBody() {
        return null;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}