package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findByDateAndNumber(LocalDate date, String number);

    @Query("""
            select t from Transaction t
            where month (t.date) = ?1
            and year(t.date) = ?2""")
    List<Transaction> findByDateContainingYearAndMonth(int month, int year);

    @Query("""
            select t from Transaction t
            where t.category.id = ?1
            and year(t.date) = ?2""")
    List<Transaction> findByCategoryIdAndDateContainingYear(Long categoryId, int year);

    List<Transaction> findByCategoryId(Long categoryId);

    @Query("""
            select t from Transaction t
            where year(t.date) = ?1""")
    List<Transaction> findByDateContainingYear(int year);

    List<Transaction> findByProjectProjectname(String projectname);

    @Query("""
            select t from Transaction t
            where month (t.date) = ?1
            and year(t.date) = ?2
            and t.project is not null""")
    List<Transaction> findByDateContainingYearAndMonthAndProjectNotNull(int month, int year);

}
