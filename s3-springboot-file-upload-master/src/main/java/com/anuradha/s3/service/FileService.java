package com.anuradha.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.anuradha.s3.entity.FileUpload;
import com.anuradha.s3.repository.FileUploadRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${aws.s3.public-bucket-name}")
    private String publicBucketName;

    private final FileUploadRepository fileUploadRepository;

    private final AmazonS3 s3Client;

    public String uploadAndConvertFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            log.error("Trống");
            throw new IllegalArgumentException("Lỗi không lấy được fileName");
        }

        String publicFileName = "images/" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        s3Client.putObject(new PutObjectRequest(publicBucketName, publicFileName, file.getInputStream(), metadata));

        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilePath(publicFileName);
        fileUpload.setFileName(originalFilename);
        fileUpload.setDateTime(LocalDateTime.now());
        fileUploadRepository.save(fileUpload);

        return getPrivateFileUrl(publicFileName);
    }

    public String getPrivateFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", publicBucketName, "ap-southeast-2", fileName);
    }

}
