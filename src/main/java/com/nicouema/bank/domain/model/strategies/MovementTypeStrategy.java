package com.nicouema.bank.domain.model.strategies;

public interface MovementTypeStrategy {

    Double calculateCurrentBalance(Double balanceToUpdate, Double amount, Double minimumBalanceAllowed);

    Double calculateCurrentDebt(Double debtToUpdate, Double debt);
}