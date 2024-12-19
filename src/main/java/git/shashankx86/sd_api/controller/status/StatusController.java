package git.shashankx86.sd_api.controller.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import git.shashankx86.sd_api.config.AppConfig;
import java.time.Instant;
import java.time.Duration;

@RestController
@RequestMapping("/status")
public class StatusController {
    private static final long START_TIME = System.currentTimeMillis();
    
    @Autowired
    private AppConfig appConfig;
    
    @GetMapping
    public StatusResponse getStatus() {
        StatusResponse status = new StatusResponse();
        
        // Calculate uptime with seconds
        long uptime = System.currentTimeMillis() - START_TIME;
        Duration duration = Duration.ofMillis(uptime);
        String uptimeStr = String.format("%d days, %d hours, %d minutes, %d seconds",
            duration.toDays(),
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart());
            
        status.setUptime(uptimeStr);
        status.setTimestamp(Instant.now().toString());
        status.setVersion(appConfig.getVersion());
        status.setEnvironment(appConfig.getEnvironment());
        
        return status;
    }
}