package util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

	public String fileToBase64StringConversion(String filePath) throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		return encodedString;
	}

	public String getDateNow() {
		LocalDateTime instance = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
		return formatter.format(instance);
	}

}
