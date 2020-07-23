package src.test.java.com.devirek.dashboardapp.people.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonDto;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;
import src.main.java.com.devirek.dashboardapp.teams.model.dto.TeamDto;
import src.main.java.com.devirek.dashboardapp.teams.service.TeamService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Integration TestSuit for {@link PersonService}
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = {TeamService.class, PersonService.class})
public class PersonServiceIntegrationTest {

    @Autowired
    private TeamService teamService;
    @Autowired
    private PersonService personService;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldAutowireUsedLayersImplementations() {
        assertThat(teamService).isNotNull();
        assertThat(personService).isNotNull();
    }

    @Test
    @Transactional
    public void shouldReturnNoAssignedPeople() {
        // given
        TeamDto teamDto = new TeamDto(1l, "Byczki z Osiedla ", "local", "wawa", 6);
        TeamDto team = teamService.createTeam(teamDto);

        List<PersonDto> personDtoList = new ArrayList<>();
        returnPersonTestResource().forEach(p -> personDtoList.add(personService.addPerson(p)));

        // when

        boolean resultOfAddingP1 = teamService.addPersonsToTeams(team.getId(), personDtoList.get(0)
                .getId());
        boolean resultOfAddingP2 = teamService.addPersonsToTeams(team.getId(), personDtoList.get(1)
                .getId());

        // extract the people resource
        List<PersonDto> notAssignedPeople = personService.findNotAssignedPeople();

        entityManager.flush();
        entityManager.clear();

        // then
        assertThat((resultOfAddingP1 && resultOfAddingP2)).isTrue();
        assertThat(personDtoList).hasSize(3);

        // grab unassigned Person
        PersonDto p1 = personDtoList.get(2);

        // check that unassigned Person exist in notAssigned Lists
        assertThat(notAssignedPeople).contains(p1);

        // check Person with assigned Team, does not on the unassigned list
        assertThat(notAssignedPeople).doesNotContain(personDtoList.get(0));
    }

    /**
     * Method prepares the resource for test
     *
     * @return list of PersonDto
     */
    private List<PersonDto> returnPersonTestResource() {
        PersonDto p1 = new PersonDto(null, "Janek", "Bucha", "email1@onet.com", "krakow", "Programing", "Developer");
        PersonDto p2 = new PersonDto(null, "Tomasz", "Nieprzydzielony", "email2@onet.com", "krakow", "Programing",
                "Developer");
        PersonDto p3 = new PersonDto(null, "Genowefa", "Przydzielona", "email22@onet.com", "krakow", "UI-Design",
                "Developer");
        return Arrays.asList(p1, p2, p3);
    }
}
