package com.finance.app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.finance.app.model.Transaction;

@Data
public class TransactionRequest {

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private Transaction.TransactionType type;

    @NotBlank
    private String category;

    private String description;

    @NotNull
    private LocalDate date;
}