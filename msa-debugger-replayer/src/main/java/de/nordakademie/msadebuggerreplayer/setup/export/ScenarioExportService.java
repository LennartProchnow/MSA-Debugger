package de.nordakademie.msadebuggerreplayer.setup.export;

import de.nordakademie.msadebuggerreplayer.setup.export.model.*;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import jakarta.annotation.Nonnull;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@RestController("/replay/setup/scenario")
public class ScenarioExportService {

    Logger logger = Logger.getLogger(ScenarioExportService.class);

    @Value("${msadebuggerreplayer.scenariorecorder.uri}")
    private String uriScenarioRecorder;

    @Autowired
    private ScenarioExportRegistry registry;



    @PostMapping("/{id}")
    public void exportScenario(@Nonnull String id) {

        //var export = readScenario(id);
        var export = mockReadScenario(id);
        if(export == null) {
            logger.warn(String.format("No scenario with the id: %s found", id));
        } else {
            logger.info(String.format("Scenario with the id: %s found", id));
            registry.registerScenario(export);
        }
    }

    private Scenario readScenario(String id){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        String uri = uriScenarioRecorder + "/" + id;

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<Scenario> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Scenario.class);

        return responseEntity.getBody();
    }

    private Scenario mockReadScenario(String id) {

        // Send Request
        String targetService = "example-service";
        String path = "example/doPostRequest";
        String method = "POST";
        String requestId = UUID.randomUUID().toString();

        Header pathHeader = new Header(":path", path);
        Header methodHeader = new Header(":method", method);
        Header authorityHeader = new Header(":authority", targetService);
        Header requestIdHeader = new Header("x-request-id", requestId);

        String body = "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}";

        EventBody eventBody = new EventBody(ContentType.APPLICATION_JSON, "1234", body);

        Event sendRequest = new Event(Communication.REQUEST, 0, Arrays.asList(pathHeader, methodHeader, authorityHeader, requestIdHeader), eventBody, targetService);

        // Receive Request
        String otherTargetService = "other-example-service";
        String otherPath = "example/send/getRequest";
        String otherMethod = "GET";
        String otherRequestId = UUID.randomUUID().toString();

        Header otherPathHeader = new Header(":path", otherPath);
        Header otherMethodHeader = new Header(":method", otherMethod);
        Header otherAuthorityHeader = new Header(":authority", otherTargetService);
        Header otherRequestIdHeader = new Header("x-request-id", otherRequestId);

        String otherBody = "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}";

        EventBody otherEventBody = new EventBody(ContentType.APPLICATION_JSON, "1234", otherBody);

        Event receiveRequest = new Event(Communication.REQUEST, 1, Arrays.asList(otherPathHeader, otherMethodHeader, otherAuthorityHeader, otherRequestIdHeader), otherEventBody, otherTargetService);

        // Send Response
        String responseTargetService = "other-example-service";
        String contentType = "application/json";

        String responseBody = "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}";

        EventBody responseEventBody = new EventBody(ContentType.APPLICATION_JSON, "12345", responseBody);

        Event responseEvent = new Event(Communication.RESPONSE, 2, Collections.EMPTY_LIST, responseEventBody, responseTargetService);

        return new Scenario("1234", "TestScenario", Arrays.asList(sendRequest, receiveRequest, responseEvent));

    }
}
