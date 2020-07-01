package src.main.java.com.devirek.dashboardapp.teams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;
import src.main.java.com.devirek.dashboardapp.teams.model.TeamsAndPersonsId;
import src.main.java.com.devirek.dashboardapp.teams.model.dto.TeamDto;
import src.main.java.com.devirek.dashboardapp.teams.service.TeamService;

import java.util.List;

/**
 * This class for managing and finding teams
 */
@RestController
public class TeamController {
	
	private final TeamService teamService;
	private final PersonService personService;
	
	@Autowired
	public TeamController(TeamService teamService, PersonService personService) {
		this.teamService = teamService;
		this.personService = personService;
	}
	
	/**
	 * This method return all teams
	 *
	 * @return
	 * 		list of teams
	 */
	@GetMapping("/teams")
	@CrossOrigin
	public List<TeamDto> findAll() {
		return teamService.findAll();
	}
	
	/**
	 * Method update Team by id
	 *
	 * @param id
	 *            of team
	 * @param teamDto
	 *            represent Team object
	 * @return updated Team
	 */
	@PutMapping("teams/{id}")
	public ResponseEntity<?> updateById(@PathVariable(value = "id") Long id, @RequestBody TeamDto teamDto) {
		teamService.updateTeamById(id, teamDto);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Method finds Team by id
	 *
	 * @param id
	 *            of team
	 * @return Team object
	 */
	@GetMapping("teams/{id}")
	public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
		return teamService
				.findTeamById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new IllegalArgumentException("There is no Team with given ID"));
	}
	
	/**
	 * Method removes team from database
	 *
	 * @param id
	 *            of team
	 * @return deleted team
	 */
	@DeleteMapping("teams/{id}")
	@CrossOrigin
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		teamService.deleteTeamById(id);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Method creates new Team
	 *
	 * @param teamDto
	 *            represent Team object
	 * @return new Team
	 */
	@PostMapping("/teams")
	@CrossOrigin
	public ResponseEntity<?> createTeam(@RequestBody TeamDto teamDto) {
		teamService.createTeam(teamDto);
		return ResponseEntity.ok(teamDto);
	}
	
	@PostMapping("/addPeopleToTeams/{teamId}/{personId}")
	@ResponseBody
	public ResponseEntity<?> addPeopleToTeam(@PathVariable Long teamId, @PathVariable Long personId) {
		TeamsAndPersonsId teamsAndPersonsId = new TeamsAndPersonsId(personId, teamId);
		teamService.addPersonsToTeams(teamId, personId);
		
		return ResponseEntity.ok(teamsAndPersonsId);
	}
	
}
