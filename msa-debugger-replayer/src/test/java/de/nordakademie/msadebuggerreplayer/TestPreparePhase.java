package de.nordakademie.msadebuggerreplayer;

import de.nordakademie.msadebuggerreplayer.core.ScenarioExecutor;
import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import de.nordakademie.msadebuggerreplayer.core.model.RequestReceiveEvent;
import de.nordakademie.msadebuggerreplayer.core.model.RequestSendEvent;
import de.nordakademie.msadebuggerreplayer.setup.export.model.*;
import de.nordakademie.msadebuggerreplayer.setup.register.MicroserviceRegistry;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestPreparePhase {

    @Autowired
    private ScenarioExecutor executor;

    @Autowired
    private MicroserviceRegistry registry;

    private String targetService = "example-service";
    private String path = "/example/getRequest";
    private String method = "POST";
    private String requestId = UUID.randomUUID().toString();
    private ServiceConfig config = new ServiceConfig(targetService, "localhost:8080");

    private void before(){
        registry.register(targetService, config);
    }

    @Test
    public void testSendRequestConversion(){

        before();

        Header pathHeader = new Header(":path", path);
        Header methodHeader = new Header(":method", method);
        Header authorityHeader = new Header(":authority", targetService);
        Header requestIdHeader = new Header("x-request-id", requestId);

        String body = "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}";

        EventBody eventBody = new EventBody(ContentType.APPLICATION_JSON, "1234", body);

        Event sendRequest = new Event(Communication.REQUEST, 0, Arrays.asList(pathHeader, methodHeader, authorityHeader, requestIdHeader), eventBody, targetService);

        Scenario scenario = new Scenario("1234", "TestScenario", Collections.singletonList(sendRequest));

        var queue = executor.prepare(scenario);

        assertNotNull(queue);

        List<ReplayEvent> eventsToReplay = queue.getEventsToReplay();

        assertEquals(1, eventsToReplay.size());

        ReplayEvent replayEvent = eventsToReplay.get(0);

        assertTrue(replayEvent instanceof RequestSendEvent);

        RequestSendEvent event = (RequestSendEvent) replayEvent;

        assertEquals(targetService, event.getServiceName());
        assertEquals(path, event.getPath());
        assertEquals(HttpMethod.valueOf(method), event.getHttpMethod());
        assertEquals(requestId, event.getRequestId());
        assertEquals(body, event.getCommunicationBody());

        assertEquals(config, event.getServiceConfig());

    }

    @Test
    public void testReceiveRequestConversion(){

        before();

        String otherTargetService = "other-example-service";

        Header pathHeader = new Header(":path", path);
        Header methodHeader = new Header(":method", method);
        Header authorityHeader = new Header(":authority", otherTargetService);
        Header requestIdHeader = new Header("x-request-id", requestId);

        String body = "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}";

        EventBody eventBody = new EventBody(ContentType.APPLICATION_JSON, "1234", body);

        Event sendRequest = new Event(Communication.REQUEST, 0, Arrays.asList(pathHeader, methodHeader, authorityHeader, requestIdHeader), eventBody, otherTargetService);

        Scenario scenario = new Scenario("1234", "TestScenario", Collections.singletonList(sendRequest));

        var queue = executor.prepare(scenario);

        assertNotNull(queue);

        List<ReplayEvent> eventsToReplay = queue.getEventsToReplay();

        assertEquals(1, eventsToReplay.size());

        ReplayEvent replayEvent = eventsToReplay.get(0);

        assertTrue(replayEvent instanceof RequestReceiveEvent);

        var event = (RequestReceiveEvent) replayEvent;

        assertEquals(otherTargetService, event.getServiceName());
        assertEquals(path, event.getPath());
        assertEquals(HttpMethod.valueOf(method), event.getHttpMethod());
        assertEquals(requestId, event.getRequestId());

    }


}
