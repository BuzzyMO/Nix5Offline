package org.example.finance.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public interface OperationService {
    void createOperation(Long accountId, Long categoryId, BigDecimal money, Instant timestamp, Boolean isIncome);
    void writeCsv(Long accountId, LocalDateTime fromTime, LocalDateTime toTime);
}
