package com.nicouema.bank.domain.model;

import com.nicouema.bank.domain.model.audit.Audit;
import com.nicouema.bank.domain.model.audit.AuditListener;
import com.nicouema.bank.domain.model.audit.Auditable;
import com.nicouema.bank.domain.model.strategies.ChargeStrategy;
import com.nicouema.bank.domain.model.strategies.DepositStrategy;
import com.nicouema.bank.domain.model.strategies.MovementTypeStrategy;
import com.nicouema.bank.domain.model.strategies.WithdrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "statement")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE statement SET is_active=false, deleted_at=CURRENT_TIMESTAMP() WHERE statement_id=?")
@EntityListeners(AuditListener.class)
public class BankStatement implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statement_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "account_id", referencedColumnName = "account_id"),
            @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")})
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movement_type_id", referencedColumnName = "movement_id")
    private MovementType movementType;

    private Double amount;

    @Transient
    private MovementTypeStrategy strategy;

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

    public void createStrategyInterface(){
        if (movementType.getId() == 1L) strategy = new DepositStrategy();
        if (movementType.getId() == 2L) strategy = new ChargeStrategy();
        if (movementType.getId() == 3L) strategy = new WithdrawStrategy();
    }

    public Double calculateCurrentBalance(Double oldBalance, Double minimumAllowed) {
        return strategy.calculateCurrentBalance(oldBalance, amount, minimumAllowed);
    }

    public Double calculateCurrentDebt(Double oldDebt) {
        return strategy.calculateCurrentDebt(oldDebt, amount);
    }
}
