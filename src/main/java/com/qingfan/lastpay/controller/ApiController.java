package com.qingfan.lastpay.controller;

import com.qingfan.lastpay.model.RequestRecord;
import com.qingfan.lastpay.repository.RequestRecordRepository;
import com.qingfan.lastpay.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Slf4j
@Controller
public class ApiController {

    private final Random random = new Random();

    @Autowired
    private ApiService apiService;

    @Autowired
    private RequestRecordRepository requestRecordRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/request")
    public String handleRequest(@RequestParam String customerName,
                                @RequestParam String model,
                                @RequestParam double price,
                                Model uiModel) {

        // mock exception: 20% call bank api occur error, throw exception
        if (random.nextDouble() < 0.2) {
            throw new RuntimeException("Bank service is temporarily unavailable.");
        }

        // call dealer
        Map<String, Object> dealer = apiService.callDealer(model, price);

        // call insurance
        Map<String, Object> insurance = apiService.callInsurance(model, price);

        // call bank
        Map<String, Object> bank = apiService.callBank(customerName, price);

        // save to db
        RequestRecord record = new RequestRecord();
        record.setCustomerName(customerName);
        record.setCarModel(model);
        record.setCarPrice(price);
        record.setCarSpecs(String.valueOf(dealer.get("specs")));
        record.setInsurancePremium(((Number) insurance.getOrDefault("premium", 0)).doubleValue());
        record.setLoanApproved(Boolean.TRUE.equals(bank.get("approved")));
        record.setBankMessage(String.valueOf(bank.get("message")));
        record.setCreatedAt(LocalDateTime.now());
        requestRecordRepository.save(record);

        // assemble UI
        uiModel.addAttribute("dealer", dealer);
        uiModel.addAttribute("insurance", insurance);
        uiModel.addAttribute("bank", bank);
        uiModel.addAttribute("record", record);

        return "summary";

    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("records", requestRecordRepository.findAll());
        return "history";
    }

}
