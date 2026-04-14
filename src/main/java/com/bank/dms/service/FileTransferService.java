package com.bank.dms.service;

import java.nio.file.Path;

public interface FileTransferService {

	void trasferFile(String fileName);

	void transferToDynamicPath(Path xmlPath, String targetFolder);

}
