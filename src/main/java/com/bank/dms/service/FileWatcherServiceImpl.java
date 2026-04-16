package com.bank.dms.service;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class FileWatcherServiceImpl implements FileWatcherService {

	@Value("${source.directory}")
	private String sourceDir;

	@Autowired
	FileTransferService fileTransferService;

	@Autowired
	FileProcessorService fileProcessorService;

	@PostConstruct
	public void startWatching() {
		System.out.println("Watcher service started!!!!!");
		new Thread(this::watchDirectory).start();
	}

//	@PostConstruct
//	public void processExistingFiles() {
//		try {
//			Path dir = Paths.get(sourceDir);
//
//			Files.list(dir).filter(Files::isRegularFile).forEach(file -> {
//				System.out.println("Processing existing file: " + file.getFileName());
//				fileTransferService.trasferFile(file.getFileName().toString());
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void watchDirectory() {
		try {

			WatchService watchService = FileSystems.getDefault().newWatchService();

			Path source = Paths.get(sourceDir);

			source.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			while (true) {
				WatchKey key = watchService.take();

				for (WatchEvent<?> event : key.pollEvents()) {

					WatchEvent.Kind<?> kind = event.kind();

					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						Path fileName = (Path) event.context();
//						System.out.println("New File detected! " + fileName.toString());
						if (fileName.toString().endsWith(".xml")) {
							System.out.println("New xml detected! " + fileName.toString());
							Path fullPath = Paths.get(sourceDir, fileName.toString());
							
							if (isFileReady(fullPath)) {
//								fileTransferService.trasferFile(fileName.toString());
								fileProcessorService.processXml(fileName.toString());
							} else {
								System.out.println("XML not ready yet: " + fileName);
							}
						}

					}
				}

				key.reset();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private boolean isFileReady(Path filePath) {
		try {
			long size1 = Files.size(filePath);
			Thread.sleep(1000);

			long size2 = Files.size(filePath);
			return size1 == size2;

		} catch (Exception e) {
			return false;
		}
	}

}
