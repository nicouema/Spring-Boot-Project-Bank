package com.nicouema.bank.domain.model.strategies;

public class DepositStrategy implements MovementTypeStrategy{

    @Override
    public Double calculateCurrentBalance(Double balanceToUpdate, Double amount, Double minimumBalanceAllowed) {
        return balanceToUpdate + amount;
    }

    @Override
    public Double calculateCurrentDebt(Double debtToUpdate, Double debt) {
        return debtToUpdate;
    }
}
