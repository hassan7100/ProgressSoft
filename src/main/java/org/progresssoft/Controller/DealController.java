package org.progresssoft.Controller;


import org.progresssoft.Model.Deal;
import org.progresssoft.Model.Status;
import org.progresssoft.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
public class DealController {
    @Autowired
    private StoreService storeService;
    @PostMapping("/insert")
    public ResponseEntity<Status> insertDeals(@RequestBody List<Deal> deals){
        Status status = storeService.addDeals(deals);
        return switch (status.getStatusType()) {
            case SUCCESS, PARTIALSUCCESS -> ResponseEntity.ok(status);
            default -> ResponseEntity.badRequest().body(status);
        };

    }
}
