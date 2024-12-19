package git.shashankx86.sd_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Service
public class CommandExecutorService {
    @Autowired
    private PrivilegeHandler privilegeHandler;

    public String executeCommand(String command, boolean sudo) throws SecurityException {
        if (sudo && !privilegeHandler.isSudoAvailable()) {
            throw new SecurityException("Sudo access is not available");
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (sudo) {
                processBuilder.command("sudo", "-n", "sh", "-c", command);
            } else {
                processBuilder.command("sh", "-c", command);
            }
            
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            StringBuilder error = new StringBuilder();
            
            try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                
                String line;
                while ((line = stdInput.readLine()) != null) {
                    output.append(line).append("\n");
                }
                while ((line = stdError.readLine()) != null) {
                    error.append(line).append("\n");
                }
            }
            
            boolean completed = process.waitFor(10, TimeUnit.SECONDS);
            if (!completed) {
                process.destroyForcibly();
                throw new RuntimeException("Command timed out");
            }
            
            if (process.exitValue() != 0) {
                throw new RuntimeException("Command failed: " + error.toString().trim());
            }
            
            return output.toString().trim();
        } catch (Exception e) {
            throw new RuntimeException("Error executing command: " + e.getMessage());
        }
    }
}