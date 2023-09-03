package de.nordakademie.msadebuggerreplayer.core.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Header;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.springframework.http.HttpMethod;

import java.util.List;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestSendEvent.class, name = "RequestSendEvent"),
        @JsonSubTypes.Type(value = RequestReceiveEvent.class, name = "RequestReceiveEvent"),
})
public interface ReplayEvent {

    /**
     * apply the event to reconstruct the communication event
     */
    void apply(NextEventExecution exe);

    /**
     * This method is to be used to receive all the headers to communicate with the microservice
     * @return headers for the communication to the microservice
     */
    List<ReplayHeader> getHeader();

    /**
     * This method takes the Http Method to communicate with the microservice
     * @return Http Method for communication to the microservice
     */
    String getHttpMethod();

    String getRequestId();

    String getServiceName();

    void setHttpMethod(String value);

    void setServiceName(String value);

    void setRequestId(String value);

    void setLamportTime(int lamportTime);

    void setPath(String value);
}
