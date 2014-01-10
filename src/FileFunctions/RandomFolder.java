package FileFunctions;

import java.net.URL;

public class RandomFolder {

	public static String calcRandomFolder(URL path) {
		String[] file;
		String[] folders;
		int[] weights;

		file = ReadFile.readFileFromPath(path).toString().split("\\^");

		folders = FindFileParam.findSubArray(file, 0);
		weights = FindFileParam.convertStringArrayToIntArray(FindFileParam
				.findSubArray(file, 1));

		return folders[calcRandomFolderIndex(weights, file[0])];

	}

	private static int calcRandomFolderIndex(int[] weights, String folderList) {
		int sumWeights = 0;
		for (int i = 0; i < weights.length; i++) {
			sumWeights += weights[i];
		}
		int randomFolderIndex = 0;
		int sumSoFar = 0;
		int randomResult = (int) (sumWeights * Math.random());
		for (; randomFolderIndex < folderList.length(); randomFolderIndex++) {
			sumSoFar += weights[randomFolderIndex];
			if (sumSoFar > randomResult) {
				break;
			}
		}
		return randomFolderIndex;
	}

}