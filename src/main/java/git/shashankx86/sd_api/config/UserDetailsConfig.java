package git.shashankx86.sd_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Properties;

@Configuration
public class UserDetailsConfig {
    
    private final Properties envProperties;
    
    public UserDetailsConfig(Properties envProperties) {
        this.envProperties = envProperties;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
            .username(envProperties.getProperty("ADMIN_USERNAME"))
            .password(passwordEncoder.encode(envProperties.getProperty("ADMIN_PASSWORD")))
            .authorities("USER") 
            .build();
            
        return new InMemoryUserDetailsManager(admin);
    }
}