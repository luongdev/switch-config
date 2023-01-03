package luongdev.switchconfig.webapi.configuration;

import luongdev.cqrs.Bus;
import luongdev.switchconfig.configuration.acl.commands.GenerateAccessControlXmlCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/switch/config")
public class ConfigurationController {

    private final Bus bus;

    public ConfigurationController(Bus bus) {
        this.bus = bus;
    }

    @GetMapping("/reload")
    public ResponseEntity<Boolean> reload(@RequestParam(name = "name") String name) {
        if ("acl".equalsIgnoreCase(name)) {
            bus.execute(new GenerateAccessControlXmlCommand());
        }
        
        return ResponseEntity.ok(true);
    }
}
