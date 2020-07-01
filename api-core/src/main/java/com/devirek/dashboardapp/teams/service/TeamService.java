package src.main.java.com.devirek.dashboardapp.teams.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.main.java.com.devirek.dashboardapp.common.CreateEntityException;
import src.main.java.com.devirek.dashboardapp.common.NoEntityFoundException;
import src.main.java.com.devirek.dashboardapp.people.model.Person;
import src.main.java.com.devirek.dashboardapp.people.repository.PersonRepository;
import src.main.java.com.devirek.dashboardapp.teams.model.Team;
import src.main.java.com.devirek.dashboardapp.teams.model.dto.TeamDto;
import src.main.java.com.devirek.dashboardapp.teams.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

/**
 * This class have methods to manage teams
 */
@Service
public class TeamService {
	
	private final TeamRepository teamRepository;
	private final ModelMapper modelMapper;
	PersonRepository personRepository;
	
	@Autowired
	public TeamService(TeamRepository teamRepository, PersonRepository personRepository, ModelMapper modelMapper) {
		this.teamRepository = teamRepository;
		this.modelMapper = modelMapper;
		this.personRepository = personRepository;
	}
	
	/**
	 * This method return a list of teams
	 *
	 * @return list of Teams
	 */
	public List<TeamDto> findAll() {
		return modelMapper.map(teamRepository.findAll(), new TypeToken<List<TeamDto>>() {
		}.getType());
	}
	
	/**
	 * Method finds Team by id
	 * 
	 * @param id
	 *            of team
	 * @return Team object
	 */
	public Optional<TeamDto> findTeamById(Long id) {
		Assert.notNull(id, "ID must exist ");
		return teamRepository
				.findById(id)
				.map(p -> modelMapper.map(p, TeamDto.class));
	}
	
	/**
	 * Method update Team by id
	 *
	 * @param id
	 *            of team
	 * @param teamDto
	 *            represent Team object
	 */
	@Transactional
	public void updateTeamById(Long id, TeamDto teamDto) {
		Assert.notNull(id, "Id can't be null ! ");
		Assert.notNull(teamDto, "Object can't be null!");
		Assert.notNull(teamDto.getId(), "Passing id is required while updating an Object!");
		teamRepository.findById(id)
				.map(team -> {
					Team teamEntity = teamRepository.getOne(id);
					teamEntity.setId(teamDto.getId());
					teamEntity.setName(teamDto.getName());
					teamEntity.setCity(teamDto.getCity());
					teamEntity.setDescription(teamDto.getDescription());
					teamEntity.setHeadcount(teamDto.getHeadcount());
					return teamRepository.save(teamEntity);
				}).orElseThrow(NoEntityFoundException::new);
	}
	
	/**
	 * Method removes team from database
	 * 
	 * @param id
	 *            of team
	 */
	@Transactional
	public void deleteTeamById(Long id) {
		Assert.notNull(id, "Id can't be null !");
		try {
			teamRepository.deleteById(id);
		} catch (Exception e) {
			throw new IllegalStateException("Team with given id, does not exist ! ");
		}
	}
	
	/**
	 * Method creates new Team
	 * 
	 * @param teamDto
	 *            represent Team object
	 */
	@Transactional
	public TeamDto createTeam(TeamDto teamDto) {
		Assert.notNull(teamDto, "Object can't be null!");
		try {
			Assert.notNull(teamDto.getName());
			Team save = teamRepository.save(modelMapper.map(teamDto, Team.class));
			return modelMapper.map(save, TeamDto.class);
		} catch (Exception e) {
			throw new CreateEntityException(e);
		}
	}
	
	@Transactional
	public Optional<Team> findTeamEntityById(Long id) {
		return teamRepository.findById(id);
	}
	
	@Transactional
	public boolean addPersonsToTeams(Long teamId, Long personId) {
		Assert.notNull(personId, "Object can't be null!");
		Assert.notNull(teamId, "Object can't be null!");
		try {
			Person person = personRepository.getOne(personId);
			Team team = teamRepository.getOne(teamId);
			team.getPersons().add(person);
		} catch (Exception e) {
			throw new CreateEntityException(e);
		}
		return true;
	}
	
}
