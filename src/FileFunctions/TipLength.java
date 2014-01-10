package FileFunctions;

import java.net.URL;

public class TipLength {

	public static int calcTipOptLength(String mainFolder) {

		StringBuffer tipsBuff = new StringBuffer();
		URL url1 = TipLength.class.getResource(mainFolder + "folders.txt");
		String[] file = ReadFile.readFileFromPath(url1)

		.toString().split("\\^");
		// String[] file = ReadFile.readFileFromPath(mainFolder + "folders.txt")
		// .toString().split("\\^");
		String[] folders = FindFileParam.findSubArray(file, 0);

		for (int i = 0; i < folders.length; i++) {
			String folder = mainFolder + folders[i];
			URL url2 = TipLength.class.getResource(folder + "/files.txt");
			String[] files = FindFileParam.findSubArray(ReadFile
					.readFileFromPath(url2).toString().split("^"), 0);
			// String[] files =
			// FindFileParam.findSubArray(ReadFile.readFileFromPath(folder +
			// "/files.txt")
			// .toString().split("^"),0);

			for (int j = 0; j < files.length; j++) {
				if (files[j].endsWith(".txt")) {
					URL url3 = TipLength.class.getResource(folder + "/"
							+ files[j]);
					tipsBuff.append(ReadFile.ReadFileContent(url3));
					// tipsBuff.append(ReadFile.ReadFileContent(folder + "/"
					// + files[j]));
					tipsBuff.append("^");
				}

			}

		}

		String[] tips = tipsBuff.toString().split("\\^");

		return findOptTipLength(tips);
	}

	private static int findOptTipLength(String[] tips) {
		int[] sizeTip = new int[tips.length];

		for (int i = 0; i < tips.length - 1; i++) {
			sizeTip[i] = tips[i].length();
		}

		int numOfWantedTips = (int) Math.round(0.95 * tips.length);
		bubbleSort(sizeTip);
		return sizeTip[numOfWantedTips];
	}

	private static void bubbleSort(int[] intArray) {
		int n = intArray.length;
		int temp = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {

				if (intArray[j - 1] > intArray[j]) {
					// swap the elements!
					temp = intArray[j - 1];
					intArray[j - 1] = intArray[j];
					intArray[j] = temp;
				}

			}
		}

	}

}