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

    public Category(CategoryBuilder categoryBuilder) {
        this.id = categoryBuilder.id;
        this.label = categoryBuilder.label;
        this.icon = categoryBuilder.icon;
        this.fixedcost = categoryBuilder.fixedcost;
        this.saving = categoryBuilder.saving;
        this.revenue = categoryBuilder.revenue;
        this.indetails = categoryBuilder.indetails;
        this.inmonitor = categoryBuilder.inmonitor;
        this.limitamount = categoryBuilder.limitamount;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

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

    public static class CategoryBuilder {
        private long id;
        private String label;
        private String icon;
        private boolean fixedcost;
        private boolean saving;
        private boolean revenue;
        private boolean indetails;
        private boolean inmonitor;
        private long limitamount;

        public CategoryBuilder id(long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder label(String label) {
            this.label = label;
            return this;
        }

        public CategoryBuilder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public CategoryBuilder fixedcost(boolean fixedcost) {
            this.fixedcost = fixedcost;
            return this;
        }

        public CategoryBuilder saving(boolean saving) {
            this.saving = saving;
            return this;
        }

        public CategoryBuilder revenue(boolean revenue) {
            this.revenue = revenue;
            return this;
        }

        public CategoryBuilder indetails(boolean indetails) {
            this.indetails = indetails;
            return this;
        }

        public CategoryBuilder inmonitor(boolean inmonitor) {
            this.inmonitor = inmonitor;
            return this;
        }

        public CategoryBuilder limitamount(long limitamount) {
            this.limitamount = limitamount;
            return this;
        }

        public Category build() {
            return new Category(this);
        }

    }
}
