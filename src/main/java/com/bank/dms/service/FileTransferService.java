package com.bank.dms.service;

import java.io.IOException;
import java.nio.file.Path;

public interface FileTransferService {

//	void trasferFile(String fileName);

	void transferToDynamicPath(Path xmlPath, String targetFolder) throws IOException;

}
