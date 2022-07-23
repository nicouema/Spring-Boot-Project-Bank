package com.nicouema.bank.domain.model;

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
import java.util.Objects;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id",nullable = false)
    private Client client_;

    @Column(name = "minimum_balance_allowed")
    private Double minimumBalanceAllowed;

    @Column(name = "initial_balance")
    private Double initialBalance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    @ToString.Exclude
    private Set<BankStatement> statements;

    @Embedded
    private Audit audit;
}
