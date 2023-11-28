package br.com.ist.job.openings.model;

import br.com.ist.job.annotation.HasUniqueConstraint;
import br.com.ist.job.annotation.Unique;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
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
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
@HasUniqueConstraint
public class Applicant extends SuperEntity implements Serializable {

    @NotEmpty(message = "{applicant.name.required}")
    private String name;
    @NotEmpty(message = "{applicant.email.required}")
    @Unique(message = "applicant.email.duplicated")
    private String email;
    
    private boolean goodResume;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ApplicantPosition> applicantPositions;
}
