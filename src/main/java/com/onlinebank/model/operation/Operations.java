package com.onlinebank.model.operation;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.List;

import static com.onlinebank.model.operation.OperationType.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Utility class to construct instances of {@link Operation}
 */
public final class Operations {
    private Operations() {
    }

    public static Operation create(OperationType operationType, List<String> accountNames, BigDecimal amount) {
        if (accountNames.size() != operationType.getAccountNumber())
            throw new IllegalArgumentException("Wrong number of accounts " + accountNames.size() +
                    " for operation: " + operationType);

        String account = accountNames.get(0);
        switch (operationType) {
            case TRANSFER:
                return transfer(account, amount, accountNames.get(1));
            case DEPOSIT:
                return deposit(account, amount);
            case WITHDRAWAL:
                return withdraw(account, amount);
            default:
                throw new IllegalStateException("Wrong operation type: " + operationType);
        }

    }
    public static Operation transfer(String fromAccount, BigDecimal amount, String toAccountName) {
        Preconditions.checkArgument(!fromAccount.equalsIgnoreCase(toAccountName),
                "Illegal TRANSFER operation to the same account: '" + fromAccount + "'");
        return new OperationImpl(TRANSFER, amount, asList(fromAccount, toAccountName));
    }

    public static Operation deposit(String account, BigDecimal amount) {
        return new OperationImpl(DEPOSIT, amount, singletonList(account));
    }

    public static Operation withdraw(String account, BigDecimal amount) {
        return new OperationImpl(WITHDRAWAL, amount, singletonList(account));
    }

}
