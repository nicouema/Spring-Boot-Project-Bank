package com.nicouema.bank.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsufficientBalanceException extends RuntimeException{
    private final String message;
}
