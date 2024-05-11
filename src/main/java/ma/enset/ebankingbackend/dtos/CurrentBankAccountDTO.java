package ma.enset.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.ebankingbackend.entities.AccountOperation;
import ma.enset.ebankingbackend.entities.Customer;
import ma.enset.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data


public  class CurrentBankAccountDTO extends BankAccountDTO {


    private String id;
    private double balance;
    private Date creatDate;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;






}
