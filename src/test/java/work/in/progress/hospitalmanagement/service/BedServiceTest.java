package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import work.in.progress.hospitalmanagement.model.Bed;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.InpatientAdmission;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class BedServiceTest {

    private BedService bedService;

    @MockBean
    private BedRepository bedRepository;

    @Before
    public void setUp() {
        bedService = new BedService(bedRepository);
    }

    @Test
    public void whenBedAssigned_thenOccupiedBedsIncrease() {
        Bed bed = Mocks.bed();
        Department department = bed.getDepartment();

        Mockito.when(bedRepository.findByDepartmentAndAdmissionNotNull(department))
                .thenReturn(Collections.emptySet())
                .thenReturn(Collections.singletonList(bed));

        assertThat(bedService.occupiedBeds(department).size()).isEqualTo(0);
        bed.setAdmission(new InpatientAdmission(Mocks.patient(), bed));
        assertThat(bedService.occupiedBeds(department).size()).isEqualTo(1);
    }

}
