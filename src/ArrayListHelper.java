import java.util.ArrayList;

/**
 * 
 * this class is a simple static helper class with one job, to take arraylists
 * of strings and make arrays out of them.
 * 
 * @author Marcus
 *
 */
public class ArrayListHelper {
	public static String[] toArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for(int i = 0; i < array.length; i ++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
