package microserviceconfiguration.controller;

import microserviceconfiguration.configuration.DBSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/*
* @RefreshScope tells the spring to refresh the Beans - including its configurations
* This happens when the appropriate actuator endpoint is called
* */
@RestController
@RefreshScope
public class GreetingController {
    @Autowired
    Environment environment;
    @Value("${my.greeting}")
    private String message;
    @Value("${app.description: A default fallback description}")
    private String description;

    @Value("${list.values}")
    private List<String> listValues;

    /*SPeL: Spring Expression Language*/
    @Value("#{${db_map.values}}")
    private Map<String, String> dbValues;

    @Autowired
    private DBSettings dbSettings;

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok(message +
                "\n| " + description +
                "\n| " + listValues +
                "\n| " + dbValues +
                "\n| " + dbSettings.getPort());
    }

    @GetMapping("environment-details")
    public ResponseEntity<String> getEnvironmentDetails() {
        String envDetails = environment.toString();
        return new ResponseEntity(envDetails, HttpStatus.OK);
    }
}
