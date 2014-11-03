import java.util.ArrayList;


public class ArrayListHelper {
	public static String[] toArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for(int i = 0; i < array.length; i ++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
