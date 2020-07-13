package java.src.test.java.com.devirek.dashboardapp.people.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import src.main.java.com.devirek.dashboardapp.people.model.Person;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonDto;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;
import src.main.java.com.devirek.dashboardapp.teams.model.Team;
import src.main.java.com.devirek.dashboardapp.teams.model.dto.TeamDto;
import src.main.java.com.devirek.dashboardapp.teams.service.TeamService;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {TeamService.class, PersonService.class})
public class TeamServiceIntegrationTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private PersonService personService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldAutowireServiceImplementation() {
        assertThat(teamService).isNotNull();
    }

    @Test
    @Transactional
    public void shouldAddPersonToTeam() {

        // Given
        TeamDto teamDto = new TeamDto(11l, "Janek", "local", "wawa", 6);
        PersonDto personDto = new PersonDto(12l, "janek", "mucha", "email1@onet.com", "krakow", "Programing",
                                            "Developer");
        TeamDto team = teamService.createTeam(teamDto);
        PersonDto person = personService.addPerson(personDto);

        // When
        final long personId = person.getId();
        teamService.addPersonsToTeams(team.getId(), personId);

        entityManager.flush();
        entityManager.clear();
        // Then
        Optional<Team> teamFromService = teamService.findTeamEntityById(team.getId());
        assertThat(teamFromService.isPresent()).isTrue();
        Set<Person> persons = teamFromService.get()
                                             .getPersons();
        assertThat(persons).hasSize(1);
        assertThat(persons.stream()
                          .filter(person1 -> person1.getId() == personId)
                          .findAny()).isPresent();
    }

}
