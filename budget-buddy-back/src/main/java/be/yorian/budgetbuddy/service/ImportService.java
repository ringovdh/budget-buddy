package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

    ImportTransactionsResponse handleImport(MultipartFile file);
}
