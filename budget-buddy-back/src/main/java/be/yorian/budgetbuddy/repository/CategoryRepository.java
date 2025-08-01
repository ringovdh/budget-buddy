package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByLabelContainingIgnoreCase(String label, Pageable pageable);
}
