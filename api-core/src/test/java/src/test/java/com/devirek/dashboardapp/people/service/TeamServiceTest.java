package src.test.java.com.devirek.dashboardapp.people.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import src.main.java.com.devirek.dashboardapp.common.NoEntityFoundException;
import src.main.java.com.devirek.dashboardapp.people.repository.IPersonRepository;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;
import src.main.java.com.devirek.dashboardapp.teams.model.Team;
import src.main.java.com.devirek.dashboardapp.teams.model.dto.TeamDto;
import src.main.java.com.devirek.dashboardapp.teams.repository.ITeamRepository;
import src.main.java.com.devirek.dashboardapp.teams.service.TeamService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TestSuit for(@link TeamService)
 */
public class TeamServiceTest {
    private TeamService teamService;
    private ModelMapper mapper;
    private PersonService personService;

    @Mock
    private ITeamRepository teamRepository;
    @Mock
    private IPersonRepository personRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mapper = new ModelMapper();
        teamService = new TeamService(teamRepository, personRepository, this.mapper);
        personService = new PersonService(personRepository, this.mapper);
    }

    @Test
    public void shouldReturnAllTeams() {
        initMockServiceTest();

        List<TeamDto> resultTeamDtos = teamService.findAll();
        assertThat(resultTeamDtos).extracting("name", "description", "city", "headcount")
                                  .contains(tuple("Name1", "description1", "city1", 2),
                                            tuple("Name2", "description2", "city2", 5));
    }

    @Test
    public void shouldReturnTeamById() {
        Team team1 = new Team(1l, "Name1", "description1", "city1", 2);

        when(teamRepository.findById(1l)).thenReturn(Optional.ofNullable(team1));
        Optional<TeamDto> teamById = teamService.findTeamById(1l);

        assertThat(teamById.get()
                           .getId()).isEqualTo(1l);
        assertThat(team1.getCity()).isEqualTo(teamById.get()
                                                      .getCity());
    }

    @Test
    public void shouldNotReturnTeamWithNoId() {
        when(teamRepository.findById(null)).thenThrow(new IllegalArgumentException("An argument is missing ! "));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> teamService.findTeamById(null))
                                                                 .withMessage("ID must exist ");
    }

    @Test
    public void shouldNotReturnTeamByGivenId() {
        when(teamRepository.findById(2l)).thenReturn(Optional.empty());

        Optional<TeamDto> teamById = teamService.findTeamById(2l);

        assertThat(teamById).isEmpty();
    }

    @Test
    public void shouldAddTeamToDatabase() {
        Team team1 = new Team(1l, "Name1", "description1", "city1", 2);

        Mockito.when(teamRepository.save(team1))
               .thenReturn(team1);
        teamService.createTeam(mapper.map(team1, TeamDto.class));

        verify(teamRepository, times(1)).save(team1);
    }

    @Test
    public void shouldNotAddTeamToDatabase() {
        when(teamRepository.save(null)).thenThrow(new IllegalArgumentException());

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> teamService.createTeam(null))
                                                                 .withMessage("Object can't be null!");
    }

    @Test
    public void shouldDeleteTeamById() {
        teamService.deleteTeamById(1l);

        verify(teamRepository, times(1)).deleteById(1l);
    }

    @Test
    public void shouldNotDeleteTeamById() {
        doThrow(new IllegalArgumentException()).when(teamRepository)
                                               .deleteById(null);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> teamService.deleteTeamById(null))
                                                                 .withMessage("Id can't be null !");
    }

    @Test
    public void shouldNotDeleteWhenTeamNotExist() {
        doThrow(new IllegalStateException()).when(teamRepository)
                                            .deleteById(11l);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> teamService.deleteTeamById(11l))
                                                              .withMessage("Team with given id, does not exist ! ");
    }

    @Test
    public void shouldUpdateTeamInDatabase() {
        Team team1 = new Team(1l, "Name1", "description1", "city1", 2);

        when(teamRepository.save(team1)).thenReturn(team1);
        when(teamRepository.findById(1l)).thenReturn(Optional.of(team1));
        when(teamRepository.getOne(1l)).thenReturn(team1);

        teamService.updateTeamById(1l, mapper.map(team1, TeamDto.class));

        verify(teamRepository, times(1)).save(team1);
        verify(teamRepository, times(1)).findById(1l);
        verify(teamRepository, times(1)).getOne(1l);
    }

    @Test
    public void shouldNotUpdateTeamToDatabase() {
        TeamDto teamDto = new TeamDto(1l, "TestCase1", "Description1", "Krakow", 12);

        when(teamRepository.findById(1l)).thenThrow(new NoEntityFoundException());
        when(teamRepository.findById(2l)).thenThrow(new IllegalArgumentException());
        when(teamRepository.findById(null)).thenThrow(new IllegalArgumentException());

        assertThatExceptionOfType(NoEntityFoundException.class).isThrownBy(
                () -> teamService.updateTeamById(1l, teamDto))
                                                               .withMessage(
                                                                       "There is no Entity in database with given id.");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> teamService.updateTeamById(null, teamDto))
                                                                 .withMessage("Id can't be null ! ");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> teamService.updateTeamById(2l, null))
                                                                 .withMessage("Object can't be null!");
    }

    private void initMockServiceTest() {
        Team team1 = new Team(1l, "Name1", "description1", "city1", 2);
        Team team2 = new Team(1l, "Name2", "description2", "city2", 5);

        when(teamRepository.findAll()).thenReturn(Arrays.asList(team1, team2));
    }
}
