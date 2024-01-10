package org.progresssoft.Controller;


import org.progresssoft.Service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DealController {
    private final StoreService storeService;
    public DealController(StoreService storeService) {
        this.storeService = storeService;
    }
    @PostMapping("/upload")
    public String insertDeals(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("status", storeService.addDeals(file));
        return "index";
    }
}
