package be.yorian.budgetbuddy.entity;


import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String label;
    public String icon;
    public boolean fixedcost;
    public boolean saving;
    public boolean revenue;
    public boolean indetails;
    public boolean inmonitor;
    public long limitamount;


    public Category() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() { return label; }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isFixedcost() {
        return fixedcost;
    }

    public void setFixedcost(boolean fixedcost) {
        this.fixedcost = fixedcost;
    }

    public boolean isSaving() { return saving; }

    public void setSaving(boolean saving) { this.saving = saving; }

    public boolean isIndetails() {
        return indetails;
    }

    public void setIndetails(boolean indetails) {
        this.indetails = indetails;
    }

    public boolean isInmonitor() {
        return inmonitor;
    }

    public void setInmonitor(boolean inmonitor) {
        this.inmonitor = inmonitor;
    }

    public long getLimitamount() {
        return limitamount;
    }

    public void setLimitamount(long limitamount) {
        this.limitamount = limitamount;
    }

    public boolean isRevenue() {
        return revenue;
    }

    public void setRevenue(boolean revenue) {
        this.revenue = revenue;
    }

    public boolean isOtherCost() {
        return !isFixedcost() && !isRevenue() && !isSaving();
    }
}
