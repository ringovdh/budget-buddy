package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.mapper.CommentMapper;
import be.yorian.budgetbuddy.repository.CategoryRepository;
import be.yorian.budgetbuddy.repository.CommentRepository;
import be.yorian.budgetbuddy.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static be.yorian.budgetbuddy.mapper.CommentMapper.mapCommentToDTO;
import static be.yorian.budgetbuddy.mapper.CommentMapper.mapToComment;
import static be.yorian.budgetbuddy.mapper.CommentMapper.updateCommentFromDto;
import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final String COMMENT_NOT_FOUND = "comment_not_found";
	private final CommentRepository commentRepository;
	private final CategoryRepository categoryRepository;


	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository,
							  CategoryRepository categoryRepository) {
		this.commentRepository = commentRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommentDTO> getComments() {
		return commentRepository.findAll().stream()
				.map(CommentMapper::mapCommentToDTO).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public CommentDTO getCommentById(Long commentId) {
		Comment comment = findCommentById(commentId);
		return mapCommentToDTO(comment);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CommentDTO> getCommentsBySearchterm(String searchterm, int page, int size) {
		return commentRepository.findBySearchtermContainingIgnoreCase(searchterm, of(page, size))
				.map(CommentMapper::mapCommentToDTO);
	}

	@Override
	public CommentDTO createComment(CommentDTO commentDto) {
		Comment comment = mapToComment(commentDto);
		comment.setCategory(handleCategory(commentDto.categoryId()));
		Comment savedComment = commentRepository.save(comment);
		return mapCommentToDTO(savedComment);
	}

	@Override
	public CommentDTO updateComment(Long commentId, CommentDTO updatedComment) {
		Comment comment = findCommentById(commentId);
		updateCommentFromDto(comment, updatedComment);
		comment.setCategory(handleCategory(updatedComment.categoryId()));
		return mapCommentToDTO(comment);
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment commentToDelete = findCommentById(commentId);
		commentRepository.delete(commentToDelete);
	}

	private Comment findCommentById(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
	}

	private Category handleCategory(long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(() ->
				new EntityNotFoundException("Category not found with id: " + categoryId));
	}

}
