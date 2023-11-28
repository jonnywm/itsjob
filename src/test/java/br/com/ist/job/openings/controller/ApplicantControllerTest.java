package br.com.ist.job.openings.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import br.com.ist.job.exception.BusinessException;
import br.com.ist.job.openings.model.Applicant;
import br.com.ist.job.openings.service.ApplicantService;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class ApplicantControllerTest {

    private ApplicantController applicantController;
    private ApplicantService applicantService;

    @Before
    public void setUp() {
        applicantService = mock(ApplicantService.class);
        applicantController = new ApplicantController(applicantService);
    }

    @Test
    public void testList() {
        Applicant applicant = new Applicant();
        Pageable pageable = mock(Pageable.class);
        Page<Applicant> expectedPage = mock(Page.class);

        when(applicantService.list(applicant, pageable)).thenReturn(expectedPage);

        Page<Applicant> result = applicantController.list(applicant, pageable);

        assertEquals(expectedPage, result);
        verify(applicantService, times(1)).list(applicant, pageable);
    }

    @Test
    public void testSave() {
        Applicant applicant = new Applicant();
        Applicant savedApplicant = new Applicant();
        ResponseEntity<Applicant> expectedResponse = ResponseEntity.accepted().body(savedApplicant);

        when(applicantService.save(applicant)).thenReturn(savedApplicant);

        ResponseEntity<Applicant> result = applicantController.save(applicant);

        assertEquals(expectedResponse, result);
        verify(applicantService, times(1)).save(applicant);
    }

    @Test
    public void testDelete() {
        UUID id = UUID.randomUUID();

        applicantController.delete(id);

        verify(applicantService, times(1)).delete(id);
    }

    @Test
    public void testApply() throws BusinessException {
        Applicant applicant = new Applicant();
        Applicant appliedApplicant = new Applicant();
        ResponseEntity<Applicant> expectedResponse = ResponseEntity.accepted().body(appliedApplicant);

        when(applicantService.apply(applicant)).thenReturn(appliedApplicant);

        ResponseEntity<Applicant> result = applicantController.apply(applicant);

        assertEquals(expectedResponse, result);
        verify(applicantService, times(1)).apply(applicant);
    }
}