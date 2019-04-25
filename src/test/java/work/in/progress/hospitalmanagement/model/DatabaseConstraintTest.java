package work.in.progress.hospitalmanagement.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import work.in.progress.hospitalmanagement.repository.BedRepository;
import work.in.progress.hospitalmanagement.repository.DepartmentRepository;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;
import work.in.progress.hospitalmanagement.repository.InpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;
import work.in.progress.hospitalmanagement.repository.PatientRepository;
import work.in.progress.hospitalmanagement.util.Mocks;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
public class DatabaseConstraintTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private HospitalStaffRepository hospitalStaffRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private InpatientAdmissionRepository inpatientAdmissionRepository;

    @Autowired
    private OutpatientAdmissionRepository outpatientAdmissionRepository;


    @Test(expected = ConstraintViolationException.class)
    public void whenDepartmentNameToLong_thenConstraintViolationExceptionShouldBeThrown() {
        Department department = Mocks.department();
        ReflectionTestUtils.setField(department, "name", StringUtils.repeat('a', 101));

        departmentRepository.saveAndFlush(department);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenPersonNameNotMatchPattern_thenConstraintViolationExceptionShouldBeThrown() {
        Patient patient = Mocks.patient();
        ReflectionTestUtils.setField(patient, "name", "Joh7n");

        patientRepository.saveAndFlush(patient);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenZipCodeTooShort_thenConstraintViolationExceptionShouldBeThrown() {
        Patient patient = Mocks.patient();
        patient.getHomeAddress().setZipCode(123);

        patientRepository.saveAndFlush(patient);
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenPatientPhoneNumberInWrongFormat_thenConstraintViolationExceptionShouldBeThrown() {
        Patient patient = Mocks.patient();
        patient.setPhoneNumber("123ABC56789");

        patientRepository.saveAndFlush(patient);
    }

    @Test
    public void whenDepartmentDeleted_thenBedsAndAdmissionsAndDepartmentShouldBeDeleted() {
        Department department = departmentRepository.saveAndFlush(Mocks.department());

        OutpatientAdmission outpatientAdmission =
                outpatientAdmissionRepository.saveAndFlush(
                        new OutpatientAdmission(patientRepository.saveAndFlush(Mocks.patient()), department));
        Bed bed = bedRepository.saveAndFlush(new Bed(department, "12E"));
        InpatientAdmission inpatientAdmission =
                inpatientAdmissionRepository.saveAndFlush(
                        new InpatientAdmission(patientRepository.saveAndFlush(Mocks.patient()), bed));
        HospitalStaff hospitalStaff = hospitalStaffRepository.saveAndFlush(
                HospitalStaff.builder().name("Rzon").surname("Lok")
                        .role(HospitalStaff.Role.DOCTOR).department(department).build());

        departmentRepository.delete(department);
        departmentRepository.flush();
        entityManager.clear();

        assertThat(departmentRepository.findAll()).isEmpty();
        assertThat(bedRepository.findAll()).isEmpty();
        assertThat(outpatientAdmissionRepository.findAll()).isEmpty();
        assertThat(inpatientAdmissionRepository.findAll()).isEmpty();
        assertThat(hospitalStaffRepository.findAll()).isEmpty();
    }

    @Test
    public void whenBedDeleted_thenAdmissionShouldBeDeleted() {
        Department department = departmentRepository.saveAndFlush(Mocks.department());

        Bed bed = bedRepository.saveAndFlush(new Bed(department, "12E"));
        InpatientAdmission inpatientAdmission =
                inpatientAdmissionRepository.saveAndFlush(
                        new InpatientAdmission(patientRepository.saveAndFlush(Mocks.patient()), bed));

        bedRepository.delete(bed);
        bedRepository.flush();
        entityManager.clear();

        assertThat(departmentRepository.findAll()).isNotEmpty();
        assertThat(bedRepository.findAll()).isEmpty();
        assertThat(inpatientAdmissionRepository.findAll()).isEmpty();
    }

    @Test
    public void whenPatientDeleted_thenAdmissionShouldBeDeleted() {
        Patient patient = patientRepository.saveAndFlush(Mocks.patient());
        Department department = departmentRepository.saveAndFlush(Mocks.department());

        OutpatientAdmission outpatientAdmission =
                outpatientAdmissionRepository.saveAndFlush(
                        new OutpatientAdmission(patient, department));

        patientRepository.delete(patient);
        patientRepository.flush();
        entityManager.clear();

        assertThat(patientRepository.findAll()).isEmpty();
        assertThat(outpatientAdmissionRepository.findAll()).isEmpty();
    }

}
