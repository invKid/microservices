package com.agileactors.account;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("{accountId}")
    public ResponseEntity<Account> getAccountById (@PathVariable("accountId") Integer accountId) {
        Account account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Account> createAccount (@RequestBody CreateAccountRequest createAccountRequest) {
        log.info("new account creation {}", createAccountRequest);
         Account newAccount = accountService.createAccount(createAccountRequest);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountResponse accountResponse) {
        Account updateAccount =  accountService.updateAccount(accountResponse);
        return new ResponseEntity<>(updateAccount, HttpStatus.OK);

    }



}
