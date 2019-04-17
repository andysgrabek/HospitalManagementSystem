package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonModelTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenPersonCreated_thenUniqueSequenceIdShouldBeAssigned() {
        assertThat(entityManager.persist(Patient.builder().
                name("John").
                surname("Smith").
                birthDate(LocalDate.now()).
                phoneNumber("123456789").
                homeAddress(new Address("Car 3", "Ohio", 12354)).
                build()).getId()).
                isLessThan(entityManager.persist(
                        Patient.builder().
                                name("Ann").
                                surname("Williams").
                                birthDate(LocalDate.now()).
                                phoneNumber("123456789").
                                homeAddress(new Address("Car 3", "Ohio", 12354)).
                                build()).getId());
    }

}
