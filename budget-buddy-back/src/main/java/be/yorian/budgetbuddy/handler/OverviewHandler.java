package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public abstract class OverviewHandler {

    protected final TransactionRepository transactionRepository;

    public OverviewHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    protected String formatMonth(YearMonth yearMonth) {
        return yearMonth.
                format(DateTimeFormatter.ofPattern("MMMM yyyy")
                        .withLocale(new Locale.Builder().setLanguage("nl").setRegion("NL").build())
                );
    }
}
