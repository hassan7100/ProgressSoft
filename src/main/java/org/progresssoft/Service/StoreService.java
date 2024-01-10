package org.progresssoft.Service;


import lombok.extern.slf4j.Slf4j;
import org.progresssoft.Model.Deal;
import org.progresssoft.Model.Status;
import org.progresssoft.Repository.DealRepository;
import org.progresssoft.Utils.ConversionSet;
import org.progresssoft.Utils.CsvToRowsHelper;
import org.progresssoft.Utils.RowsToDealsHelper;
import org.progresssoft.Utils.TimeSet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class StoreService {
    private final TimeSet<Deal> dealsCache;
    private final DealRepository dealRepository;
    public StoreService(DealRepository dealRepository) {
        this.dealsCache = new TimeSet<>(600000); // 10 minutes
        this.dealRepository = dealRepository;
    }
    public Status addDeals(MultipartFile file)  {
        try {
            log.info("Adding deals from file: " + file.getOriginalFilename());
            CsvToRowsHelper csvToRowsHelper = new CsvToRowsHelper();
            RowsToDealsHelper rowsToDealsHelper = new RowsToDealsHelper();
            List<String[]> csvRows = csvToRowsHelper.readCsvFile(file);
            ConversionSet conversionSet = rowsToDealsHelper.convertRowsToDeals(csvRows);
            Status status = new Status();
            if (conversionSet.getSuccessfulDeals().isEmpty()) {
                log.warn("No deals to add.");
                status.setStatusType(Status.StatusType.Failed);
                status.setMessage("No deals to add.");
                return status;
            }
            for (Deal deal : conversionSet.getSuccessfulDeals()) {
                if (dealsCache.contains(deal)) {
                    log.warn("Deal with id: " + deal.getId() + " already exists.");
                    conversionSet.addMessage("Deal with id: " + deal.getId() + " already exists.");
                } else {
                    dealRepository.findById(deal.getId()).ifPresentOrElse(
                            (d) -> {
                                log.warn("Deal with id:" + deal.getId() + " already exists.");
                                conversionSet.addMessage("Deal with id: " + deal.getId() + " already exists.");
                            },
                            () -> {
                                this.dealsCache.add(deal);
                                dealRepository.save(deal);
                            }
                    );
                }
            }
            if(conversionSet.getMessage().isEmpty()) {
                return Status.builder()
                        .statusType(Status.StatusType.Success)
                        .message("All deals added successfully.")
                        .build();
            }else{
                return Status.builder()
                        .statusType(Status.StatusType.PartialSuccess)
                        .message(conversionSet.getMessage().toString())
                        .build();
            }
        }catch (IOException e){
            log.warn("Error reading file.");
            return Status.builder()
                    .statusType(Status.StatusType.Failed)
                    .message("Error reading file.")
                    .build();
        }


    }

}
