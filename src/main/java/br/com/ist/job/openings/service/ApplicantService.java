package br.com.ist.job.openings.service;

import br.com.ist.job.exception.BusinessException;
import br.com.ist.job.openings.model.Applicant;
import br.com.ist.job.openings.model.ApplicantPosition;
import br.com.ist.job.openings.repository.ApplicantRepository;
import br.com.ist.job.openings.repository.SuperRepo;
import br.com.ist.job.utils.Utils;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class ApplicantService extends SuperService<Applicant> implements Serializable {

    private final ApplicantRepository repository;

    @Override
    public SuperRepo<Applicant> getRepo() {
        return repository;
    }

    public Applicant apply(Applicant applicant) throws BusinessException {
        
        if (Objects.nonNull(applicant)) {
            if (!CollectionUtils.isEmpty(applicant.getApplicantPositions())) {
                applicant.getApplicantPositions()
                        .stream()
                        .forEach(applicantPosition -> {
                            applicantPosition.setApplicant(applicant);
                            applicantPosition.setSalaryOutRanged(Utils.isOutRanged(applicantPosition.getSalaryExpectation(),
                                    applicantPosition.getPosition().getMinSalary(),
                                    applicantPosition.getPosition().getMaxSalary()));
                        });
            } else {
                throw new BusinessException("applicant.error.applyPositionsRequired");
            }
        } else {
            throw new BusinessException("applicant.error.onIdentify");
        }
        
        Applicant applicantDB = getRepo().findById(applicant.getId()).orElse(applicant);
        
        if (Objects.nonNull(applicantDB)) {
            if (CollectionUtils.isEmpty(applicantDB.getApplicantPositions())) {
                applicantDB.setApplicantPositions(applicant.getApplicantPositions());
            } else {
                applicantDB.getApplicantPositions().addAll(applicant.getApplicantPositions());
            }
        }
        super.save(applicantDB);
        return repository.findById(applicantDB.getId()).get();
    }

    @Override
    public Applicant save(Applicant applicant) {
        applicant.setGoodResume(calculateIfGoodResume(applicant));
        return super.save(applicant);
    }
    
    //Calculate if applicant has good resume or not
    private boolean calculateIfGoodResume(Applicant applicant){
        //TODO: Implement based on applicant features
        return new Random().nextBoolean();
    }
    
}
