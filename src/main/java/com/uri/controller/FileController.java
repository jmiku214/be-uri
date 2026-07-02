package com.uri.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import com.uri.dto.Response;
import com.uri.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class FileController {

    @Autowired
    private FileService fileSystemStorage;

    @Value("${file.upload-dir}")
    private String filePath;

    @Value("${application.name}")
    private String applicationName;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/file/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Extract original file name and extension
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid file name");
            }

            int dotIndex = originalFileName.lastIndexOf(".");
            String fileBaseName = dotIndex != -1 ? originalFileName.substring(0, dotIndex) : originalFileName;
            String fileExtension = dotIndex != -1 ? originalFileName.substring(dotIndex) : "";

            if (!fileExtension.equals(".png") && !fileExtension.equals(".jpeg") && !fileExtension.equals(".jpg") && !fileExtension.equals(".pdf") && !fileExtension.equals(".docx")) {
                return ResponseEntity.badRequest().body("Invalid file format. Only PNG, JPEG, and JPG are allowed.");
            }

            // Normalize and construct the new file name
            String fileName = StringUtils.cleanPath(fileBaseName + "_" + System.currentTimeMillis() + fileExtension);

            // Define the upload directory
            String uploadDir = filePath; // Replace with your actual directory path

            // Construct the full file path
            Path filePath = Paths.get(uploadDir, fileName).normalize();

            // Create parent directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Save the file
            file.transferTo(filePath.toFile());

            // Generate the download URL
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/download/").path(fileName)
                    .toUriString();
            if (downloadUrl.contains("/" + applicationName + "/")) {

                downloadUrl = downloadUrl.replace("/" + applicationName + "/", "/backend/");
                downloadUrl = downloadUrl.replace("http", "https");
            }
            // Create the response
            Response<?> response = new Response<>(HttpStatus.OK.value(), "Upload successfully.", downloadUrl);
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getResponseCode()));

        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload file: " + ex.getMessage());
        }
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileSystemStorage.loadFileAsResource(fileName, request);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}