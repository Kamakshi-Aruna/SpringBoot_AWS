package org.example.demo_aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final  AmazonS3 s3Client;
     private final String bucketName = "bucketName";

    @Autowired
    public FileService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

//    Upload a file
    public String uploadFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, file.getOriginalFilename(), inputStream, metadata);
        return "File uploaded successfully: " + file.getOriginalFilename();
    }

//    Upload a folder
    public String uploadFolder(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, file.getOriginalFilename(), inputStream, metadata);
        return "File uploaded successfully: " + file.getOriginalFilename();
    }


//    List all files in the bucket
    public List<String> listFiles() {
        return s3Client.listObjects(bucketName).getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

//    Download a file from S3
    public byte[] downloadFile(String fileName) throws IOException {
        // Get the object from S3
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            return outputStream.toByteArray();
        }
    }

//    Delete a file from s3
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return "File deleted successfully: " + fileName;
    }
}