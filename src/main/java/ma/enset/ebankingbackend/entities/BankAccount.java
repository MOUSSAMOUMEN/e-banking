package ma.enset.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 5)
@NoArgsConstructor @AllArgsConstructor

public abstract class BankAccount {

    @Id //il faut pas mettre GeneratedValues car c est de type String
    private String id;
    private double balance;
    private Date creatDate;

    //le status est un type enumer alors il faut cree un type enumerat AccountStatus avec alt+Entrer
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    //un compte appartient a un client alors il faut declarer un objet de type Customer
    @ManyToOne
    private Customer customer;

    //un compte peut avoir plusieurs operations il faut declarer un liste AccountOperation

    @OneToMany (mappedBy = "bankAccount",fetch =FetchType.LAZY)
    private List<AccountOperation> accountOperations;





}
