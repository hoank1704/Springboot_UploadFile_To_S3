package com.anuradha.s3.controller;

import com.anuradha.s3.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "File Management", description = "Endpoints for uploading files")
@RequestMapping("")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @PostMapping("/upload")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Upload a file to public S3 bucket and convert to private S3 bucket", description = "Uploads a file to public S3 bucket, then converts it to a private S3 bucket and returns the URL of the private file")
    public ResponseEntity<String> uploadAndConvertFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileService.uploadAndConvertFile(file);
            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload and convert file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
