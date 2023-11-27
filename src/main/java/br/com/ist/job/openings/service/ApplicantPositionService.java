package br.com.ist.job.openings.service;

import br.com.ist.job.openings.model.ApplicantPosition;
import br.com.ist.job.openings.repository.ApplicantPositionRepository;
import br.com.ist.job.openings.repository.SuperRepo;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantPositionService extends SuperService<ApplicantPosition> implements Serializable {

    private final ApplicantPositionRepository repository;

    @Override
    public SuperRepo<ApplicantPosition> getRepo() {
        return repository;
    }
}
