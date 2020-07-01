package src.main.java.com.devirek.dashboardapp.people.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import src.main.java.com.devirek.dashboardapp.common.CreateEntityException;
import src.main.java.com.devirek.dashboardapp.people.model.Person;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonDto;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonWithTeamsDto;
import src.main.java.com.devirek.dashboardapp.people.repository.PersonRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PersonService class for managing {@link PersonRepository}
 */
@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * This method return a list of Persons
     *
     * @return
     */
    public List<PersonDto> findAll() {
        return modelMapper.map(personRepository.findAll(), new TypeToken<List<PersonDto>>() {
        }.getType());
    }

    /**
     * Method returns a Person by id
     *
     * @param id
     * @return
     */
    public Optional<PersonDto> findById(Long id) {
        Assert.notNull(id, "id can't be null");
        return personRepository.findById(id)
                               .map(p -> modelMapper.map(p, PersonDto.class));

    }

    /**
     * This method delete person by id
     *
     * @param id
     */

    public Optional<PersonDto> deletePerson(Long id) {
        Assert.notNull(id, "id can't be null");
        Optional<Person> personOptional = personRepository.findById(id);
        personRepository.deleteById(id);
        return personOptional.map(p -> modelMapper.map(p, PersonDto.class));
    }

    /**
     * This method create new person
     *
     * @param personDto
     * @return
     */
    public PersonDto addPerson(PersonDto personDto) {
        Assert.notNull(personDto, "Object can't be null!");
        try {
            Assert.notNull(personDto.getFirstName());
            Person save = personRepository.save(modelMapper.map(personDto, Person.class));
            return modelMapper.map(save, PersonDto.class);
        } catch (Exception e) {
            throw new CreateEntityException(e);
        }
    }

    /**
     * This method update person by id
     *
     * @param id
     * @param personDto
     * @return
     */

    public void updatePersonById(long id, PersonDto personDto) throws NoSuchElementException {
        Assert.notNull(id, "id can't be null");
        Assert.notNull(personDto, "personDto can't be null");
        personRepository.findById(id)
                        .map(person -> {
                            Person personEntity = personRepository.getOne(id);
                            personEntity.setFirstName(personDto.getFirstName());
                            personEntity.setLastName(personDto.getLastName());
                            personEntity.setLocation(personDto.getLocation());
                            personEntity.setEmail(personDto.getEmail());
                            personEntity.setRole(personDto.getRole());
                            personEntity.setStatus(personDto.getStatus());
                            return personRepository.save(personEntity);
                        })
                        .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Return all not Assigned People
     *
     * @return not Assigned Person's entities
     */
    public List<PersonDto> findNotAssignedPeople() {
        return modelMapper.map(personRepository.findAllNotAssignedPeople(), new TypeToken<List<PersonDto>>() {
        }.getType());
    }

    /**
     * Return all Assigned People
     *
     * @return Assigned Person's entities
     */
    public List<PersonWithTeamsDto> peopleWithTeamsAssigned() {
        List<Person> people = personRepository.findByTeamsNotEmpty();

        return people.stream()
                     .map(person -> {
                         PersonWithTeamsDto dto = modelMapper.map(person, PersonWithTeamsDto.class);
                         dto.setTeams(mapTeams(person));
                         return dto;
                     })
                     .collect(Collectors.toList());
    }

    private Set<TeamDto> mapTeams(Person person) {
        return person.getTeams()
                     .stream()
                     .map(t -> modelMapper.map(t, TeamDto.class))
                     .collect(Collectors.toSet());
    }
}
