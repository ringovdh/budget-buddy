package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.dto.ImportTransactionsResponse;
import be.yorian.budgetbuddy.entity.Transaction;
import be.yorian.budgetbuddy.exception.ImportTransactionException;
import be.yorian.budgetbuddy.helper.ImportResponseHelper;
import be.yorian.budgetbuddy.mapper.TransactionMapper;
import be.yorian.budgetbuddy.service.ImportService;
import be.yorian.transactionAdapterBNP.adapter.TransactionAdapter;
import be.yorian.transactionAdapterBNP.adapter.TransactionAdapterFactory;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ImportServiceImpl implements ImportService {

    private static final Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);

    private final ImportResponseHelper importResponseHelper;

    @Autowired
    public ImportServiceImpl(ImportResponseHelper importResponseHelper) {
        this.importResponseHelper = importResponseHelper;
    }

    @Override
    public ImportTransactionsResponse handleImport(MultipartFile file) {
        log.info("Starting import process for file: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            log.warn("Import failed: File is null or empty.");
            throw new ImportTransactionException("Uploaded file cannot be null or empty.");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path tempFile = null;
        List<TransactionDTO> transactionDtos;

        try {
            tempFile = Files.createTempFile("import-", "-" + originalFilename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                log.debug("Copied uploaded file to temporary path: {}", tempFile);
            }

            TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
            transactionDtos = transactionAdapter.convertPdfFile(tempFile.toFile());
            log.info("Successfully converted file, found {} potential transactions.", transactionDtos.size());

        } catch (IOException e) {
            log.error("Import failed: Could not read or write the uploaded file '{}'.", originalFilename, e);
            throw new ImportTransactionException("Failed to process uploaded file: " + originalFilename, e);
        } catch (Exception e) {
            log.error("Import failed: Error during PDF conversion for file '{}'.", originalFilename, e);
            throw new ImportTransactionException("Error converting file " + originalFilename + " using adapter.", e);
        } finally {
            if (tempFile != null) {
                try {
                    Files.deleteIfExists(tempFile);
                    log.debug("Deleted temporary file: {}", tempFile);
                } catch (IOException e) {
                    log.warn("Could not delete temporary file: {}", tempFile, e);
                }
            }
        }

        if (transactionDtos.isEmpty()) {
            log.warn("No transactions found in file: {}", originalFilename);
            transactionDtos = Collections.emptyList();
        }

        List<Transaction> transactions = transactionDtos.stream()
                .map(TransactionMapper::mapDtoToTransaction)
                .toList();
        importResponseHelper.setTransactions(transactions);
        return importResponseHelper.createImportResponse();
    }
}
