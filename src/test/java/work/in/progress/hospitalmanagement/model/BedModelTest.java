package work.in.progress.hospitalmanagement.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class BedModelTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BedRepository bedRepository;

    @Test
    public void whenBedAssigned_thenOccupiedBedsIncrease() {
        Bed bed = Mocks.bed();
        Department department = entityManager.persist(bed.getDepartment());
        department.getBeds().add(bed);

        assertThat(bedRepository.occupiedBeds(bed.getDepartment()).size()).isEqualTo(0);

        bed.setAdmission(entityManager.persist(
                new InpatientAdmission(entityManager.persist(Mocks.patient()), bed)));

        assertThat(bedRepository.occupiedBeds(bed.getDepartment()).size()).isEqualTo(1);
    }

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        Bed bed = Mocks.bed();
        assertThat(bed.toString()).isEqualTo("Bed(" +
                "department=" + bed.getDepartment() + ", " +
                "roomNumber=" + bed.getRoomNumber() + ")");
    }

}
