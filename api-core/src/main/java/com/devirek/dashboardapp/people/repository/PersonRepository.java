package src.main.java.com.devirek.dashboardapp.people.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import src.main.java.com.devirek.dashboardapp.people.model.Person;

import java.util.List;

/**
 * PersonRepository class as data storage layer
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	@Query(value = "SELECT * FROM PERSON WHERE PERSON.ID NOT IN ( SELECT PERSON_ID  FROM PERSONS_TEAMS  )", nativeQuery = true)
	List<Person> findAllNotAssignedPeople();
	
	List<Person> findByTeamsNotEmpty();
	
}
