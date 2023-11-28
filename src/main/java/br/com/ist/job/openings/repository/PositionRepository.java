package br.com.ist.job.openings.repository;

import br.com.ist.job.openings.model.Position;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
public interface PositionRepository extends SuperRepo<Position> {

    Page<Position> findByNameContainingAndValidFromBeforeAndValidUntilAfterAndActiveIsTrue(String name, Date from, Date until, Pageable pageable);
    
    Page<Position> findByValidFromBeforeAndValidUntilAfterAndActiveIsTrue(Date from, Date until, Pageable pageable);
}
