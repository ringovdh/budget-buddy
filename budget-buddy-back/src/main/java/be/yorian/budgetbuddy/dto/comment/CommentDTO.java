package be.yorian.budgetbuddy.dto.comment;

public record CommentDTO(
        Long id,
        String searchterm,
        String replacement,
        Long categoryId) { }
