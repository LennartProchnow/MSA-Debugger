package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.core.RequestSender;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Header;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.springframework.http.HttpMethod;

import java.util.List;

public class RequestSendEvent extends AbstractReplayEvent {

    private String type = "RequestSendEvent";

    private RequestSender sender;

    private ServiceConfig config;

    private String body;

    public RequestSendEvent(RequestSender sender) {
        this.sender = sender;
    }

    public RequestSendEvent(){
        //default Constructor
    }

    @Override
    public void apply(NextEventExecution exe) {
        sender.send(this);
        exe.apply();
    }

    public void setServiceConfig(ServiceConfig config) {
        this.config = config;
    }

    public ServiceConfig getServiceConfig() {
        return config;
    }

    public String getCommunicationBody() {
        return body;
    }

    public void setCommunicationBody(String body) {
        this.body = body;
    }
}
