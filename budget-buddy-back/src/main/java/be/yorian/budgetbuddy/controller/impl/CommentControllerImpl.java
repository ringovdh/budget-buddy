package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.CommentController;
import be.yorian.budgetbuddy.entity.Comment;
import be.yorian.budgetbuddy.response.CustomResponse;
import be.yorian.budgetbuddy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;
    
    @Autowired
    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping("/comments/")
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @Override
    @GetMapping(produces = "application/json", path="/comments/searchterm")
    public ResponseEntity<CustomResponse> getCommentBySearchterm(@RequestParam Optional<String> searchterm,
                                                                 @RequestParam Optional<Integer> page,
                                                                 @RequestParam Optional<Integer> size) {
        Page<Comment> comments = commentService.getCommentsBySearchterm(searchterm.orElse(""),
                page.orElse(0), size.orElse(10));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, Page> dataMap = new HashMap<>();
        dataMap.put("page", comments);
        response.setData(dataMap);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/comments/{comment_id}")
    public Optional<Comment> getCommentById(@PathVariable("comment_id") Long commentId) {
    	return commentService.getCommentById(commentId);
    }

    @Override
    @PutMapping("/comments/{comment_id}")
    public void updateComment(@PathVariable("comment_id")Long commentId,
                              @RequestBody Comment comment) {
        commentService.updateComment(commentId, comment);
    }

    @Override
    @PostMapping("/comments/")
    public void createComment(@RequestBody Comment comment) {
        commentService.saveComment(comment);
    }
    
    @Override
    @DeleteMapping("/comments/{comment_id}")
	public void deleteComment(@PathVariable("comment_id")Long commentId) {
        commentService.deleteComment(commentId);
	}

}
