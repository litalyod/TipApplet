package FileFunctions;

public class FindFileParam {

	public static String[] findSubArray(String[] arr, int index) {

		String[] list = new String[arr.length];

		if ((index != 0) & (index != 1) & (index != 2))
			return null;

		for (int i = 0; i < arr.length; i++) {
			list[i] = arr[i].split(",")[index];
		}
		return list;

	}

	public static int[] convertStringArrayToIntArray(String[] arr) {
		int[] newArr = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			newArr[i] = Integer.parseInt(arr[i]);
		}

		return newArr;

	}

}
