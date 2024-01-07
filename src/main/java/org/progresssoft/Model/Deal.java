package org.progresssoft.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deal")
public class Deal {
    public enum Currency{
        AED, SAR, USD, EGP, JPY, EUR, GBP
    }
    @Id
    private Long id;
    @Column(name = "from_currency")
    @Enumerated(EnumType.STRING)
    private Currency fromCurrency;
    @Column(name = "to_currency")
    @Enumerated(EnumType.STRING)
    private Currency toCurrency;
    @Column(name = "amount")
    private Double amount;
}
