package br.com.ist.job.openings.controller;

import br.com.ist.job.exception.BusinessException;
import br.com.ist.job.openings.model.Applicant;
import br.com.ist.job.openings.service.ApplicantService;
import br.com.ist.job.utils.Constants;
import java.io.Serializable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(Constants.API_VERSION + "/applicants")
@RequiredArgsConstructor
public class ApplicantController implements Serializable {

    private final ApplicantService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Applicant> list(@RequestBody(required = false) Applicant applicant,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20, page = 0) Pageable pageable) {
        return service.list(applicant, pageable);
    }

    @PostMapping
    public ResponseEntity<Applicant> save(@RequestBody Applicant applicant) {
        return ResponseEntity.accepted().body(service.save(applicant));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }

    @PostMapping("/apply")
    public ResponseEntity<Applicant> apply(@RequestBody Applicant applicant) throws BusinessException {
        return ResponseEntity.accepted().body(service.apply(applicant));
    }
}
