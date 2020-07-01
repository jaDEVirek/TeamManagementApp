package src.main.java.com.devirek.dashboardapp.teams.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.main.java.com.devirek.dashboardapp.teams.model.Team;

/**
 * Teams repository class
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
}
