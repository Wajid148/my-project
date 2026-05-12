package com.finance.app.controller;

import com.finance.app.model.Transaction;
import com.finance.app.model.Transaction.TransactionStatus;
import com.finance.app.model.Transaction.TransactionType;
import com.finance.app.model.User;
import com.finance.app.repository.TransactionRepository;
import com.finance.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    // GET all transactions for logged in user
    @GetMapping
    public ResponseEntity<?> getAllTransactions(Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow();
            List<Transaction> transactions = transactionRepository.findByUser(user);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // POST create new transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(
            @RequestBody Map<String, String> request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElseThrow();

            Transaction transaction = Transaction.builder()
                    .user(user)
                    .amount(new BigDecimal(request.get("amount")))
                    .type(TransactionType.valueOf(request.get("type")))
                    .category(request.get("category"))
                    .description(request.get("description"))
                    .status(TransactionStatus.valueOf(
                            request.getOrDefault("status", "SUCCESS")))
                    .date(LocalDate.parse(request.get("date")))
                    .build();

            return ResponseEntity.ok(transactionRepository.save(transaction));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // DELETE transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            transactionRepository.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}