package com.nicouema.bank.domain.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
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
        AccountId anAccountId = (AccountId) o;
        return Objects.equals(id, anAccountId.getId()) &&
                Objects.equals(branch, anAccountId.getBranch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch);
    }

}
