package com.nicouema.bank.domain.model;

import com.nicouema.bank.common.exception.InsufficientBalanceException;
import com.nicouema.bank.domain.model.audit.Audit;
import com.nicouema.bank.domain.model.audit.AuditListener;
import com.nicouema.bank.domain.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "account")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE account SET is_active=false WHERE branch_id=?")
@EntityListeners(AuditListener.class)
public class Account implements Auditable {

    @EmbeddedId
    private AccountId accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", nullable = false, updatable = false, insertable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id",nullable = false)
    private Client client_;

    @Column(name = "minimum_balance_allowed")
    private Double minimumBalanceAllowed;

    @Column(name = "initial_balance")
    private Double initialBalance;

    @Column(name = "current_balance")
    private Double currentBalance;

    @Column(name = "debt")
    private Double debt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    @ToString.Exclude
    private Set<BankStatement> statements;

    @Embedded
    private Audit audit;
    

    public Account updateCurrentBalance() {

        Double currentBalance = 0.00;
        Double currentDebt = 0.00;

        for (BankStatement statement:this.getStatements()) {
            Long movementId = statement.getMovementType().getId();
            Double movementAmount = statement.getAmount();
            if (movementId == 1L) {
                currentBalance += movementAmount;
            } else if (movementId == 2L) {
                currentDebt += movementAmount;
            } else if (movementId == 3L) {
                if (currentBalance - movementAmount >= this.getMinimumBalanceAllowed()){
                    currentBalance -= movementAmount;
                }
                else {
                    throw new InsufficientBalanceException("There is not enough balance in your account");
                }
            }
        }
        this.setCurrentBalance(currentBalance);
        this.setDebt(currentDebt);
        return this;
    }
}
