package ma.enset.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.ebankingbackend.enums.OperationType;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor

public class AccountOperation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date operationDate;

    private double amount ;

    //le type d'operation soit debit ou bien credit il faut utilisee un type enumer TypeOperation

   @Enumerated(EnumType.STRING)  //cette annotation pour afficher le type format String dans la base de donnee
    private OperationType type;

    //une operation concerne un compte alors il faut declarer un objet BankAccount

    @ManyToOne
    private BankAccount bankAccount;


    private String description;


}
