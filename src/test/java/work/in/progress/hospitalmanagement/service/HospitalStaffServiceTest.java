package work.in.progress.hospitalmanagement.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.model.Address;
import work.in.progress.hospitalmanagement.model.Department;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class HospitalStaffServiceTest {

    private HospitalStaffService hospitalStaffService;

    @MockBean
    private HospitalStaffRepository hospitalStaffRepository;

    private final static String staffName = "John";
    private final static String staffSurname = "Smith";
    private final static String staffEmail = "johnsmit1@dtu.dk";

    @Before
    public void setUp() {
        hospitalStaffService = new HospitalStaffService(hospitalStaffRepository);
        HospitalStaff hospitalStaff = HospitalStaff.builder()
                .name(staffName)
                .surname(staffSurname)
                .role(HospitalStaff.Role.DOCTOR)
                .department(new Department("Intensive Care", new Address("Sky 1", "Warsaw", 54321)))
                .build();
        ReflectionTestUtils.setField(hospitalStaff, "email", staffEmail);

        Mockito.when(hospitalStaffRepository.findByName(hospitalStaff.getName()))
                .thenReturn(Collections.singletonList(hospitalStaff));
        Mockito.when(hospitalStaffRepository.findBySurname(hospitalStaff.getSurname()))
                .thenReturn(Collections.singletonList(hospitalStaff));
        Mockito.when(hospitalStaffRepository.findByEmail(hospitalStaff.getEmail()))
                .thenReturn(Optional.of(hospitalStaff));
    }

    @Test
    public void whenValidName_thenPatientShouldBeFound() {
        List<HospitalStaff> result = hospitalStaffService.findByName(staffName);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getName()).isEqualTo(staffName);
    }

    @Test
    public void whenValidSurname_thenPatientShouldBeFound() {
        List<HospitalStaff> result = hospitalStaffService.findBySurname(staffSurname);

        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getSurname()).isEqualTo(staffSurname);
    }

    @Test
    public void whenValidEmail_thenPatientShouldBeFound() {
        Optional<HospitalStaff> result = hospitalStaffService.findByEmail(staffEmail);

        assertThat(result.isPresent()).isTrue();
        //noinspection OptionalGetWithoutIsPresent
        assertThat(result.get().getEmail()).isEqualTo(staffEmail);
    }

    @Test
    public void whenPatientRegistered_thenPatientShouldBeFound() {
        HospitalStaff staff = Mocks.hospitalStaff();
        Mockito.when(hospitalStaffRepository.save(staff)).thenReturn(staff);

        HospitalStaff registered = hospitalStaffService.registerHospitalStaff(staff);

        assertThat(registered).isNotNull();
    }

}
