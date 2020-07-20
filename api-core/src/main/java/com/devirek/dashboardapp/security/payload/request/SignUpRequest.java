package src.main.java.com.devirek.dashboardapp.security.payload.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;
    @NotBlank
    @Email
    @Size(max = 45)
    private String email;
    @NotBlank
    @Size(min = 6, max = 25)
    private String password;

    private Set<String> role;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
