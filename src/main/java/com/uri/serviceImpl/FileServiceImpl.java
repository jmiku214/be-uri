package com.uri.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


import com.uri.config.FileUploadProperties;
import com.uri.service.FileService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileServiceImpl implements FileService {

	private final Path fileStorageLocation;

	@Value("${file.upload-dir}")
	private String filePath;


	public FileServiceImpl(FileUploadProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			// throw new FileStorageException("Could not create the directory where the
			// uploaded files will be stored.", ex);
		}
	}

	@Override
	public String getFileExtension(MultipartFile file) {
		if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
			return ""; // Return empty string or throw an exception, based on your requirements
		}

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null) {
			return ""; // Additional check for originalFilename being null
		}

		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
			return originalFilename.substring(dotIndex + 1);
		} else {
			return ""; // Return empty string if no extension found
		}
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {

			throw new RuntimeException("Could not create upload dir!");
		}

	}

	@Override
	public String storeFile(MultipartFile file, Long currentDate) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(currentDate + file.getOriginalFilename());
		try {
			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + file.getOriginalFilename() + ". Please try again!",
					ex);
		}
	}

	@Override
	public Resource loadFileAsResource(String fileName, HttpServletRequest request) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new RuntimeException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new RuntimeException("File not found " + fileName, ex);
		}
	}

	@Override
	public Path searchFile(String fileName) {
		try {
			System.out.println(filePath);
			Path baseDirectory = Paths.get(filePath);
			Path filePath = Files.walk(baseDirectory).filter(p -> p.getFileName().toString().equals(fileName))
					.findFirst().orElseThrow(() -> new FileNotFoundException("File not found"));

			System.out.println("File found: " + filePath);
			return filePath;
		} catch (IOException e) {
			System.err.println("An error occurred: " + e.getMessage());
		}
		return null;
	}

}
