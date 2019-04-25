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
        Department department = entityManager.persistAndFlush(Mocks.department());
        Bed bed = bedRepository.saveAndFlush(new Bed(department, "12E"));

        assertThat(bedRepository.findByDepartmentAndAdmissionNotNull(department)).isEmpty();

        bed.setAdmission(entityManager.persist(
                new InpatientAdmission(entityManager.persist(Mocks.patient()), bed)));

        assertThat(bedRepository.findByDepartmentAndAdmissionNotNull(department)).isNotEmpty();
    }

    @Test
    public void whenToStringCalled_thenProperStringShouldBeReturned() {
        Bed bed = Mocks.bed();
        assertThat(bed.toString()).isEqualTo("Bed(" +
                "department=" + bed.getDepartment() + ", " +
                "roomNumber=" + bed.getRoomNumber() + ")");
    }

}
