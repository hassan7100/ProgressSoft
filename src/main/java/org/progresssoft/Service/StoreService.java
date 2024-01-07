package org.progresssoft.Service;


import org.progresssoft.Model.Deal;
import org.progresssoft.Model.Status;
import org.progresssoft.Repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StoreService {
    @Autowired
    private Map<Long, Deal> indexMap;
    @Autowired
    private DealRepository dealRepository;
    public Status addDeals(List<Deal> deals) {
        List<Deal> failedDeals = new ArrayList<>();
        deals.forEach(deal ->
                {
                    if (!indexMap.containsKey(deal.getId())) {
                        indexMap.put(deal.getId(), deal);
                        dealRepository.save(deal);
                    } else{
                        failedDeals.add(deal);
                    }
                }
                );
        if(failedDeals.isEmpty()){
            return Status.builder()
                    .statusType(Status.StatusType.SUCCESS)
                    .message("All deals added successfully")
                    .failedDeals(failedDeals)
                    .build();
        }
        else if(failedDeals.size() == deals.size()){
            return Status.builder()
                    .statusType(Status.StatusType.FAILED)
                    .message("All deals failed to add")
                    .failedDeals(failedDeals)
                    .build();
        }
        else{
            return Status.builder()
                    .statusType(Status.StatusType.PARTIALSUCCESS)
                    .message("Some deals failed to add")
                    .failedDeals(failedDeals)
                    .build();
        }
    }
    public List<Deal> getAllDeals(){
        return indexMap.values().stream().toList();
    }
}
