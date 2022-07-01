package com.nicouema.bank.domain.model.audit;

public interface Auditable {
    Audit getAudit();
    void setAudit(Audit audit);
}
