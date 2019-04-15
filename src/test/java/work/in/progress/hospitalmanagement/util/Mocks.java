package work.in.progress.hospitalmanagement.util;

import work.in.progress.hospitalmanagement.model.*;

import java.time.LocalDate;

public class Mocks {

    /* Suppresses default constructor, ensuring non-insatiability. */
    private Mocks() {
    }

    public static Address address() {
        return new Address("Energy 1","Copenhagen", 12345);
    }

    public static Department department() {
        return new Department("Intensive Care", address());
    }

    public static Patient patient() {
        return Patient.builder()
                .name("John")
                .surname("Smith")
                .birthDate(LocalDate.of(1410, 7, 15))
                .phoneNumber("123456789")
                .isAlive(true)
                .homeAddress(address())
                .build();
    }

    public static HospitalStaff hospitalStaff() {
        return HospitalStaff.builder()
                .name("Ann")
                .surname("Williams")
                .role(HospitalStaff.Role.DOCTOR)
                .department(department())
                .build();
    }

    public static Bed bed() {
        return new Bed(department(), "12E");
    }

    public static InpatientAdmission inpatientAdmission() {
        return new InpatientAdmission(patient(), bed());
    }

    public static OutpatientAdmission outpatientAdmission() {
        return new OutpatientAdmission(patient(), department());
    }

}
