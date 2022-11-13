package com.agileactors.transaction;

public record TransactionRequest(Integer sourceAccountId, Integer targetAccountId, Double amount, String currency) {
}
