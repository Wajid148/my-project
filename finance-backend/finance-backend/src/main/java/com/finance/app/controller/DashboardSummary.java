package com.finance.app.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardSummary {

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpenses;
    private BigDecimal totalBalance;
    private BigDecimal spendingLimit;
    private BigDecimal spentAmount;
    private double spentPercentage;

    private BigDecimal foodAndHealth;
    private BigDecimal entertainment;
    private BigDecimal shopping;
    private BigDecimal investment;
    private BigDecimal transport;
    private BigDecimal utilities;
    private BigDecimal other;
}