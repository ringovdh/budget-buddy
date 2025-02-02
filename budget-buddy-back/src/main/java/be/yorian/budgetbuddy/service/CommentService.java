package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CommentService {
	
	List<Comment> getComments();
	Page<Comment> getCommentsBySearchterm(String searchterm, int page, int size);
	Optional<Comment> getCommentById(Long comment_id);
	void saveComment(Comment comment);
	void deleteComment(Long comment_id);
	void updateComment(Long commentId, Comment comment);
}
