package de.nordakademie.msadebuggerreplayer.core.model;

import de.nordakademie.msadebuggerreplayer.setup.export.model.Header;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface ReplayEvent {

    /**
     * apply the event to reconstruct the communication event
     */
    void apply(NextEventExecution exe);

    /**
     * This method is to be used to get the payload for the communication to the microservice
     * @return the communication body
     */
    String getCommunicationBody();

    /**
     * This method is to be used to receive all the headers to communicate with the microservice
     * @return headers for the communication to the microservice
     */
    List<ReplayHeader> getHeader();

    /**
     * This method takes the Http Method to communicate with the microservice
     * @return Http Method for communication to the microservice
     */
    HttpMethod getHttpMethod();
}
