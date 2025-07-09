package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.entity.Comment;

public class CommentMapper {

    public static Comment mapComment(Comment existingComment, Comment newComment) {
        existingComment.setCategory(existingComment.getCategory());
        existingComment.setReplacement(newComment.getReplacement());
        existingComment.setSearchterm(newComment.getSearchterm());
        return existingComment;
    }
}
