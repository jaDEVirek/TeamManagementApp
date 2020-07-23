package src.main.java.com.devirek.dashboardapp.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import src.main.java.com.devirek.dashboardapp.security.models.EnumRole;
import src.main.java.com.devirek.dashboardapp.security.models.Role;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(EnumRole name);

}
