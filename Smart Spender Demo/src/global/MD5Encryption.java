package global;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletContext;

public class MD5Encryption {

	public String getEncrptedString(String userPassword, ServletContext servletContext) throws IOException {

		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(userPassword.getBytes());

			// Convert byte array into signum representation. Signum is no format 1 for positive 0 for zero -1 for negative number.
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException exception) {
			String relativePath = "logs/error-log.txt";
			String rootPath = servletContext.getRealPath(relativePath);
			File logFile = new File(rootPath);
			if (!logFile.exists()) {
				logFile.mkdirs();
			}
			FileWriter fileWriter = new FileWriter(logFile, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.write(exception.getMessage() + "/n");
			printWriter.close();
		}
		return "";
	}
}