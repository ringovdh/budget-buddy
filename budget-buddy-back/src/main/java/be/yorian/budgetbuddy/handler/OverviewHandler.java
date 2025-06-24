package be.yorian.budgetbuddy.handler;

import be.yorian.budgetbuddy.dto.ProjectData;
import be.yorian.budgetbuddy.entity.Category;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.repository.TransactionRepository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


public class OverviewHandler {

    protected final TransactionRepository transactionRepository;

    public OverviewHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    protected List<ProjectData> retrieveProjectData(List<Transaction> transactions) {
        List<ProjectData> projectDataList = new ArrayList<>();
        transactions.stream()
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(Transaction::getProject))
                .forEach((project, txs) -> {
                    double total = txs.stream().mapToDouble(Transaction::getAmountWithSign).sum();
                    projectDataList.add(new ProjectData(project, total));
                });
        return projectDataList;
    }

    protected List<ProjectData> retrieveProjectData2(Map<Category, List<Transaction>> groupedByCategory) {
        List<ProjectData> projectDataList = new ArrayList<>();
        groupedByCategory.values().stream().flatMap(List::stream)
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(Transaction::getProject))
                .forEach((project, txs) -> {
                    double total = txs.stream().mapToDouble(Transaction::getAmountWithSign).sum();
                    projectDataList.add(new ProjectData(project, total));
                });
        return projectDataList;
    }

    protected String formatMonth(int year, int month) {
        return YearMonth.of(year, month).
                format(DateTimeFormatter.ofPattern("MMMM yyyy")
                        .withLocale(new Locale.Builder().setLanguage("nl").setRegion("NL").build())
                );
    }
}
