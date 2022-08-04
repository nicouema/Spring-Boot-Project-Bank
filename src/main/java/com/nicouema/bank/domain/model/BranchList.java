package com.nicouema.bank.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BranchList extends PageImpl<Branch> {
    public BranchList(List<Branch> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
