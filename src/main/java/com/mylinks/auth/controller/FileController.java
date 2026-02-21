package com.mylinks.auth.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	 private final String UPLOAD_DIR = "uploads/";

	    @PostMapping("/upload")
	    public ResponseEntity<?> uploadImage(
	            @RequestParam("file") MultipartFile file,@RequestParam("userId") String userId
	    ) throws IOException {

	        if (file.isEmpty()) {
	            return ResponseEntity.badRequest().body(Map.of("error","File is empty"));
	        }

	        // Validate image type
	        if (!file.getContentType().startsWith("image/")) {
	            return ResponseEntity.badRequest().body(Map.of("error","Only images allowed"));
	        }

	     // 🔥 Allow only images
	        String contentType = file.getContentType();
	        
	        if (contentType == null ||
	                !(contentType.equals("image/jpeg") ||
	                  contentType.equals("image/png") ||
	                  contentType.equals("image/jpg"))) {

	                return ResponseEntity.badRequest()
	                        .body(Map.of(
		                            "error", "Only JPG, JPEG, PNG images are allowed"
		                            ));
	            }
	     // 🔥 Check size manually (extra safety)
	        if (file.getSize() > 2 * 1024 * 1024) {
	            return ResponseEntity.badRequest()
	                    .body(Map.of(
	                            "error", "File size must not exceed 2MB"
	                            ));//("File size must be less than 2MB");
	            
	            
	        }
	        String imageType = null;
	        if(contentType.equals("image/jpeg")) {
	        	imageType = ".jpeg";
	        }else if(contentType.equals("image/png")) {
	        	imageType = ".png";
	        }else if(contentType.equals("image/jpg")) {
	        	imageType = ".jpg";
	        }
	        String fileName = userId + "_image_"+LocalDate.now()+imageType;

	        Path uploadPath = Paths.get(UPLOAD_DIR);

	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        Files.copy(file.getInputStream(), uploadPath.resolve(fileName));

//	        String imageUrl = "http://localhost:8099/api/files/" + fileName;
	        String imageUrl = "D:\\MYWON\\GLOSS\\mylinks\\uploads\\"+ fileName;
	        return ResponseEntity.ok(Map.of(
	                "fileName", fileName,
	                "imageUrl", imageUrl
	        ));
	    }
}
