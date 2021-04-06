package gitlab03;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		if(args[0].equals("-c")) {
			makeCollection.make(args[1]);
		}else if(args[0].equals("-k")) {
			makeKeyword.make(args[1]);
		}else if(args[0].equals("-i")) {
			indexer.make(args[1]);
		}else if(args[0].equals("-s")) {
			if(args[2].equals("-q")) {
				searcher.query(args[3]);
			}
			searcher.make(args[1]);
		}
		
	}

}
