package br.com.ist.job.openings.repository;

import br.com.ist.job.openings.model.SuperEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jonny
 * @param <T>
 */
@Repository
public interface SuperRepo<T extends SuperEntity> extends JpaRepository<T, UUID> {
    
}
