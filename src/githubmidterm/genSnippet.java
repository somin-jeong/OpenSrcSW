package githubmidterm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;

public class genSnippet {
	static String data;
	public static void query(String args) {
		System.out.print("입력 : ");		
		data = args;
		System.out.println(data);
	}

	public static void gS(String args) throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("input.txt");

		Reader reader = new InputStreamReader(fis);
		
		
		
		char[] buffer = new char[2000];
		int charnum = reader.read(buffer);
		reader.close();
		
		//data.contains(s)
		
	}
	
	
}
