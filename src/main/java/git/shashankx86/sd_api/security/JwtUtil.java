package git.shashankx86.sd_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Properties;
import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final SecretKey key;
    private final long jwtExpiration;

    @Autowired
    public JwtUtil(Properties envProperties) {
        try {
            String secretKey = envProperties.getProperty("JWT_SECRET");
            if (secretKey == null || secretKey.length() < 32) {
                logger.warn("JWT_SECRET not found or too short, generating secure key");
                this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            } else {
                this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
            }
            
            this.jwtExpiration = Long.parseLong(
                envProperties.getProperty("JWT_EXPIRATION", "86400000")
            );
            logger.info("JwtUtil initialized successfully");
        } catch (Exception e) {
            logger.error("Error initializing JwtUtil", e);
            throw e;
        }
    }

    public String generateToken(UserDetails userDetails) {
        try {
            return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        } catch (Exception e) {
            logger.error("Error generating token", e);
            throw e;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}