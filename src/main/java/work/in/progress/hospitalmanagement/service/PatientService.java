package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides a public API to manage {@link Patient}.
 *
 * @author jablonskiba
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Registers a given patient. Use the returned patient for further operations as the
     * register operation might have changed the patient instance.
     *
     * @param patient the patient to register, must not be {@code null}
     * @return the registered patient, will never be {@code null}
     * @throws IllegalArgumentException if the specified patient is {@code null}
     */
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Unregisters a given patient.
     *
     * @param patient the patient to unregister, must not be {@code null}
     * @throws IllegalArgumentException if the specified patient is {@code null}
     */
    public void unregisterPatient(Patient patient) {
        patientRepository.delete(patient);
    }

    /**
     * Retrieves a patients with a specified name {@see Patient#getName()}.
     *
     * @param name search criteria
     * @return all patients with a specified name
     */
    public List<Patient> findByName(String name) {
        return patientRepository.findByName(name);
    }

    /**
     * Retrieves a patients with a specified surname {@see Patient#getSurname()}.
     *
     * @param surname search criteria
     * @return all patients with a specified surname
     */
    public List<Patient> findBySurname(String surname) {
        return patientRepository.findBySurname(surname);
    }

    /**
     * Retrieves a patients with a specified birth date {@see Patient#getBirthDate()}.
     *
     * @param birthDate search criteria
     * @return all patients with a specified birth date
     */
    public List<Patient> findByBirthDate(LocalDate birthDate) {
        return patientRepository.findByBirthDate(birthDate);
    }

}
