package com.finance.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  // ← ADD THIS LINE
public class FinanceDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinanceDashboardApplication.class, args);
    }
}