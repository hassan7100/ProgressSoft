package org.progresssoft.Controller;


import org.progresssoft.Model.Status;
import org.progresssoft.Utils.CsvToRowsHelper;
import org.progresssoft.Service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DealController {
    private final StoreService storeService;
    private final CsvToRowsHelper csvToRowsHelper;

    public DealController(StoreService storeService, CsvToRowsHelper csvToRowsHelper) {
        this.storeService = storeService;
        this.csvToRowsHelper = csvToRowsHelper;
    }

    @PostMapping("/upload")
    public ResponseEntity<Status> insertDeals(@RequestParam("file") MultipartFile file) {
        Status status = storeService.addDeals(file);
        return ResponseEntity.ok(status);
    }
}
