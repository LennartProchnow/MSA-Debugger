package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.*;
import de.nordakademie.msadebuggerreplayer.setup.export.ScenarioExportRegistry;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Communication;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Event;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Scenario;
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

    public ScenarioQueue prepare(Scenario scenario){

        ScenarioQueue queue = new ScenarioQueue();

        List<ReplayEvent> requestsToReplay = new ArrayList<>();

        List<ResponseEvent> responseEvents = new ArrayList<>();

        scenario.getEvents().forEach(e -> {
            if (e.getType() == Communication.REQUEST) {
                if(isApplicableForReplay(e)){
                    requestsToReplay.add(convertScenarioEventToRequestEvent(e));
                }
            } else if (e.getType() == Communication.RESPONSE) {
                responseEvents.add(convertScenarioEventToResponseEvent(e));
            } else {
                throw new IllegalArgumentException(String.format("Events with the CommunicationType: %s are not supported", e.getType()));
            }
        });

        queue.setupEventsToReplay(requestsToReplay);
        queue.setupResponsesToReplay(responseEvents);

        return queue;
    }

    private ResponseEvent convertScenarioEventToResponseEvent(Event e) {
        ResponseEvent response = new ResponseEvent();
        response.setBody(e.getBody().body());
        e.getHeaders().forEach(header -> {
            switch (header.key()) {
                case ":status" -> response.setStatus(header.value());
                case "x-request-id" -> response.setRequestId(header.value());
                default -> {
                    //ToDo: add other header
                }
            }
        });
        return response;
    }

    /**
     * checks if the event is applicable for the replay, so it checks if the event contains a registered Microservice
     * @param e
     * @return
     */
    private boolean isApplicableForReplay(Event e) {
        return microserviceRegistry.isRegistered(e.getTarget(), e.getSource());
    }

    private ReplayEvent convertScenarioEventToRequestEvent(Event scenarioEvent){
        var targetName = scenarioEvent.getTarget();
        ServiceConfig targetConfig = microserviceRegistry.getServiceConfig(targetName);

        ReplayEvent replayEvent;

        if(targetConfig == null) {//no registered service is the target Service so executor could be target
            replayEvent = new RequestReceiveEvent(requestEventSink);
        } else {// service is registered and should be the target of the request
            replayEvent = new RequestSendEvent(this.sender);
            ((RequestSendEvent) replayEvent).setCommunicationBody(scenarioEvent.getBody().body());
            ((RequestSendEvent) replayEvent).setServiceConfig(targetConfig);

        }

        scenarioEvent.getHeaders().forEach(header -> {
            switch (header.key()) {
                case ":path" -> replayEvent.setPath(header.value());
                case ":method" -> replayEvent.setHttpMethod(header.value());
                case ":authority" -> replayEvent.setServiceName(header.value());
                case "x-communication-id" -> replayEvent.setRequestId(header.value());
                default -> {
                    //ToDo hier könnten Custom Header hinzugefügt werden
                }
            }
        });

        replayEvent.setLamportTime(scenarioEvent.getLamportTime());

        return replayEvent;
    }

    public void executeReplay(ScenarioQueue queue) {
        this.executeNextEvent(queue);
    }

    public void executeNextEvent(ScenarioQueue queue){
        var nextEvent = queue.getNextEvent();


        Consumer<ScenarioQueue> exec = null;
        if(!queue.isScenarioCompleted()) {
            exec = this::executeNextEvent;
        }
        NextEventExecution exe = new NextEventExecution(exec, queue);

        if(nextEvent instanceof RequestSendEvent) {
            sender.send(nextEvent);
            exe.apply();
        } else {
            requestEventSink.setNextEventExecution(exe);
        }
    }

}
