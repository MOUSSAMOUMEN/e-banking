package ma.enset.ebankingbackend.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("SA")
@AllArgsConstructor @NoArgsConstructor
public class SavingAccount extends BankAccount{

    //SavinAccount c est aussi un compte qui possede un attribut plus de type double
    // -> taux d'interet (interestRate)

    private double interestRate;
}
