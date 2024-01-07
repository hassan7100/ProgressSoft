package org.progresssoft.Model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Status {
    public enum StatusType{
        SUCCESS,PARTIALSUCCESS, FAILED
    }
    private StatusType statusType;
    private String message;
    private List<Deal> failedDeals;
}
