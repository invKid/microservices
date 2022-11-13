package com.agileactors.account;

import com.agileactors.account.exception.RestApiRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;


    public Account createAccount(CreateAccountRequest createAccountRequest) {
        Account account = Account.builder()
                .balance(createAccountRequest.balance())
                .currency(createAccountRequest.currency())
                .createdAt(LocalDateTime.now()).
                build();
      return  accountRepository.save(account);
    }

    public Account getAccountById(Integer accountId) {
        Account account = accountRepository.findAccountById(accountId)
        		.orElseThrow(() -> new RestApiRequestException("Account by id " + accountId + " was not found"));
          return account;
    }


    public Account updateAccount(AccountResponse accountResponse) {
        Optional<Account> acc =  accountRepository.findAccountById(accountResponse.getId());
        Account account = acc.get();
        account.setBalance(accountResponse.getBalance());
        return accountRepository.save(account);

    }
}
