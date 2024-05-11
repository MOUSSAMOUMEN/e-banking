package ma.enset.ebankingbackend.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("CA")
@NoArgsConstructor @AllArgsConstructor
public class CurrentAccount extends BankAccount{

    //le CurrentAccount est aussi un compte qui posside un attrbut plus OverDraft

    private double overDraft;
}
