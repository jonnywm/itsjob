package br.com.ist.job.openings.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ist.job.exception.BusinessException;
import br.com.ist.job.openings.model.Applicant;
import br.com.ist.job.openings.model.ApplicantPosition;
import br.com.ist.job.openings.model.Position;
import br.com.ist.job.openings.repository.ApplicantRepository;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author jonny
 */
public class ApplicantServiceTest {

    @Mock
    private ApplicantRepository repository;

    @InjectMocks
    private ApplicantService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApplyWithValidApplicant() throws BusinessException {
        Applicant applicant = new Applicant();
        ApplicantPosition position = ApplicantPosition.builder()
                .salaryExpectation(BigDecimal.valueOf(50000))
                .build();
        position.setPosition(Position.builder()
                .name("Software Engineer")
                .minSalary(BigDecimal.valueOf(4000))
                .maxSalary(BigDecimal.valueOf(6000))
                .build());
        List<ApplicantPosition> positions = new ArrayList<>();
        positions.add(position);
        applicant.setApplicantPositions(positions);

        Applicant savedApplicant = Applicant.builder().build();
        savedApplicant.setId(UUID.randomUUID());
        savedApplicant.setApplicantPositions(positions);

        when(repository.findById(UUID.randomUUID())).thenReturn(Optional.of(savedApplicant));
        when(repository.save(applicant)).thenReturn(savedApplicant);

        Applicant result = service.apply(applicant);

        assertNotNull(result);
        assertEquals(savedApplicant, result);
    }

    @Test(expected = BusinessException.class)
    public void testApplyWithNullApplicant() throws BusinessException {
        service.apply(null);
    }

    @Test(expected = BusinessException.class)
    public void testApplyWithEmptyPositions() throws BusinessException {
        Applicant applicant = new Applicant();
        service.apply(applicant);
    }

    @Test
    public void testSaveWithGoodResume() {
        UUID uuid = UUID.randomUUID();
        Applicant applicant = Applicant.builder().goodResume(true).build();
        applicant.setId(uuid);

        Applicant savedApplicant = new Applicant();
        savedApplicant.setId(uuid);
        savedApplicant.setGoodResume(true);

        when(repository.save(applicant)).thenReturn(savedApplicant);

        Applicant result = service.save(applicant);

        assertNotNull(result);
        assertEquals(savedApplicant, result);
    }

}
