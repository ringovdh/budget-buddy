package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImportController {

    ResponseEntity<CustomResponse> importTransactions(MultipartFile file);
}
