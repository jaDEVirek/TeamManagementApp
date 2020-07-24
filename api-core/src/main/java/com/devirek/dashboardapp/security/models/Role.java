package src.main.java.com.devirek.dashboardapp.security.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "name")
    private EnumRole roleName;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRole getRoleName() {
        return roleName;
    }

    public void setRoleName(EnumRole roleName) {
        this.roleName = roleName;
    }
}
