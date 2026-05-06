package com.finance.app.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.finance.app.model.Transaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private Transaction.TransactionType type;
    private String category;
    private String description;
    private Transaction.TransactionStatus status;
    private LocalDate date;
    private LocalDateTime createdAt;
}