package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CommentController {

    ResponseEntity<List<CommentDTO>> getComments();

    ResponseEntity<CommentDTO> getCommentById(Long commentId);

    ResponseEntity<Page<CommentDTO>> getCommentsBySearchterm(Optional<String> searchterm,
                                                             Optional<Integer> page,
                                                             Optional<Integer> size);

    ResponseEntity<CommentDTO> createComment(CommentDTO comment);

    ResponseEntity<CommentDTO> updateComment(Long commentId, CommentDTO comment);

    ResponseEntity<Void> deleteComment(Long commentId);
}
