package be.yorian.budgetbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BudgetbuddyBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetbuddyBackApplication.class, args);
    }

}
