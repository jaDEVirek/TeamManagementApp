package src.main.java.com.devirek.dashboardapp.teams.model;

import src.main.java.com.devirek.dashboardapp.people.model.Person;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Team represents entity in the database
 */
@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;

    @NotNull
    @Size(min = 4, max = 35, message = "Name should be between 4-35 characters.")
    private String name;
    @NotNull
    @Size(max = 100, message = "Description should be less then 100 characters")
    private String description;
    @NotNull
    @Size(min = 3, max = 20)
    private String city;
    @Min(value = 0)
    private Integer headcount;
    @Column(name = "Creation_time")
    private LocalDateTime createdOn;
    @Column(name = "Modification_time")
    private LocalDateTime modifiedOn;

    @ManyToMany
    @JoinTable(name = "PERSONS_TEAMS",
               joinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "id")})
    private Set<Person> persons = new HashSet<>();

    /**
     * Default constructor using for ModelMapper
     */
    public Team() {
    }

    public Team(Long id, String name, String description, String city, Integer headcount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.headcount = headcount;
    }

    /*
     * Method sets creation time of the object
     */
    @PrePersist
    public void persistOnCreate() {
        this.createdOn = LocalDateTime.now();
    }

    /*
     * Method sets modification time of the object
     */
    @PreUpdate
    public void updateOnModify() {
        this.modifiedOn = LocalDateTime.now();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", city='" + city + '\'' + ", headcount=" + headcount + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + '}';
    }

    /*
     * Implementation of equals and hashCode for testing purposes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) && Objects.equals(version, team.version) && Objects
                .equals(name,
                        team.name) && Objects.equals(
                description, team.description) && Objects.equals(city, team.city) && Objects
                .equals(headcount,
                        team.headcount) && Objects.equals(
                createdOn, team.createdOn) && Objects.equals(modifiedOn, team.modifiedOn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, version, name, description, city, headcount, createdOn, modifiedOn);
    }
}
