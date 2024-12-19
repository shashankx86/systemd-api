package git.shashankx86.sd_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

@Service
public class PrivilegeHandler {
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeHandler.class);
    
    @Value("${app.security.sudo.enabled:false}")
    private boolean sudoEnabled;
    
    private boolean sudoAvailable = false;

    @PostConstruct
    public void init() {
        if (sudoEnabled) {
            checkPrivileges();
        } else {
            logger.info("Sudo features are disabled by configuration");
        }
    }
    
    private void checkPrivileges() {
        if (!isRoot()) {
            logger.warn("Application is not running with elevated privileges. Some features will be limited.");
        }
        checkSudoAccess();
    }

    private boolean isRoot() {
        return System.getProperty("user.name").equals("root");
    }
    
    private void checkSudoAccess() {
        try {
            ProcessBuilder pb = new ProcessBuilder("sudo", "-n", "true");
            Process p = pb.start();
            int exitCode = p.waitFor();
            sudoAvailable = (exitCode == 0);
            if (!sudoAvailable) {
                logger.warn("Sudo access is not available. Some features will be limited.");
            } else {
                logger.info("Sudo access is available");
            }
        } catch (Exception e) {
            logger.error("Failed to check sudo access: {}", e.getMessage());
            sudoAvailable = false;
        }
    }
    
    public boolean isSudoAvailable() {
        return sudoEnabled && sudoAvailable;
    }
}