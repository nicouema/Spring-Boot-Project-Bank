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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "user")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE user SET is_active=false, deleted_at=CURRENT_TIMESTAMP() WHERE user_id=?")
@EntityListeners(AuditListener.class)
public class User implements Auditable, UserDetails {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;

    @OneToOne(mappedBy = "user")
    private Client client;

    @Embedded
    private Audit audit;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null) {
            return Collections.singleton(this.role);
        }
        return Collections.emptySet();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return audit.getIsActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User aUser = (User) o;
        return Objects.equals(id, aUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
