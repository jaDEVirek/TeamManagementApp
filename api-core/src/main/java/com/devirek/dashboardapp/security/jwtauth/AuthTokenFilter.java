package src.main.java.com.devirek.dashboardapp.security.jwtauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import src.main.java.com.devirek.dashboardapp.security.repository.IUserRepository;
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

    private IUserRepository userRepository;


    @Autowired
    public AuthTokenFilter(JWTUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }


    public AuthTokenFilter() {
        if (Objects.isNull(userDetailsService) || Objects.isNull(jwtUtils)) {
            userDetailsService = new UserDetailsServiceImpl(userRepository);
            jwtUtils = new JWTUtils();
        }
    }

    @Autowired
    private void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            Optional<String> jWebToken = parseJwt(request);

            if (Optional.ofNullable(jWebToken)
                    .isPresent() && jwtUtils.validateJwtToken(jWebToken.get())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(
                        jwtUtils.getUserNameFromToken(jWebToken.get()));
                setAuthenticationByUserDetails(request, userDetails);
            }

        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationByUserDetails(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
    }

    /**
     * Bearer - prefix determines the type of authentication
     *
     * @return
     */
    private Optional<String> parseJwt(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer")) {
            return Optional.of(authorizationHeader.substring(7, authorizationHeader.length()));
        }
        ;
        return Optional.empty();
    }
}
