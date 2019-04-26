package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;

@Service
public class OutpatientAdmissionService extends AbstractService<OutpatientAdmission, Integer> {

    @Autowired
    public OutpatientAdmissionService(OutpatientAdmissionRepository outpatientAdmissionRepository) {
        super(outpatientAdmissionRepository);
    }

    @Override
    protected Sort defaultSort() {
        return Sort.by("admissionDate");
    }

}
