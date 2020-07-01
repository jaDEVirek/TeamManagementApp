package src.main.java.com.devirek.dashboardapp.people.model.dto;


import java.io.Serializable;


/**
 * This class define personDto
 */
public class PersonWithTeamsDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String status;
    private String role;
    private Set<TeamDto> teams;

    public PersonWithTeamsDto() {
    }

    public PersonWithTeamsDto(Long id, String firstName, String lastName, String email, String location, String status,
                              String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.status = status;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamDto> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "PersonDto{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", location='" + location + '\'' + ", status='" + status + '\'' + ", role='" + role + '\'' + '}';
    }

}
