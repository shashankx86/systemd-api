package git.shashankx86.sd_api.controller.auth;

import git.shashankx86.sd_api.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")  // Remove /api since it's already in application.properties
@CrossOrigin
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.debug("Login attempt for user: {}", loginRequest.getUsername());
            
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            
            if (userDetails != null && passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                String token = jwtUtil.generateToken(userDetails);
                logger.info("Login successful for user: {}", loginRequest.getUsername());
                return ResponseEntity.ok(new AuthResponse(token));
            }
            
            logger.warn("Invalid credentials for user: {}", loginRequest.getUsername());
            throw new BadCredentialsException("Invalid username or password");
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid credentials"));
        } catch (Exception e) {
            logger.error("Login error", e);
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }
}

class ErrorResponse {
    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}