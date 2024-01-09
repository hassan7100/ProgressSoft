package org.progresssoft.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.List;
import org.progresssoft.Model.Deal;

import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionSet {
    private LinkedList<Deal> successfulDeals = new LinkedList<>();
    private final StringBuilder message = new StringBuilder();
    public void addMessage(String message){
        this.message.append(message + "\n");
    }
}
