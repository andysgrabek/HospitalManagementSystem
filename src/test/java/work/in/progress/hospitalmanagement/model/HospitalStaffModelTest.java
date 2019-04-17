package work.in.progress.hospitalmanagement.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HospitalStaffModelTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HospitalStaffRepository hospitalStaffRepository;

    @Test
    public void whenFetched_calculateEmailBasedOnFields() {
        HospitalStaff hospitalStaff = Mocks.hospitalStaff();
        entityManager.persist(hospitalStaff.getDepartment());
        entityManager.persist(hospitalStaff);
        entityManager.flush();
        entityManager.refresh(hospitalStaff);
        
        assertThat(hospitalStaff.getEmail()).
                isEqualTo(String.format("%s%s%d@dtu.dk",
                        StringUtils.left(hospitalStaff.getName(), 4).toLowerCase(),
                        StringUtils.left(hospitalStaff.getSurname(), 4).toLowerCase(),
                        hospitalStaff.getId()));
    }

}
