package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.entity.Comment;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;
import static be.yorian.budgetbuddy.mother.CategoryMother.categorySaving;

public class CommentMother {

    public static Comment comment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setCategory(categoryGrocery());
        comment.setSearchterm("testing");
        comment.setReplacement("Transaction for testing (replaced)");
        return comment;
    }

    public static Comment commentCarrefour() {
        return new Comment.CommentBuilder()
                .id(1L)
                .searchterm("Carrefour")
                .replacement("Aankopen in Carrefour")
                .category(categoryGrocery())
                .build();
    }

    public static CommentDTO commentDtoCarrefour() {
        return new CommentDTO(
                1L,
                "Carrefour",
                "Aankopen in Carrefour",
                categoryGrocery().getId());
    }

    public static CommentDTO updatedCommentDtoCarrefour() {
        return new CommentDTO(
                1L,
                "Carrefour",
                "Boodschappen in Carrefour",
                categoryGrocery().getId());
    }

    public static CommentDTO updatedCommentCarrefourUnknownCategoryDto() {
        return new CommentDTO(
                1L,
                "Carrefour",
                "Boodschappen in Carrefour",
                99L);
    }

    public static Comment easySaveComment() {
        return new Comment.CommentBuilder()
                .id(2L)
                .searchterm("EasySave")
                .replacement("sparen via EasySave")
                .category(categorySaving())
                .build();
    }

    public static CommentDTO commentDtoEasySave() {
        return new CommentDTO(
                2L,
                "EasySave",
                "sparen via EasySave",
                categorySaving().getId());
    }

    public static CommentDTO newCommentDTO() {
        return new CommentDTO(
                0L,
                "Nieuw",
                "Nieuwe commentaar",
                categoryGrocery().getId()
        );
    }

    public static CommentDTO savedNewCommentDTO() {
        return new CommentDTO(
                10L,
                "Nieuw",
                "Nieuwe commentaar",
                categoryGrocery().getId()
        );
    }

    public static CommentDTO savedUpdatedCommentDTO() {
        return new CommentDTO(
                10L,
                "Nieuw",
                "Aangepaste commentaar",
                categoryGrocery().getId()
        );
    }

    public static Comment newComment() {
        return new Comment.CommentBuilder()
                .id(0L)
                .searchterm("Nieuw")
                .replacement("Nieuwe commentaar")
                .category(categoryGrocery())
                .build();
    }
}
