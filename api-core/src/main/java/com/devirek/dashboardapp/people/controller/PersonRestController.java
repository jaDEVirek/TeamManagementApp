package src.main.java.com.devirek.dashboardapp.people.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.main.java.com.devirek.dashboardapp.common.NoEntityFoundException;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonDto;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonWithTeamsDto;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;

import java.util.List;


/**
 * PersonRestController class for managing Persons
 */
@RestController
public class PersonRestController {
	
	private final PersonService personService;
	
	@Autowired
	public PersonRestController(PersonService personService) {
		this.personService = personService;
	}
	
	/**
	 * This method return all Persons
	 *
	 * @return
	 */
	@GetMapping("/people")
	@CrossOrigin
	public List<PersonDto> findAll() {
		return personService.findAll();
	}
	
	/**
	 * Method returns an Person entity selected id
	 *
	 * @param id
	 * @return
	 */
	
	@GetMapping(value = "/people/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDto> getPersonById(@PathVariable long id) {
		return personService
				.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Update person in database
	 *
	 * @param id
	 * @param personDto
	 * @return
	 */
	@PutMapping(value = "/people/{id}")
	public ResponseEntity<PersonDto> updateById(@PathVariable(value = "id") long id, @RequestBody PersonDto personDto) {
		try {
			personService.updatePersonById(id, personDto);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Delete person from database
	 *
	 * @param id
	 * @return
	 */
	
	@DeleteMapping("/people/{id}")
	@CrossOrigin
	public ResponseEntity<PersonDto> deletePerson(@PathVariable Long id) {
		return personService
				.deletePerson(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	/**
	 * Add new person to database
	 *
	 * @param personDto
	 * @return
	 */
	@PostMapping("/people")
	@CrossOrigin
	public ResponseEntity<?> createPerson(@RequestBody PersonDto personDto) {
		try {
			personService.addPerson(personDto);
			return ResponseEntity.ok(personDto);
		} catch (Exception e) {
			throw new NoEntityFoundException(e);
		}
	}
	
	/**
	 * Return all not Assigned People
	 *
	 * @return not Assigned Person's entities
	 */
	@GetMapping("/people/unassigned")
	@CrossOrigin
	public List<PersonDto> findNotAssignedPeople() {
		return personService.findNotAssignedPeople();
	}
    /**
     * Return all Assigned People
     *
     * @return Assigned Person's entities
     */
	@GetMapping("/peopleWithTeams")
	public List<PersonWithTeamsDto> getPeopleAssignedToTeam() {
		return personService.peopleWithTeamsAssigned();
	}
	
}
