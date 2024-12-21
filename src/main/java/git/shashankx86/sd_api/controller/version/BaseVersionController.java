package git.shashankx86.sd_api.controller.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import git.shashankx86.sd_api.service.CommandExecutorService;
import git.shashankx86.sd_api.service.PrivilegeHandler;
import git.shashankx86.sd_api.common.response.ErrorResponse;
import git.shashankx86.sd_api.common.response.ApiResponse;

public abstract class BaseVersionController {
    @Autowired
    protected CommandExecutorService commandExecutor;
    
    @Autowired
    protected PrivilegeHandler privilegeHandler;
    
    protected abstract boolean isSudo();
    
    @GetMapping("/version")
    public ResponseEntity<ApiResponse<?>> getVersion() {
        try {
            String output = commandExecutor.executeCommand("systemd --version", isSudo());
            return ResponseEntity.ok(ApiResponse.success(new VersionResponse(output)));
        } catch (SecurityException e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}