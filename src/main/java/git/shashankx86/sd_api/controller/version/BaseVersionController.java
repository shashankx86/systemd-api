package git.shashankx86.sd_api.controller.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import git.shashankx86.sd_api.service.CommandExecutorService;
import git.shashankx86.sd_api.service.PrivilegeHandler;
import git.shashankx86.sd_api.common.response.ErrorResponse;

public abstract class BaseVersionController {
    @Autowired
    protected CommandExecutorService commandExecutor;
    
    @Autowired
    protected PrivilegeHandler privilegeHandler;
    
    protected abstract boolean isSudo();
    
    @GetMapping("/version")
    public ResponseEntity<?> getVersion() {
        try {
            String output = commandExecutor.executeCommand("systemd --version", isSudo());
            return ResponseEntity.ok(new VersionResponse(output));
        } catch (SecurityException e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("Sudo access is not available"));
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
        }
    }
}