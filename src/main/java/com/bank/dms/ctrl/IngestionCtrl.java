package com.bank.dms.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dms.service.FileTransferService;

@RestController
@RequestMapping("/dms")
public class IngestionCtrl {

	@Autowired
	FileTransferService ingestionService;
	
//	@GetMapping("/transfer/{fileName}")
//	public String transferFile(@PathVariable String fileName) {
//		return ingestionService.trasferFile(fileName);
//	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello";
	}
	
}
