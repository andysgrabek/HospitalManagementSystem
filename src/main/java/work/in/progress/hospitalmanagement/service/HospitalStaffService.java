package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.HospitalStaff;
import work.in.progress.hospitalmanagement.repository.HospitalStaffRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalStaffService {

    private final HospitalStaffRepository hospitalStaffRepository;

    @Autowired
    public HospitalStaffService(HospitalStaffRepository hospitalStaffRepository) {
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    public HospitalStaff registerHospitalStaff(HospitalStaff hospitalStaff) {
        return hospitalStaffRepository.save(hospitalStaff);
    }

    public List<HospitalStaff> findByName(String name) {
        return hospitalStaffRepository.findByName(name);
    }

    public List<HospitalStaff> findBySurname(String surname) {
        return hospitalStaffRepository.findBySurname(surname);
    }

    public Optional<HospitalStaff> findByEmail(String email) {
        return hospitalStaffRepository.findByEmail(email);
    }

}
