package com.uri.service;

import java.nio.file.Path;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public String getFileExtension(MultipartFile file);

	String storeFile(MultipartFile file, Long currentDate);

	public Resource loadFileAsResource(String fileName, HttpServletRequest request);

	public Path searchFile(String fileName);

}
