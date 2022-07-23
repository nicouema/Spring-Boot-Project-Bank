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
@Entity(name = "document_type")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE document_type SET is_active=false WHERE document_type_id=?")
@EntityListeners(AuditListener.class)
public class DocumentType implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_type_id", nullable = false)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "docType", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Client> clients;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentType aDocumentType = (DocumentType) o;
        return Objects.equals(id, aDocumentType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
