package ma.enset.ebankingbackend;

import ma.enset.ebankingbackend.dtos.BankAccountDTO;
import ma.enset.ebankingbackend.dtos.CurrentBankAccountDTO;
import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.dtos.SavingBankAccountDTO;
import ma.enset.ebankingbackend.entities.*;
import ma.enset.ebankingbackend.enums.AccountStatus;
import ma.enset.ebankingbackend.enums.OperationType;
import ma.enset.ebankingbackend.exceptions.BalanceNotSufficientException;
import ma.enset.ebankingbackend.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.repositories.AccountOpeartionRepository;
import ma.enset.ebankingbackend.repositories.BankAccountRepository;
import ma.enset.ebankingbackend.repositories.CustomerRepository;
import ma.enset.ebankingbackend.services.BankAccountService;
import ma.enset.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(EBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

        return args -> {

            Stream.of("Moussa","Yassin","Aicha","Oumaima").forEach(name->{

                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });

            bankAccountService.listCustomers().forEach(customer -> {

                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*12000,5.5,customer.getId());
                    List<BankAccountDTO> bankAccounts=bankAccountService.bankAccountList();
                    for(BankAccountDTO bankAccount:bankAccounts){

                        for(int i=0;i<10;i++){
                            String accountId;
                            if(bankAccount instanceof SavingBankAccountDTO) {
                                accountId=((SavingBankAccountDTO) bankAccount).getId();

                            }

                            else {
                                accountId=((CurrentBankAccountDTO) bankAccount).getId();
                            }
                            bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");

                            bankAccountService.debit(accountId,10000+Math.random()*90000,"Debit");

                        }

                    }
                } catch (CustomerNotFoundException|BalanceNotSufficientException|BankAccountNotFoundException e) {
                    e.printStackTrace();
                }

            });

        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOpeartionRepository accountOpeartionRepository){

        return args -> {

            // creation de trois clients
            Stream.of("HASSAN","YASSIN","AICHA").forEach(name->{

                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);

            });

            //pour chaque client on va enregestre un compte coutant et un compte epargne
            customerRepository.findAll().forEach(cust -> {

                //compte courant
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreatDate(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                //compte epargne
                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreatDate(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            //il faut maintenant ajoutee des operations

            bankAccountRepository.findAll().forEach(acc->{

                // cree 5 opeartions pour chaque compte

                for(int i=0;i<10;i++){

                    AccountOperation accountOperation=new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOpeartionRepository.save(accountOperation);
                }

            });

        };
    }

}
