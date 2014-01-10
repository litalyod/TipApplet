import FileFunctions.*;

import java.awt.*;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

public class Tipapplet extends JApplet implements Runnable {

	private final static int NUM_CHARS_PER_SEC = 13;
	private final static int NUM_QUOTES_FROM_SAME_FILE = 3;

	private final static int IMAGE_SLEEP_TIME = 40000;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fileToRead;
	String context;
	String folderToRead;
	String mainFolder = "tipFolders/";
	TextArea txtArea;
	StringBuffer quote = new StringBuffer();
	Image image = null;
	MediaTracker mt;
	Thread runner, t;
	int count = 0;
	String[] quotes;
	boolean isTextFile = true;
	int optTipLength = 10000;

	String[] contexts;
	String[] files;
	int[] weights;

	public void start() {
		runner = new Thread((Runnable) this);
		runner.start();
	}

	public void run() {
		t = Thread.currentThread();
		while (t == runner) {
			if (!readNewQuote()) {
				return;
			}
			repaint();
			if (isTextFile) {
				try {
					int timeToSleep = 1000 * quote.length() / NUM_CHARS_PER_SEC;
					sleep(timeToSleep);
				} catch (InterruptedException e) {
				}
			} else {
				try {
					sleep(IMAGE_SLEEP_TIME);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public void init() {

		txtArea = new TextArea("", 5, 100, TextArea.SCROLLBARS_NONE);
		txtArea.setEditable(false);
		txtArea.setFont(new Font("TimesRoman", Font.LAYOUT_LEFT_TO_RIGHT, 16));
		add(txtArea);
		// optTipLength = TipLength.calcTipOptLength(mainFolder);
	}

	public boolean readNewQuote() {
		if (count == 0) {
			String foldersFileName = mainFolder + "folders.txt";
			System.out.println("foldersFileName: " + foldersFileName);
			URL urlFolders = Tipapplet.class.getResource(foldersFileName);

			folderToRead = RandomFolder.calcRandomFolder(urlFolders);
			folderToRead = mainFolder + folderToRead;
			URL urlFile = Tipapplet.class.getResource(folderToRead + "/"
					+ "files.txt");
			StringBuffer strBuff = ReadFile.readFileFromPath(urlFile);
			if (strBuff == null) {
				return false;
			}
			String[] fileContent = strBuff.toString().split("\\^");
			files = FindFileParam.findSubArray(fileContent, 0);
			// fileList = fileContent[0];
			weights = FindFileParam.convertStringArrayToIntArray(FindFileParam
					.findSubArray(fileContent, 1));
			int randomFileIndex = RandomFile
					.calcRandomFileIndex(weights, files);
			contexts = FindFileParam.findSubArray(fileContent, 2);
			fileToRead = files[randomFileIndex];
			context = contexts[randomFileIndex];
			fileToRead = folderToRead + "/" + fileToRead;
			String prHtml = this.getParameter("fileToRead");
			if (prHtml != null)
				fileToRead = new String(prHtml);

			if (fileToRead.endsWith(".txt")) {
				isTextFile = true;
				URL url = Tipapplet.class.getResource(fileToRead);
				strBuff = ReadFile.ReadFileContent(url);
				if (strBuff == null) {
					return false;
				}

				String context = strBuff.toString();

				quotes = context.split("\\^");

			} else {
				isTextFile = false;
				image = new ImageIcon(getClass().getResource(fileToRead))
						.getImage();// getImage(getDocumentBase(),
									// "FileFunctions/"+fileToRead);
				count = 0;
			}
		}
		if (isTextFile) {
			quote.delete(0, quote.length());

			String tip = ReadTip.nextTip(quotes);

			while (tip.length() > optTipLength) {
				tip = ReadTip.nextTip(quotes);
			}

			quote.append(tip);
			quote.append("\n" + context);
			count++;
		}
		if (count > NUM_QUOTES_FROM_SAME_FILE) {
			count = 0;
		}
		return true;
	}

	public void paint(Graphics g) {
		/*
		 * if (!ReadNewQuote()) { return; }
		 */
		g.clearRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		if (isTextFile) {
			// add(txtArea);
			addArea();
			String string = quote.toString();
			txtArea.setText(fixString(string));
		} else {
			removeArea();
			drawImage(g);

		}

	}

	private String fixString(String string) {
		// this is a problem that Word prefers ’ to '
		return string.replace("’", "'");
	}

	private void addArea() {
		System.out.println(System.currentTimeMillis() + " adding textArea");
		txtArea.setVisible(true);
		// add(txtArea);
		System.out.println(System.currentTimeMillis() + " added textArea");
	}

	private void removeArea() {
		System.out.println(System.currentTimeMillis() + " removing area");
		txtArea.setVisible(false);
		// remove(txtArea);
		System.out.println(System.currentTimeMillis() + " removed area");
	}

	private void drawImage(Graphics g) {
		System.out.println(System.currentTimeMillis() + " draw image");
		g.drawImage(image, 0, 0, this);
		System.out.println(System.currentTimeMillis() + " drew image");
	}

	@SuppressWarnings("static-access")
	private void sleep(int timeToSleep) throws InterruptedException {
		System.out.println(System.currentTimeMillis() + " sleeping: "
				+ timeToSleep);
		t.sleep(timeToSleep);
		System.out.println(System.currentTimeMillis() + " slept: "
				+ timeToSleep);
	}

}