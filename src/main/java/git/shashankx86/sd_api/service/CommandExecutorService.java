package git.shashankx86.sd_api.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Service
public class CommandExecutorService {
    public String executeCommand(String command, boolean sudo) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (sudo) {
                processBuilder.command("sudo", "sh", "-c", command);
            } else {
                processBuilder.command("sh", "-c", command);
            }
            
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            boolean completed = process.waitFor(10, TimeUnit.SECONDS);
            if (!completed) {
                process.destroyForcibly();
                return "Command timed out";
            }
            
            return output.toString().trim();
        } catch (Exception e) {
            return "Error executing command: " + e.getMessage();
        }
    }
}