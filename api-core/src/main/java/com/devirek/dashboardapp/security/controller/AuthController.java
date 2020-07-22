package src.main.java.com.devirek.dashboardapp.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import src.main.java.com.devirek.dashboardapp.security.models.EnumRole;
import src.main.java.com.devirek.dashboardapp.security.models.Role;
import src.main.java.com.devirek.dashboardapp.security.models.User;
import src.main.java.com.devirek.dashboardapp.security.payload.request.LoginRequest;
import src.main.java.com.devirek.dashboardapp.security.payload.request.SignUpRequest;
import src.main.java.com.devirek.dashboardapp.security.payload.response.PayloadResponse;
import src.main.java.com.devirek.dashboardapp.security.payload.response.TokenResponse;
import src.main.java.com.devirek.dashboardapp.security.repository.IRoleRepository;
import src.main.java.com.devirek.dashboardapp.security.repository.IUserRepository;
import src.main.java.com.devirek.dashboardapp.security.service.UserDetailsImpl;
import src.main.java.com.devirek.dashboardapp.utility.JWTUtils;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private IUserRepository userRepository;

    private IRoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JWTUtils jwtUtils;

    @Autowired
    public AuthController(IUserRepository userRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUserByLogin(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = generateAuthentication(loginRequest);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(
                new TokenResponse(principal.getId(), jwtUtils.generateWebToken(authentication), principal.getUsername(),
                        principal.getEmail(), getRolesForUser(principal)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest()
                    .body(new PayloadResponse("Error: Email is already in use!"));
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new PayloadResponse("Error: Email is already in use!"));
        }
        User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setRoles(setRolesForNewUser(signUpRequest.getRole()));
        userRepository.save(user);
        return ResponseEntity.ok(new PayloadResponse("User registered successfully!"));
    }

    private Set<Role> setRolesForNewUser(Set<String> signUpRoles) {
        Set<Role> roles = new HashSet<>();
        if (!Optional.ofNullable(signUpRoles)
                .isPresent()) {
            roles.add(findRoleByType(EnumRole.USER_ROLE));
        } else {
            signUpRoles.forEach(signUpRole -> {
                switch (signUpRole) {
                    case "admin":
                        roles.add(findRoleByType(EnumRole.ADMIN_ROLE));
                        break;
                    case "mod":
                        roles.add(findRoleByType(EnumRole.MODERATOR_ROLE));
                        break;
                    default:
                        roles.add(findRoleByType(EnumRole.USER_ROLE));
                        break;
                }
            });
        }
        return roles;
    }

    private Role findRoleByType(EnumRole enumRole) throws RuntimeException {
        return roleRepository.findByName(enumRole)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    private List<String> getRolesForUser(UserDetailsImpl userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private Authentication generateAuthentication(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                        loginRequest.getPassword()));
    }
}
