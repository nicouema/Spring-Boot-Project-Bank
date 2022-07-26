package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ClientList extends PageImpl<Client> {
    public ClientList(List<Client> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
