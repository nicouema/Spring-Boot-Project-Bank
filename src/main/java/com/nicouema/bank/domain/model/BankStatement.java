package com.nicouema.bank.domain.model;

import com.nicouema.bank.domain.model.audit.Audit;
import com.nicouema.bank.domain.model.audit.AuditListener;
import com.nicouema.bank.domain.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "statement")
@Where(clause = "is_active=true AND operation_date_time=CURRENT_TIMESTAMP()")
@SQLDelete(sql = "UPDATE statement SET is_active=false WHERE statement_id=?")
@EntityListeners(AuditListener.class)
public class BankStatement implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statement_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")})
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_type_id", referencedColumnName = "movement_id")
    private MovementType movementType;

    private Double amount;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankStatement aBankStatement = (BankStatement) o;
        return Objects.equals(id, aBankStatement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
