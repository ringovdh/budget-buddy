package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CommentController {

    List<Comment> getComments();
    ResponseEntity<CustomResponse> getCommentBySearchterm(Optional<String> searchterm,
                                                          Optional<Integer> page,
                                                          Optional<Integer> size);
    Comment getCommentById(Long comment_id);
    Comment createComment(Comment comment);
    void updateComment(Long comment_id,
                       Comment comment);
    void deleteComment(Long comment_id);
}
