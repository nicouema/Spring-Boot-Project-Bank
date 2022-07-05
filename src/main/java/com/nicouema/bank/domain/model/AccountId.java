package com.nicouema.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class AccountId implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "branch_id", nullable = false)
    private Long branch;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return Objects.equals(id, accountId.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
