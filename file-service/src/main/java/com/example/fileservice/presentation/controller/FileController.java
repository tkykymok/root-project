package com.example.fileservice.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-service")
public class FileController {

    @GetMapping("/test")
    public ResponseEntity<?> getTest() {

        return ResponseEntity.ok("OK");
    }

}
