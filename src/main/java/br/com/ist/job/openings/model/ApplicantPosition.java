package br.com.ist.job.openings.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "applicant_position")
public class ApplicantPosition extends SuperEntity implements Serializable {

    private BigDecimal salaryExpectation;
    private boolean salaryOutRanged;

    @ManyToOne(fetch = FetchType.LAZY)
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Position position;
}
