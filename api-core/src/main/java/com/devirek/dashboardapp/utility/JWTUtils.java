package src.main.java.com.devirek.dashboardapp.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JWTUtils {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    @Value("${devirek.api.jwtSecureKey}")
    private String jwtSecureKey;

    @Value("${devirek.api.jwtTimeExpiration}")
    private Integer jwtExpirationTime;


    public String generateWeBToken() {


    }

    public boolean validateJwtToken(String webToken) {
        return false;
    }


    public String getUserNameFromJWT(String userName) {


    }
}
