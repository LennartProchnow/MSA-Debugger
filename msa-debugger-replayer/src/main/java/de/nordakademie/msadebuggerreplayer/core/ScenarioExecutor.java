package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.*;
import de.nordakademie.msadebuggerreplayer.setup.export.ScenarioExportRegistry;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Communication;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Event;
import de.nordakademie.msadebuggerreplayer.setup.register.MicroserviceRegistry;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class ScenarioExecutor {

    @Autowired
    private RequestSender sender;

    @Autowired
    private ScenarioExportRegistry scenarioExportRegistry;

    @Autowired
    private MicroserviceRegistry microserviceRegistry;

    @Autowired
    private RequestEventSink requestEventSink;

    public ScenarioQueue prepare(String scenarioId){
        // Hier dann noch die Queue preparen
        // die ScenarioEvents werden überführt in ReplayEvents
        // die Events werden in eine Execution Queue geschrieben
        // die Execution Queue wird dann zurück gegeben
        var scenario = scenarioExportRegistry.getScenario(scenarioId);

        ScenarioQueue queue = new ScenarioQueue();

        List<ReplayEvent> requestsToReplay = new ArrayList<>();

        List<ResponseEvent> responseEvents = new ArrayList<>();

        scenario.getEvents().forEach(e -> {
            if (e.getType() == Communication.REQUEST) {
                requestsToReplay.add(convertScenarioEventToRequestEvent(e));
            } else if (e.getType() == Communication.RESPONSE) {
                //der hier müsste dann eigentlich in einer eigenen Liste abgefackelt werden
                throw new UnsupportedOperationException("not implemented yet");
            } else {
                throw new IllegalArgumentException(String.format("Events with the CommunicationType: %s are not supported", e.getType()));
            }
        });

        queue.setupEventsToReplay(requestsToReplay);
        queue.setupResponsesToReplay(responseEvents);

        return queue;
    }

    private ReplayEvent convertScenarioEventToRequestEvent(Event scenarioEvent){
        var targetName = scenarioEvent.getTarget();
        ServiceConfig targetConfig = microserviceRegistry.getServiceConfig(targetName);
        //ToDo das betrachtet jetzt erstmal keine Kommunikation, von Services die garnicht abgespielt werden soll
        // dann soo -> ServiceConfig sourceConfig = microserviceRegistry.getServiceConfig(source);

        if(targetConfig == null) {//no registered service is the target Service so executor could be target
            return createRequestSendEvent(scenarioEvent);
        } else {
            return createRequestReceiveEvent(scenarioEvent);
        }
    }

    private ReplayEvent createRequestReceiveEvent(Event scenarioEvent) {
        RequestReceiveEvent event = new RequestReceiveEvent(requestEventSink);
        scenarioEvent.getHeaders().forEach(header -> {
            switch (header.key()) {
                case ":path":
                    event.setPath(header.value());
                    break;
                case ":method":
                    event.setHttpMethod(HttpMethod.valueOf(header.value()));
                    break;
                case ":authority":
                    event.setServiceName(header.value());
                    break;
                case "x-request-id":
                    event.setRequestId(header.value());
                    break;
                default:
            }
        });
        event.setCommunicationBody(scenarioEvent.getBody().body());
        event.setLamportTime(scenarioEvent.getLamportTime());

        return event;

    }

    private RequestSendEvent createRequestSendEvent(Event scenarioEvent){
        RequestSendEvent event = new RequestSendEvent(this.sender);
        scenarioEvent.getHeaders().forEach(header -> {
            switch (header.key()) {
                case ":path":
                    event.setPath(header.value());
                    break;
                case ":method":
                    event.setHttpMethod(HttpMethod.valueOf(header.value()));
                    break;
                case ":authority":
                    event.setServiceName(header.value());
                    break;
                case "x-request-id":
                    event.setRequestId(header.value());
                    break;
                default:
            }
        });
        event.setCommunicationBody(scenarioEvent.getBody().body());
        event.setLamportTime(scenarioEvent.getLamportTime());

        return event;
    }

    public void executeReplay(ScenarioQueue queue) {
        this.executeNextEvent(queue);
    }

    public void executeNextEvent(ScenarioQueue queue){
        var nextEvent = queue.getNextEvent();

        Consumer<ScenarioQueue> exec = this::executeNextEvent;

        NextEventExecution exe = new NextEventExecution(exec, queue);

        nextEvent.apply(exe);
    }

}
