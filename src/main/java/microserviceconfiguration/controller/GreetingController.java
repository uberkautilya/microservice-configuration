package microserviceconfiguration.controller;

import microserviceconfiguration.configuration.DBSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {
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
        return ResponseEntity.ok(description +
                "\n| " + listValues +
                "\n| " + dbValues +
                "\n| " + dbSettings.getPort());
    }
}
