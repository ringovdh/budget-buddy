package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.entity.Comment;

public class CommentMapper {

    public static Comment mapToComment(CommentDTO commentDto) {
        if (commentDto != null) {
            return new Comment.CommentBuilder()
                    .id(commentDto.id())
                    .searchterm(commentDto.searchterm())
                    .replacement(commentDto.replacement())
                    .build();
        }
        return null;
    }

    public static CommentDTO mapCommentToDTO(Comment comment) {
        if (comment != null) {
            return new CommentDTO(
                    comment.getId(),
                    comment.getSearchterm(),
                    comment.getReplacement(),
                    comment.getCategory().getId()
            );
        }
        return null;
    }

    public static void updateCommentFromDto(Comment comment, CommentDTO updatedComment) {
        if (comment == null || updatedComment == null) {
            return;
        }
        comment.setSearchterm(updatedComment.searchterm());
        comment.setReplacement(updatedComment.replacement());
    }
}
