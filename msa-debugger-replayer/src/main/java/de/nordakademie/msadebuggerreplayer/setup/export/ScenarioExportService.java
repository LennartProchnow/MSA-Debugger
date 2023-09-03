package de.nordakademie.msadebuggerreplayer.setup.export;

import de.nordakademie.msadebuggerreplayer.setup.export.model.*;
import jakarta.annotation.Nonnull;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

@Service
public class ScenarioExportService {

    Logger logger = Logger.getLogger(ScenarioExportService.class);

    @Value("${msadebuggerreplayer.scenariorecorder.uri}")
    private String uriScenarioRecorder;

    @Autowired
    private ScenarioExportRegistry registry;


    public void exportScenario(@Nonnull String id) {

        var export = readScenario(id);
        //var export = mockReadScenario(id);
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

        String uri = "http://" + uriScenarioRecorder + "/" + id;

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<Scenario> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, Scenario.class);

        return responseEntity.getBody();
    }

}
