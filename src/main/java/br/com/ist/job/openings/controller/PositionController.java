package br.com.ist.job.openings.controller;

import br.com.ist.job.openings.model.Position;
import br.com.ist.job.openings.service.PositionService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping(Constants.API_VERSION + "/positions")
@RequiredArgsConstructor
public class PositionController implements Serializable {

    private final PositionService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Position> list(@RequestBody(required = false) Position position,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20, page = 0) Pageable pageable) {
        return service.list(position, pageable);
    }
    
    @GetMapping("/open")
    @ResponseStatus(HttpStatus.OK)
    public Page<Position> getOpenPositions(@RequestParam("name") String name, 
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 20, page = 0) Pageable pageable) {
        return service.getOpenPositions(name, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void save(@RequestBody Position position) {
        service.save(position);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }
}
