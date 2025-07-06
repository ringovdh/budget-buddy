package be.yorian.budgetbuddy.mother;

import be.yorian.budgetbuddy.entity.Comment;

import static be.yorian.budgetbuddy.mother.CategoryMother.categoryGrocery;

public class CommentMother {

    public static Comment comment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setCategory(categoryGrocery());
        comment.setSearchterm("testing");
        comment.setReplacement("Transaction for testing (replaced)");
        return comment;
    }
}
