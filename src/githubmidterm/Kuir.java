package githubmidterm;

import java.io.IOException;

public class Kuir {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(args[0].equals("-f")) {
			if(args[2].equals("-q")) {
				genSnippet.query(args[3]);
			}
			genSnippet.gS(args[1]);
		}
		
	}

}
