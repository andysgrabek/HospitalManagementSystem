package work.in.progress.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import work.in.progress.hospitalmanagement.model.OutpatientAdmission;
import work.in.progress.hospitalmanagement.repository.OutpatientAdmissionRepository;

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
