package com.qingfan.lastpay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class MockApiController {

    private final Random random = new Random();

    @GetMapping("/dealer")
    public ResponseEntity<Map<String, Object>> dealer(@RequestParam String model, @RequestParam double price) throws InterruptedException {
        // simulate network delay
        Thread.sleep(100 + random.nextInt(700));
        Map<String, Object> result = new HashMap<>();
        result.put("model", model);
        result.put("price", price);
        result.put("specs", model + " - Gasoline, Auto");
        result.put("stock", random.nextInt(50) + 1);
        result.put("dealerTimestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/insurance")
    public ResponseEntity<Map<String, Object>> insurance(@RequestParam String model, @RequestParam double price) throws InterruptedException {
        Thread.sleep(100 + random.nextInt(600));
        Map<String, Object> result = new HashMap<>();
        double base = Math.max(500, price * 0.03);
        double premium = Math.round((base + random.nextDouble() * 300) * 100.0) / 100.0;
        result.put("model", model);
        result.put("price", price);
        result.put("premium", premium);
        result.put("insuranceTimestamp", System.currentTimeMillis());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/bank")
    public ResponseEntity<Map<String, Object>> bank(@RequestParam String customer, @RequestParam double price) throws InterruptedException {
        Thread.sleep(100 + random.nextInt(800));
        Map<String, Object> result = new HashMap<>();
        boolean approved = price < 50000;
        result.put("customer", customer);
        result.put("price", price);
        result.put("approved", approved);
        result.put("rate", approved ? 3.5 + random.nextDouble() * 2.5 : null);
        result.put("bankTimestamp", System.currentTimeMillis());
        result.put("message", approved ? "APPROVED" : "REJECTED");
        return ResponseEntity.ok(result);
    }


}
