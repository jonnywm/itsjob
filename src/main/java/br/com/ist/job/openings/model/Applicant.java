package br.com.ist.job.openings.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@Table(name = "applicant")
public class Applicant extends SuperEntity implements Serializable {

    @NotEmpty(message = "{applicant.name.required}")
    private String name;
    @NotEmpty(message = "{applicant.email.required}")
    private String email;
    
    private boolean goodResume;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant")
    private List<ApplicantPosition> applicantPositions;
}
