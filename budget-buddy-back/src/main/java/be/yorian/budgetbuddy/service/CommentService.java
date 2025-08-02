package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
	
	List<CommentDTO> getComments();
	CommentDTO getCommentById(Long commentId);
	Page<CommentDTO> getCommentsBySearchterm(String searchterm, int page, int size);
	CommentDTO createComment(CommentDTO comment);
	void deleteComment(Long commentId);
	CommentDTO updateComment(Long commentId, CommentDTO comment);
}
