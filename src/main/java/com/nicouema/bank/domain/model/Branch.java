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

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "branch")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE branch SET is_active=false, deleted_at=CURRENT_TIMESTAMP() WHERE branch_id = ?")
@EntityListeners(AuditListener.class)
public class Branch implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    private Long id;

    private String name;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private Integer streetNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    @ToString.Exclude
    private Set<Account> accounts;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch aBranch = (Branch) o;
        return Objects.equals(id, aBranch.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Client> getClients() {
        List<Client> list = new ArrayList<>();
        for (Account account:this.getAccounts()) {
            list.add(account.getClient_());
        }
        return list;
    }
}
