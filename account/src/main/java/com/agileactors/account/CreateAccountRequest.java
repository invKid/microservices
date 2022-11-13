package com.agileactors.account;

import java.time.LocalDateTime;

public record CreateAccountRequest(Double balance, String currency) {
}
