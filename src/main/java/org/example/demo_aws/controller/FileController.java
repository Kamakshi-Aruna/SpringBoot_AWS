package org.example.demo_aws.controller;

import org.example.demo_aws.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    FileService fileService;

//   Show the Home page with the list of files
    @GetMapping("/")
    public String ShowHomePage(Model model) {
        populateFileList(model);
        return "HomePage";
    }

//   List the files
    @GetMapping("/listFiles")
    public String listFiles(Model model) {
        populateFileList(model);
        return "HomePage";
    }

//    Helper method to populate the file list in the model
    private void populateFileList(Model model) {
        List<String> files = fileService.listFiles();
        model.addAttribute("files", files); // Add the list of files to the model
    }

//   Uploading a file
    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String message = fileService.uploadFile(file);
            model.addAttribute("message", message);
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed: " + e.getMessage());
        }
        populateFileList(model);
        return "HomePage";
    }

//  Upload a folder
    @PostMapping("/uploadfolder")
    public String uploadFolder(@RequestParam("files") MultipartFile[] files, Model model) {
        StringBuilder message = new StringBuilder();
        try {
            for (MultipartFile file : files) {
                String uploadMessage = fileService.uploadFolder(file);
                message.append(uploadMessage).append("<br>");
            }
        } catch (IOException e) {
            message.append("File upload failed: ").append(e.getMessage());
        }
        model.addAttribute("message", message.toString());
        populateFileList(model);
        return "HomePage";
    }

//    download a file
    @GetMapping("/downloadfile")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileName) {
        try {
            byte[] data = fileService.downloadFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileName + "\"") // Set headers for download
                    .contentLength(data.length)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

//    Deleting the file
    @PostMapping("/deletefile")
    public String deleteFile(@RequestParam("fileName") String fileName, Model model) {
        String message = fileService.deleteFile(fileName);
        model.addAttribute("message", message);
        populateFileList(model);
        return "HomePage";
    }
}