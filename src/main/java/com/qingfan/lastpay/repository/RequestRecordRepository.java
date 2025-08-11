package com.qingfan.lastpay.repository;

import com.qingfan.lastpay.model.RequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRecordRepository extends JpaRepository<RequestRecord, Long> {
}
