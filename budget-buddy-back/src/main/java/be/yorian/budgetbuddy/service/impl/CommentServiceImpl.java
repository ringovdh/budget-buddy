package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public List<Comment> getComments() {
		return commentRepository.findAll();
	}

	@Override
	public Page<Comment> getCommentsBySearchterm(String searchterm, int page, int size) {
		return commentRepository.findBySearchtermContaining(searchterm, of(page, size));
	}
	
	@Override
	public Optional<Comment> getCommentById(Long comment_id) {
		return commentRepository.findById(comment_id);
	}

	@Override
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	@Override
	public void updateComment(Long commentId, Comment updatedComment) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		if (optionalComment.isEmpty()) {
			throw new EntityNotFoundException("comment_not_found");
		} else {
			Comment comment = optionalComment.get();
			comment.setSearchterm(updatedComment.getSearchterm());
			comment.setReplacement(updatedComment.getReplacement());
			comment.setCategory(updatedComment.getCategory());
			commentRepository.save(comment);
		}
	}

	@Override
	public void deleteComment(Long comment_id) {
		commentRepository.deleteById(comment_id);
	}

}
