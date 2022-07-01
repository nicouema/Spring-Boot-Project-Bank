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
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "movement_type")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE movement_type SET is_active=false")
@EntityListeners(AuditListener.class)
public class MovementType implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "movementType", fetch = FetchType.LAZY)
    private Set<BankStatement> statements;

    @Embedded
    private Audit audit;
}
