package be.yorian.budgetbuddy.controller.impl;

import be.yorian.budgetbuddy.controller.CommentController;
import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;


    @Autowired
    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>> getComments() {
        List<CommentDTO> comments = commentService.getComments();
        return ResponseEntity.ok(comments);
    }

    @Override
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(
            @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @Override
    @GetMapping("/searchterm")
    public ResponseEntity<Page<CommentDTO>> getCommentsBySearchterm(
            @RequestParam Optional<String> searchterm,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {
        Page<CommentDTO> comments = commentService.getCommentsBySearchterm(
                searchterm.orElse(""),
                page.orElse(0),
                size.orElse(10));
        return ResponseEntity.ok(comments);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment) {
        CommentDTO newComment = commentService.createComment(comment);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newComment.id())
                .toUri();
        return ResponseEntity.created(location).body(newComment);
    }

    @Override
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentService.updateComment(commentId, comment));
    }

    @Override
    @DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
	}

}
