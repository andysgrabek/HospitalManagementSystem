package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.Patient;
import work.in.progress.hospitalmanagement.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void unregisterPatient(Patient patient) {
        patientRepository.delete(patient);
    }

    public Optional<Patient> findByName(String name) {
        return patientRepository.findByName(name);
    }

    public Optional<Patient> findBySurname(String surname) {
        return patientRepository.findBySurname(surname);
    }

    public Optional<Patient> findByBirthDate(LocalDate birthDate) {
        return patientRepository.findByBirthDate(birthDate);
    }

}