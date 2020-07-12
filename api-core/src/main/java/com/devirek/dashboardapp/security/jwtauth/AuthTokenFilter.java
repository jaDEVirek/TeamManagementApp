package src.main.java.com.devirek.dashboardapp.security.jwtauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import src.main.java.com.devirek.dashboardapp.security.service.UserDetailsServiceImpl;
import src.main.java.com.devirek.dashboardapp.utility.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    private JWTUtils jwtUtils;

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthTokenFilter(JWTUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    public AuthTokenFilter() {
        if (Objects.isNull(userDetailsService) || Objects.isNull(jwtUtils)) {
            userDetailsService = new UserDetailsServiceImpl();
            jwtUtils = new JWTUtils();
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            String jWebToken = parseJwt(request);
            if (Optional.ofNullable(jWebToken)
                        .isPresent() && jwtUtils.validateJwtToken(jWebToken)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.getUserNameFromJWT(jWebToken));
                setAuthenticationByUserDetails(request, userDetails);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
    }

    private void setAuthenticationByUserDetails(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                                                                                                          null,
                                                                                                          userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    }

    private String parseJwt(HttpServletRequest request) {


    }


}
