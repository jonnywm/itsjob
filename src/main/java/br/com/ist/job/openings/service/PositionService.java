package br.com.ist.job.openings.service;

import br.com.ist.job.openings.model.Position;
import br.com.ist.job.openings.repository.PositionRepository;
import br.com.ist.job.openings.repository.SuperRepo;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PositionService extends SuperService<Position> implements Serializable {

    private final PositionRepository repository;

    @Override
    public SuperRepo<Position> getRepo() {
        return repository;
    }

    public Page<Position> getOpenPositions(String name, Pageable pageable) {
        Date now = new Date();
        return repository.findByNameContainingAndValidFromBeforeAndValidUntilAfterAndActiveIsTrue(name, now, now, pageable);
    }
}
