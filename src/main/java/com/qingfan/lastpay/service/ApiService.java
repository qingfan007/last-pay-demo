package com.qingfan.lastpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> callDealer(String model, double price) {
        String url = String.format("http://localhost:8080/api/dealer?model=%s&price=%s", model, price);
        Map response = restTemplate.getForObject(url, Map.class);
        return response == null ? new HashMap<>() : response;
    }

    public Map<String, Object> callInsurance(String model, double price) {
        String url = String.format("http://localhost:8080/api/insurance?model=%s&price=%s", model, price);
        Map response = restTemplate.getForObject(url, Map.class);
        return response == null ? new HashMap<>() : response;
    }

    public Map<String, Object> callBank(String customer, double price) {
        String url = String.format("http://localhost:8080/api/bank?customer=%s&price=%s", customer, price);
        Map response = restTemplate.getForObject(url, Map.class);
        return response == null ? new HashMap<>() : response;
    }

}
