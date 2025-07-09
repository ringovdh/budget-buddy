package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static be.yorian.budgetbuddy.mapper.CommentMapper.mapComment;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> getComments() {
		return commentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Comment> getCommentsBySearchterm(String searchterm, int page, int size) {
		return commentRepository.findBySearchtermContaining(searchterm, of(page, size));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Comment getCommentById(Long comment_id) {
		return commentRepository.findById(comment_id)
				.orElseThrow(() -> new EntityNotFoundException("comment_not_found"));
	}

	@Override
	public Comment saveComment(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public void updateComment(Long commentId, Comment updatedComment) {
		Comment existingComment = commentRepository.findById(commentId)
				.orElseThrow(() -> new EntityNotFoundException("comment_not_found"));
		mapComment(existingComment, updatedComment);
	}

	@Override
	public void deleteComment(Long comment_id) {
		if (!commentRepository.existsById(comment_id)){
			throw new EntityNotFoundException("comment_not_found");
		}
		commentRepository.deleteById(comment_id);
	}

}
