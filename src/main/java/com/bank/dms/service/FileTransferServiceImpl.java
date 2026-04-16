package com.bank.dms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bank.dms.exception.PathNotFoundException;

@Service
public class FileTransferServiceImpl implements FileTransferService {

//	private String sourceDir = "C:\\Users\\ramkd\\OneDrive\\Pictures\\Wallpaper";
//	private String destDir = "C:\\Users\\ramkd\\OneDrive\\Pictures";

//	@Value("${source.directory}")
//	private String sourceDir;
//
//	@Value("${destiantion.directory}")
//	private String destDir;

//	@Override
//	public void trasferFile(String fileName) {
//
////			Setting up the path
//		Path sourcePath = Paths.get(sourceDir, fileName);
//		Path destPath = Paths.get(destDir, fileName);
//
////			Checking the path and file
//		if (!Files.exists(sourcePath)) {
////				System.out.println("File Not Found: " + sourcePath.toString());
//			throw new PathNotFoundException("Can not locate the directory : " + sourcePath);
//		}
//
////			Creating a dest directory
//		try {
//			Files.createDirectories(destPath);
//
////			Moving the file
//			Files.move(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		System.out.println("File trasnferred sucessfully!!");
//
//		System.out.println("File transferred successsfully!!! " + fileName);
//	}

	@Override
	public void transferToDynamicPath(Path sourceFile, String targetDir) {

		Path targetPath = Paths.get(targetDir, sourceFile.getFileName().toString());

//		Creating a dest directory
		try {

//		Moving the file
			Files.createDirectories(targetPath.getParent());

			Files.move(sourceFile, targetPath, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Moved to: " + targetPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
