package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import de.nordakademie.msadebuggerreplayer.core.model.RequestSendEvent;
import de.nordakademie.msadebuggerreplayer.setup.register.MicroserviceRegistry;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class RequestSender {

    @Autowired
    private MicroserviceRegistry configRegistry;

    @Async
    public void send(ReplayEvent event) {
        // ToDo: das tatsächliche Senden müsste eigentlich asynchron sein
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        RequestSendEvent request = (RequestSendEvent) event;

        ServiceConfig config = configRegistry.getServiceConfig(request.getServiceName());

        String uri = "http://" + config.getFullQualifiedPath() + "/" + request.getPath();

        event.getHeader().forEach(h -> headers.add(h.key(), h.value()));
        HttpEntity<String> requestEntity = new HttpEntity<>(request.getCommunicationBody(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.valueOf(request.getHttpMethod()), requestEntity, String.class);
    }

}
