package git.shashankx86.sd_api.controller.version;

public class VersionResponse {
    private String version;
    private String features;

    public VersionResponse(String output) {
        String[] parts = output.split("\n", 2);
        this.version = parts[0];
        this.features = parts.length > 1 ? parts[1] : "";
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}