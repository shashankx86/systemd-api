package git.shashankx86.sd_api.controller.status;

import java.time.Instant;

public class StatusResponse {
    private String uptime;
    private String timestamp;
    private String version;
    private String environment;

    // Getters and setters
    public String getUptime() { return uptime; }
    public void setUptime(String uptime) { this.uptime = uptime; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
}