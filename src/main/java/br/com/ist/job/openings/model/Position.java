package br.com.ist.job.openings.model;

import br.com.ist.job.openings.model.enums.PositionType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@EqualsAndHashCode(of="id", callSuper = true)
@Entity
@Table(name = "tbl_postition")
public class Position  extends SuperEntity implements Serializable  {

    @NotEmpty(message = "{position.name.required}")
    private String name;
    private String location;

    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date validUntil;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    private List<ApplicantPosition> applicantsPosition;
}
