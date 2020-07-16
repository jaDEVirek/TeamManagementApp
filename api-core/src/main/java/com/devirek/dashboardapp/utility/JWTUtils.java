package src.main.java.com.devirek.dashboardapp.utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import src.main.java.com.devirek.dashboardapp.security.service.UserDetailsImpl;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);


    private static final SecretKey jwtSecureKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${devirek.api.jwtTimeExpiration}")
    private Integer jwtExpirationTime;


    public String generateWebToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                   .setSubject(principal.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
                   .signWith(jwtSecureKey,SignatureAlgorithm.HS512)
                   .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecureKey)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.error("Validate JWTToken - Error! Type: " + e + " " + e.getMessage() + " Cause: " + e.getCause());
        }
        return false;
    }


    public String getUserNameFromToken(String authToken) {
        return Jwts.parserBuilder()
                   .setSigningKey(jwtSecureKey)
                   .build()
                   .parseClaimsJws(authToken)
                   .getBody()
                   .getSubject();
    }

}
