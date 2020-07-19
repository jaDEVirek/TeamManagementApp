package src.main.java.com.devirek.dashboardapp.security.payload.response;


import java.util.List;

public class TokenResponse {

    private static final String accessTokenType = "Bearer";
    private Long id;
    private String token;
    private String userName;
    private String email;
    private List<String> roles;

    public TokenResponse(Long id, String token, String userName, String email, List<String> roles) {
        this.id = id;
        this.token = token;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public static String getAccessTokenType() {
        return accessTokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }gi
}
