package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BankStatementList extends PageImpl<BankStatement> {
    public BankStatementList(List<BankStatement> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
