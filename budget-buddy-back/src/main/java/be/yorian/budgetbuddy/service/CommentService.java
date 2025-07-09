package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
	
	List<Comment> getComments();
	Page<Comment> getCommentsBySearchterm(String searchterm, int page, int size);
	Comment getCommentById(Long comment_id);
	Comment saveComment(Comment comment);
	void deleteComment(Long comment_id);
	void updateComment(Long commentId, Comment comment);
}
