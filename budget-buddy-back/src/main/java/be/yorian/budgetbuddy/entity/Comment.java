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
        return searchterm.toLowerCase();
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

}
