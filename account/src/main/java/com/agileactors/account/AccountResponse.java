package com.agileactors.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponse implements Serializable {

    private  Integer id;
    private Double balance;
     

}
