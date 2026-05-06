package com.finance.app.repository;

import com.finance.app.model.Transaction;
import com.finance.app.model.Transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions for a user, newest first
    List<Transaction> findByUserIdOrderByDateDescCreatedAtDesc(Long userId);

    // Get transactions by type for a user
    List<Transaction> findByUserIdAndTypeOrderByDateDesc(Long userId, TransactionType type);

    // Get transactions within a date range
    List<Transaction> findByUserIdAndDateBetweenOrderByDateDesc(Long userId, LocalDate start, LocalDate end);

    // Get transactions by category
    List<Transaction> findByUserIdAndCategoryIgnoreCaseOrderByDateDesc(Long userId, String category);

    // Sum income for a specific month/year
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'INCOME' " +
           "AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    BigDecimal sumIncomeByMonth(@Param("userId") Long userId,
                                @Param("year") int year,
                                @Param("month") int month);

    // Sum expenses for a specific month/year
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "AND YEAR(t.date) = :year AND MONTH(t.date) = :month")
    BigDecimal sumExpensesByMonth(@Param("userId") Long userId,
                                  @Param("year") int year,
                                  @Param("month") int month);

    // Monthly stats for overview chart (last 12 months)
    @Query(value = """
        SELECT EXTRACT(YEAR FROM date) as year,
               EXTRACT(MONTH FROM date) as month,
               SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) as income,
               SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) as expenses
        FROM transactions
        WHERE user_id = :userId AND date >= :fromDate
        GROUP BY EXTRACT(YEAR FROM date), EXTRACT(MONTH FROM date)
        ORDER BY year, month
        """, nativeQuery = true)
    List<Object[]> getMonthlyStats(@Param("userId") Long userId,
                                   @Param("fromDate") LocalDate fromDate);

    // Sum by category for a month
    @Query("SELECT t.category, COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
           "AND YEAR(t.date) = :year AND MONTH(t.date) = :month " +
           "GROUP BY t.category")
    List<Object[]> sumByCategoryForMonth(@Param("userId") Long userId,
                                         @Param("year") int year,
                                         @Param("month") int month);
}
