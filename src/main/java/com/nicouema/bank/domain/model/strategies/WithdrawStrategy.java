package com.nicouema.bank.domain.model.strategies;

import com.nicouema.bank.common.exception.InsufficientBalanceException;

public class WithdrawStrategy implements MovementTypeStrategy{

    @Override
    public Double calculateCurrentBalance(Double balanceToUpdate, Double amount, Double minimumBalanceAllowed) {
        if (balanceToUpdate - amount >= minimumBalanceAllowed){
                    return balanceToUpdate - amount;
                }
                else {
                    throw new InsufficientBalanceException("There is not enough balance in your account");
                }
    }

    @Override
    public Double calculateCurrentDebt(Double debtToUpdate, Double debt) {
        return debtToUpdate;
    }
}
