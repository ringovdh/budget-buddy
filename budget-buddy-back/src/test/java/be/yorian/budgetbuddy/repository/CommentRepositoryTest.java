package be.yorian.budgetbuddy.repository;

import be.yorian.budgetbuddy.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentRepositoryTest extends BaseRepositoryTest {

    @Autowired
    CommentRepository commentRepository;


    @Test
    @Sql("/test-comment.sql")
    void findBySearchtermContaining() {
        Page<Comment> comments = commentRepository.findBySearchtermContaining("Test", PageRequest.of(0, 1));
        assertEquals(1, comments.getTotalElements());
        assertThat(comments.getContent().getFirst())
                .satisfies(c ->
                        assertEquals( "This is a test comment", c.getReplacement())
                );
    }
}