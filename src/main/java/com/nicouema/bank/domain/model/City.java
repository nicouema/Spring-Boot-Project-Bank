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
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "city")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE city SET is_active=false, deleted_at=CURRENT_TIMESTAMP() WHERE city_id=?")
@EntityListeners(AuditListener.class)
public class City implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "city",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Branch> branches;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City aCity = (City) o;
        return Objects.equals(id, aCity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
