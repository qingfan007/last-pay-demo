package com.qingfan.lastpay.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RequestRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;
    private String carModel;
    private double carPrice;

    @Column(length = 1000)
    private String carSpecs;

    private double insurancePremium;
    private boolean loanApproved;
    private String bankMessage;

    private LocalDateTime createdAt;
}
