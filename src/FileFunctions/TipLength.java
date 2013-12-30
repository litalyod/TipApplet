package FileFunctions;


public class TipLength {

	public static int CalcTipOptLength(String mainFolder) {

		StringBuffer tipsBuff = new StringBuffer();
		String[] file = ReadFile.ReadFileFromPath(mainFolder+"folders.txt").toString().split("\\^");
		String[] folders = file[0].split(",");
		
		
		for (int i = 0; i < folders.length; i++) {
			String folder = mainFolder+folders[i];
			String[] files = ReadFile.ReadFileFromPath(folder+"/files.txt").toString().split("\\^")[0].split(",");
			
			for (int j = 0; j < files.length; j++) {
				if (files[j].endsWith(".txt")) {
					tipsBuff.append(ReadFile.ReadFileContent(folder+"/"+files[j]));
					// if (i != mainFolder.length-1) {
						tipsBuff.append("^");
					//}	
					
				}
				
			}
			
		}
		
		String[] tips = tipsBuff.toString().split("\\^");
		
		return FindOptTipLength(tips);
	}


	private static int FindOptTipLength(String[] tips) {
		int[] sizeTip = new int[tips.length];

		for (int i = 0; i < tips.length-1; i++) {
			sizeTip[i] = tips[i].length();
		}

		int numOfWantedTips = (int) Math.round(0.95 * tips.length);
		BubbleSort(sizeTip);
		return sizeTip[numOfWantedTips];
	}

	private static void BubbleSort(int[] intArray) {
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