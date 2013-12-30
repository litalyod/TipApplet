import FileFunctions.*;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

public class Tipapplet extends JApplet implements Runnable {

	private final static int NUM_CHARS_PER_SEC = 25;
	private final static int NUM_QUOTES_FROM_SAME_FILE = 3;

	private final static int IMAGE_SLEEP_TIME = 10000;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String fileToRead;
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

	String fileList;
	String[] weights;

	public void start() {
		runner = new Thread((Runnable) this);
		runner.start();
	}

	@SuppressWarnings("static-access")
	public void run() {
		t = Thread.currentThread();
		while (t == runner) {
			repaint();
			if (isTextFile) {
				try {
					t.sleep(1000 * quote.length() / NUM_CHARS_PER_SEC);
				} catch (InterruptedException e) {
				}
			} else {
				try {
					t.sleep(IMAGE_SLEEP_TIME);
					add(txtArea);
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
		optTipLength = TipLength.CalcTipOptLength(mainFolder);
	}

	public boolean ReadNewQuote() {
		if (count == 0) {
			folderToRead = RandomFolder.CalcRandomFolder(mainFolder);
			folderToRead = mainFolder+folderToRead;
			StringBuffer strBuff = ReadFile.ReadFileFromPath(folderToRead + "/" + "files.txt");
			if (strBuff == null) {
				return false;
			}
			String[] fileContent = strBuff.toString().split("\\^");
			fileList = fileContent[0];
			weights = fileContent[1].split(",");
			int randomFileIndex = RandomFile.calcRandomFileIndex(weights,
					fileList);
			String[] files = fileList.split(",");
			fileToRead = files[randomFileIndex];
			fileToRead = folderToRead+"/"+fileToRead;
			String prHtml = this.getParameter("fileToRead");
			if (prHtml != null)
				fileToRead = new String(prHtml);

			if (fileToRead.endsWith(".txt")) {
				isTextFile = true;
				strBuff = ReadFile.ReadFileContent(fileToRead);
				if (strBuff == null) {
					return false;
				}

				String context = strBuff.toString();

				quotes = context.split("\\^");

			} else {
				isTextFile = false;
				image = new ImageIcon(getClass().getResource("FileFunctions/"+fileToRead)).getImage();//getImage(getDocumentBase(), "FileFunctions/"+fileToRead);
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
			quote.append(optTipLength);
			count++;
		}
		if (count > NUM_QUOTES_FROM_SAME_FILE) {
			count = 0;
		}
		return true;
	}

	public void paint(Graphics g) {

		if (!ReadNewQuote()) {
			return;
		}
		g.clearRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		if (isTextFile) {
			//add(txtArea);
			txtArea.setText(quote.toString());
		} else {
			remove(txtArea);
			g.drawImage(image,0,0,this);

		}

	}

}