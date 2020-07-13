package java.src.test.java.com.devirek.dashboardapp.people.model;

import org.junit.BeforeClass;
import org.junit.Test;
import src.main.java.com.devirek.dashboardapp.people.model.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestSuit for {@link Person}
 *
 * @author Wiktor Religo
 * @since 10.04.2018
 */
public class PersonTest {

    private static Person person1;
    private static Person person2;

    /**
     * Method initializes Person class fields with fake data
     */
    @BeforeClass
    public static void initObjects() {
        person1 = new Person(1L, "Tomek", "Nowak", "Krkaów", "email@gmail.com", "APPS", "Developer");
        person2 = new Person(1L, "Alicja", "Kowalska", "Warszawa", "email2@gmail.com", "Business", "Designer");

    }

    @Test
    public void testEqualsForPerson() {
        assertThat(person1).isEqualTo(person1);
        assertThat(person1).isNotEqualTo(person2);
    }

    @Test
    public void testHashCodeForPerson() {
        assertThat(person1.hashCode()).isEqualTo(person1.hashCode());
        assertThat(person1.hashCode()).isNotEqualTo(person2.hashCode());
    }

}
