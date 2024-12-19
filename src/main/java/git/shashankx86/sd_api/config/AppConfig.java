package git.shashankx86.sd_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${spring.application.environment}")
    private String environment;

    @Value("${spring.application.version:${version:unknown}}")
    private String version;

    public String getEnvironment() {
        return environment;
    }

    public String getVersion() {
        return version;
    }
}