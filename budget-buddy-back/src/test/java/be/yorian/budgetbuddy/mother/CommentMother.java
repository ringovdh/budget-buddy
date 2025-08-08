package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.dto.comment.CommentDTO;
import be.yorian.budgetbuddy.entity.Category;
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
        Category category = categoryGrocery();
        return new CommentDTO(
                1L,
                "Carrefour",
                "Aankopen in Carrefour",
                category.getId(),
                category.getLabel());
    }

    public static CommentDTO updatedCommentDtoCarrefour() {
        Category category = categoryGrocery();
        return new CommentDTO(
                1L,
                "Carrefour",
                "Boodschappen in Carrefour",
                category.getId(),
                category.getLabel());
    }

    public static CommentDTO updatedCommentCarrefourUnknownCategoryDto() {
        return new CommentDTO(
                1L,
                "Carrefour",
                "Boodschappen in Carrefour",
                99L,
                null);
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
        Category category = categoryGrocery();
        return new CommentDTO(
                2L,
                "EasySave",
                "sparen via EasySave",
                category.getId(),
                category.getLabel());
    }

    public static CommentDTO newCommentDTO() {
        Category category = categoryGrocery();
        return new CommentDTO(
                0L,
                "Nieuw",
                "Nieuwe commentaar",
                category.getId(),
                null);
    }

    public static CommentDTO savedNewCommentDTO() {
        Category category = categoryGrocery();
        return new CommentDTO(
                10L,
                "Nieuw",
                "Nieuwe commentaar",
                category.getId(),
                category.getLabel());
    }

    public static CommentDTO savedUpdatedCommentDTO() {
        Category category = categoryGrocery();
        return new CommentDTO(
                10L,
                "Nieuw",
                "Aangepaste commentaar",
                category.getId(),
                category.getLabel());
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
