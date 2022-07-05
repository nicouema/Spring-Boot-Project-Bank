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
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "client")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE FROM client SET is_active=false WHERE client_id=?")
@EntityListeners(AuditListener.class)
public class Client implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id", nullable = false)
    private DocumentType docType;

    @Column(name = "doc_number", nullable = false)
    private String docNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private Integer streetNumber;

    @OneToMany(mappedBy = "client_", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Account> accounts;

    @Embedded
    private Audit audit;
}
