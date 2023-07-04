package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.RequestEventSink;
import de.nordakademie.msadebuggerreplayer.core.RequestSender;
import org.springframework.http.HttpMethod;

public class RequestReceiveEvent extends AbstractReplayEvent {

    private RequestEventSink sink;

    private String path;

    private HttpMethod httpMethod;

    private String responseBody;

    public RequestReceiveEvent(RequestEventSink sink) {
        this.sink = sink;
    }

    @Override
    public void apply(NextEventExecution exe) {
        sink.setNextEventExecution(exe);
    }

    @Override
    public String getCommunicationBody() {
        return responseBody;
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
