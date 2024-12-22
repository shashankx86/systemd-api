package git.shashankx86.sd_api.controller.auth;

public class AuthResponse {
    private String token;
    
    public AuthResponse(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
}