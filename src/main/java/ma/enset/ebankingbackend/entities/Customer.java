package ma.enset.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    //un client peut avoir plusieurs comptes il faut declarer un list de comptes
    @OneToMany (mappedBy = "customer")
    @JsonProperty (access = JsonProperty.Access.READ_ONLY)
    private List<BankAccount> bankAccounts;
}
