package de.nordakademie.msadebuggerreplayer.core;

import de.nordakademie.msadebuggerreplayer.core.model.ReplayEvent;
import de.nordakademie.msadebuggerreplayer.core.model.RequestSendEvent;
import de.nordakademie.msadebuggerreplayer.setup.register.MicroserviceRegistry;
import de.nordakademie.msadebuggerreplayer.setup.register.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestSender {

    @Autowired
    private MicroserviceRegistry configRegistry;

    public void send(ReplayEvent event) {

        // ToDo: das tatsächliche Senden müsste eigentlich asynchron sein
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        RequestSendEvent request = (RequestSendEvent) event;

        ServiceConfig config = configRegistry.getServiceConfig(request.getServiceName());

        String url = config.getFullQualifiedPath() + "/" + request.getPath();

        event.getHeader().forEach(h -> headers.add(h.key(), h.value()));
        HttpEntity<String> requestEntity = new HttpEntity<>(event.getCommunicationBody(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, event.getHttpMethod(), requestEntity, String.class);
    }
}
