package git.shashankx86.sd_api.controller.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import git.shashankx86.sd_api.service.CommandExecutorService;

public abstract class BaseVersionController {
    @Autowired
    protected CommandExecutorService commandExecutor;
    
    protected abstract boolean isSudo();
    
    @GetMapping("/version")
    public VersionResponse getVersion() {
        String output = commandExecutor.executeCommand("systemd --version", isSudo());
        return new VersionResponse(output);
    }
}