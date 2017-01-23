package com.niit.hive.util;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;

public class FileUpload {
	
	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	public static void upload(MultipartFile multifile, String filename)
	{
		logger.debug("File name = "+filename);
		//E:\\Pranil Pathare\\Project_2_Hive\\HiveBackEnd\\src\\main\\webapp\\resources\\images\\
		String path = "E:\\Pranil Pathare\\Project_2_Hive\\Hive\\WebContent\\resources\\images\\";
		path=path+filename+".jpg";
		File f=new File(path);
		if(!multifile.isEmpty())
		{
			try
			{
				byte[] bytes=multifile.getBytes();
				FileOutputStream fos=new FileOutputStream(f);
				BufferedOutputStream bos=new BufferedOutputStream(fos);
				bos.write(bytes);
				bos.close();
				logger.debug("File Uploaded Successfully");
			}
			catch(Exception e)
			{
				logger.info("Exception Arised"+e);
			}
		}
		else
		{
			logger.debug("File is Empty not Uploaded");
		}
	}
	//controller code
	//String.valueOf(prodmodel.getProduct_id())+".jpg";
	
	//MultipartFile multifile=prodmodel.getProduct_image();
	
}
