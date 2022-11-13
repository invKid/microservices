package com.agileactors.transaction;

import com.agileactors.transaction.exception.RestApiRequestException;
import com.agileactors.transaction.exception.RestTemplateResponseErrorHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;


@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public void createTransaction(TransactionRequest transactionRequest)  {

       restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());


        AccountResponse sourceAccountResponse = restTemplate.getForObject(
                "http://localhost:8080/account/{accountId}",
                AccountResponse.class,
                transactionRequest.sourceAccountId());


        AccountResponse destinationAccountResponse = restTemplate.getForObject(
                "http://localhost:8080/account/{accountId}",
                AccountResponse.class,
                transactionRequest.targetAccountId());

        log.info("Source Account:" + sourceAccountResponse);
        log.info("Target Account:" + destinationAccountResponse);

        if(sourceAccountResponse.getId() == null) {
            throw new RestApiRequestException("The Source Account "+transactionRequest.sourceAccountId()+" doesn't exist");
        }
        if(destinationAccountResponse.getId() == null) {
            throw new RestApiRequestException("The Target Account "+transactionRequest.targetAccountId()+" doesn't exist");
        }

        if (sourceAccountResponse.getId() == destinationAccountResponse.getId()) {
            throw new RestApiRequestException("The Accounts is the same.");
        }


        if (sourceAccountResponse.getBalance() >= transactionRequest.amount()) {
            sourceAccountResponse.setBalance(sourceAccountResponse.getBalance() - transactionRequest.amount());
            destinationAccountResponse.setBalance(destinationAccountResponse.getBalance() + transactionRequest.amount());


            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


            restTemplate.exchange(
                            "http://localhost:8080/account/update",
                            HttpMethod.PUT,
                            new HttpEntity<AccountResponse>(sourceAccountResponse, headers),
                            String.class)
                    .getBody();


            restTemplate.exchange(
                            "http://localhost:8080/account/update",
                            HttpMethod.PUT,
                            new HttpEntity<AccountResponse>(destinationAccountResponse, headers),
                            String.class)
                    .getBody();


            Transaction transaction = Transaction.builder()
                    .sourceAccountId(transactionRequest.sourceAccountId())
                    .targetAccountId(transactionRequest.targetAccountId())
                    .amount(transactionRequest.amount())
                    .currency(transactionRequest.currency())
                    .build();
            transactionRepository.save(transaction);

        } else {
            throw new RestApiRequestException("A balance is less than the transaction amount.");
        }
    }
}
