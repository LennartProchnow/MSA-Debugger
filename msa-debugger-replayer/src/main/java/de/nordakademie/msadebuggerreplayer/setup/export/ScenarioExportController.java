package de.nordakademie.msadebuggerreplayer.setup.export;

import de.nordakademie.msadebuggerreplayer.core.model.RequestSendEvent;
import de.nordakademie.msadebuggerreplayer.setup.export.model.Scenario;
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

//@Path("replay/setup/scenario")
//@ApplicationScoped
@RestController("/replay/setup/scenario")
public class ScenarioExportController {

    Logger logger = Logger.getLogger(ScenarioExportController.class);

    @Value("${msadebuggerreplayer.scenariorecorder.uri}")
    private String uriScenarioRecorder;

    @Autowired
    private ScenarioExportRegistry registry;



    @PostMapping("/{id}")
    public void exportScenario(@Nonnull String id) {
        var export = readScenario(id);
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
}
