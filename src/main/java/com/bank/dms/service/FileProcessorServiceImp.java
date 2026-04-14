package com.bank.dms.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bank.dms.utility.FileDetails;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class FileProcessorServiceImp implements FileProcessorService {

	    @Value("${source.directory}")
	    private String sourceDir;

	    @Autowired
	    private FileTransferService fileTransferService;

	    public void processXml(String xmlFileName) {
	        try {
	            String baseName = xmlFileName.replace(".xml", "");

	            Path xmlPath = Paths.get(sourceDir, xmlFileName);
	            Path pdfPath = Paths.get(sourceDir, baseName + ".pdf");
	            Path jpgPath = Paths.get(sourceDir, baseName + ".jpg");

	            // ✅ Check if corresponding file exists
	            Path dataFile = null;

	            if (Files.exists(pdfPath)) {
	                dataFile = pdfPath;
	            } else if (Files.exists(jpgPath)) {
	                dataFile = jpgPath;
	            } else {
	                System.out.println("No matching file found for: " + baseName);
	                return;
	            }

	            // ✅ Parse XML using Jackson
	            FileDetails details = parseXml(xmlPath.toFile());

	            String targetFolder = details.getTargetPath();

	            // ✅ Transfer both XML + data file
	            fileTransferService.transferToDynamicPath(xmlPath, targetFolder);
	            fileTransferService.transferToDynamicPath(dataFile, targetFolder);

	            System.out.println("Processed successfully: " + baseName);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private FileDetails parseXml(File xmlFile) throws Exception {
	        XmlMapper xmlMapper = new XmlMapper();
	        return xmlMapper.readValue(xmlFile, FileDetails.class);
	    }
	}