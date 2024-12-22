package git.shashankx86.sd_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class EnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);
    
    @Bean
    public Properties envProperties() {
        Properties props = new Properties();
        String envPath = System.getProperty("env.path", ".env");
        File envFile = new File(envPath);

        if (envFile.exists()) {
            try (FileInputStream fis = new FileInputStream(envFile)) {
                props.load(fis);
            } catch (Exception e) {
                logger.warn("Could not load .env file: {}", e.getMessage());
            }
        }

        // Set default values if not present
        setDefaultIfMissing(props, "JWT_SECRET", "default_jwt_secret_key_for_testing");
        setDefaultIfMissing(props, "ADMIN_USERNAME", "admin");
        setDefaultIfMissing(props, "ADMIN_PASSWORD", "admin");
        setDefaultIfMissing(props, "JWT_EXPIRATION", "86400000");

        return props;
    }

    private void setDefaultIfMissing(Properties props, String key, String defaultValue) {
        if (!props.containsKey(key)) {
            props.setProperty(key, defaultValue);
            logger.info("Using default value for {}", key);
        }
    }
}