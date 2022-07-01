package com.nicouema.bank.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private final Object resourceId;
}
