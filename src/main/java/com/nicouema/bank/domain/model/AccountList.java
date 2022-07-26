package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class AccountList extends PageImpl<Account> {

    public AccountList(List<Account> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
