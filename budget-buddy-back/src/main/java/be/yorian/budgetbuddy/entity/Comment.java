package be.yorian.budgetbuddy.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String searchterm;
    private String replacement;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSearchterm() {
        return searchterm;
    }

    public void setSearchterm(String searchterm) {
        this.searchterm = searchterm;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static class CommentBuilder {
        private long id;
        private String searchterm;
        private String replacement;
        private Category category;

        public CommentBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CommentBuilder searchterm(String searchterm) {
            this.searchterm = searchterm;
            return this;
        }

        public CommentBuilder replacement(String replacement) {
            this.replacement = replacement;
            return this;
        }

        public CommentBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public Comment build() {
            Comment comment = new Comment();
            comment.setId(this.id);
            comment.setSearchterm(this.searchterm);
            comment.setReplacement(this.replacement);
            comment.setCategory(this.category);
            return comment;
        }
    }
}
