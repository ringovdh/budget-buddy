package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findBySearchtermContaining(String searchterm, Pageable pageable);

}
