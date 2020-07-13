package src.test.java.com.devirek.dashboardapp.people.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import src.main.java.com.devirek.dashboardapp.people.model.Person;
import src.main.java.com.devirek.dashboardapp.people.model.dto.PersonDto;
import src.main.java.com.devirek.dashboardapp.people.repository.IPersonRepository;
import src.main.java.com.devirek.dashboardapp.people.service.PersonService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;


/**
 * TestSuit for {@link PersonService}
 */

public class PersonServiceTest {

    private PersonService personService;
    private ModelMapper mapper;

    @Mock
    private IPersonRepository personRepository;

    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);
        mapper = new ModelMapper();
        personService = new PersonService(personRepository, this.mapper);

    }

    @Before
    public void initMockRepository() {
        Person person1 = new Person(1L, "jan", "mucha", "krakow", "email1@onet.com", "Programing", "Developer");
        Person person2 = new Person(1L, "Alicja", "Kowalska", "Warszawa", "email2@gmail.com", "Business", "Designer");
        Mockito.when(personRepository.findAll())
               .thenReturn(Arrays.asList(person1, person2));
    }

    @Test
    public void shouldReturnAllPeople() {

        List<PersonDto> resultPeopleDtos = personService.findAll();
        assertThat(resultPeopleDtos).hasSize(2);
    }

    @Test
    public void shouldReturnNoAssignedPeople() {
        Person person1 = new Person(1L, "jan", "mucha", "warszawa", "email1@onet.com", "Programing", "Developer");
        Mockito.when(personService.findNotAssignedPeople())
               .thenReturn(Arrays.asList(mapper.map(person1, PersonDto.class)));

        List<PersonDto> noAssignedPeople = personService.findNotAssignedPeople();
        assertThat(noAssignedPeople).hasSize(1);
    }

    @Test
    public void shouldReturnPersonById() {

        Optional<PersonDto> result = personService.findById(3L);
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldDeletePersonById() {
        personService.deletePerson(1L);
        Mockito.verify(personRepository, times(1))
               .deleteById(1l);
    }

    @Test
    public void shouldAddPersonToDatabase() {
        Person person1 = new Person(1L, "jan", "mucha", "krakow", "email1@onet.com", "Programing", "Developer");
        Mockito.when(personRepository.save(person1))
               .thenReturn(person1);

        personService.addPerson(mapper.map(person1, PersonDto.class));
        Mockito.verify(personRepository, times(1))
               .save(person1);
    }

    @Test
    public void shouldUpdatePersonInDatabase() {
        Person person1 = new Person(1L, "jan", "mucha", "warszawa", "email1@onet.com", "Programing", "Developer");

        Mockito.when(personRepository.save(person1))
               .thenReturn(person1);
        Mockito.when(personRepository.findById(1L))
               .thenReturn(Optional.of(person1));
        Mockito.when(personRepository.getOne(1L))
               .thenReturn(person1);

        personService.updatePersonById(1L, mapper.map(person1, PersonDto.class));

        Mockito.verify(personRepository, times(1))
               .save(person1);
        Mockito.verify(personRepository, times(1))
               .findById(1L);
        Mockito.verify(personRepository, times(1))
               .getOne(1L);
    }

}
