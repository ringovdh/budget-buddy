package be.yorian.budgetbuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tx_id;

    public String number;

    public Double amount;

    public String sign;

    public LocalDate date;

    public String comment;

    @Transient
    public String originalComment = "";

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    public Category category;

    @OneToOne
    @JoinColumn(name= "project_id", referencedColumnName = "id")
    public Project project;


    public Transaction() {}

    public Transaction(String number, Double amount, String sign, LocalDate date, String comment, Category category, Project project) {
        this.number = number;
        this.amount = amount;
        this.sign = sign;
        this.date = date;
        this.comment = comment;
        this.category = category;
        this.project = project;
    }

    public Transaction(String number) {
        this.number = number;
    }

    public long getTx_id() {
        return tx_id;
    }

    public void setTx_id(long tx_id) {
        this.tx_id = tx_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setOriginalComment(String originalComment) {
        this.originalComment = originalComment;
    }

    public String getOriginalComment() {
        return originalComment;
    }

    public double getAmountWithSign() {
        if (sign.equals("+")) {
            return amount;
        } else {
        return -amount;
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tx_id=" + tx_id +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", sign='" + sign + '\'' +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                ", category=" + category + '\'' +
                ", project=" + project +
                '}';
    }

}
