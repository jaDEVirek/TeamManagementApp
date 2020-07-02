package src.main.java.com.devirek.dashboardapp.people.model.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class define personDto
 */
public class PersonDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String location;
    private String status;
    private String role;

    public PersonDto() {
    }

    public PersonDto(Long id, String firstName, String lastName, String email, String location, String status,
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

    @Override
    public String toString() {
        return "PersonDto{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", location='" + location + '\'' + ", status='" + status + '\'' + ", role='" + role + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDto)) return false;
        PersonDto personDto = (PersonDto) o;
        return Objects.equals(getId(), personDto.getId()) && Objects.equals(getFirstName(),
                                                                            personDto.getFirstName()) && Objects.equals(
                getLastName(), personDto.getLastName()) && Objects.equals(getEmail(),
                                                                          personDto.getEmail()) && Objects.equals(
                getLocation(), personDto.getLocation()) && Objects.equals(getStatus(),
                                                                          personDto.getStatus()) && Objects.equals(
                getRole(), personDto.getRole());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getLocation(), getStatus(), getRole());
    }
}
