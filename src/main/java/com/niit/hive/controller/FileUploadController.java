package com.niit.hive.controller;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.niit.hive.util.FileUpload;

@RestController
@MultipartConfig(maxFileSize = 20971520)
public class FileUploadController {
	
	@RequestMapping(value = "/fileupload")
	public void upload(@RequestParam("file") MultipartFile inputFile, @RequestParam("username") String filename) {
		FileUpload.upload(inputFile, filename);
	}
}
