package org.progresssoft.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deal")
public class Deal {
    public enum Currency {
        AED, SAR, USD, INR, PKR
    }
    @Id
    private Long id;
    @Column(name = "from_currency")
    private Currency fromCurrency;
    @Column(name = "to_currency")
    private Currency toCurrency;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "timestamp")
    private Date timestamp;
    @Override
    public boolean equals(Object o) {
        try {
            return this.getId() == ((Deal) o).getId();
        }catch (Exception e){
            return false;
        }
    }
}
