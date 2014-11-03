import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ListHelper {
	public static final String listFilePath = System.getenv("APPDATA") + "\\MAndWorks\\Imgur\\settings\\list.txt";
	
	public static String[] getList() {
		
		System.out.println(listFilePath);
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			
			
			File file = new File(listFilePath);

			Scanner s = new Scanner(file);
			
			while (s.hasNextLine()) {
				
				String line = s.nextLine();
				
				list.add(line);
			}
			s.close();
		} catch (Exception e) {
			//hopefully this never happens
			JFrame frame = new JFrame("Error");
			frame.add(new JLabel(e.getMessage()));
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		}
		
		System.out.print("Opened List:\n");
		for(String listItem : list)
			System.out.print(" - " + listItem + "\n");
		System.out.print("\n");
		
		return ArrayListHelper.toArray(list);
	}

	public static void save(String[] contents) {
		File file = new File(listFilePath);
		file.delete();
		try{
			file.createNewFile();
			FileWriter f = new FileWriter(file);
			String newLine = System.getProperty("line.separator");
			for(int i = 0; i < contents.length; i ++) {
				f.write(contents[i] + newLine);
			}
			f.close();
		}catch(Exception e) {
			JFrame frame = new JFrame("Error");
			frame.add(new JLabel(e.getMessage()));
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		}
		
		testSave(contents);
		
	}
	
	/**
	 * this will test to make sure these contents are in the save.
	 * if they aren't, then resave and check
	 * 
	 * indirect recursion.
	 * @param contents
	 * @return
	 */
	private static void testSave(String[] contents) {
		String[] savedContents = getList();
		
		if(contents.length != savedContents.length) save(contents);
		for(int i = 0; i < contents.length; i ++) {
			if(!contents[i].equals(savedContents[i])) save(contents);
		}
	}
	
	@SuppressWarnings("unused")
	private static String arrayToString(Object[] array) {
		String _return = "{";
		
		for(Object item : array) {
			_return += item.toString() + ", ";
		}
		
		return _return.substring(0, _return.length() - 2) + "}";
		
		
		
	}
}
