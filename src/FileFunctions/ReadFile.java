package FileFunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ReadFile {

	public static StringBuffer readFileFromPath(URL url) {

		StringBuffer strBuff;
		String line;

		try {
			InputStream in = url.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			strBuff = new StringBuffer();
			while ((line = bf.readLine()) != null) {
				strBuff.append(line);
			}
			return strBuff;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static StringBuffer ReadFileContent(URL url) {

		StringBuffer strBuff;
		String line;

		try {
			InputStream in = url.openStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			strBuff = new StringBuffer();
			while ((line = bf.readLine()) != null) {
				strBuff.append(line + "\n");
			}
			return strBuff;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
}