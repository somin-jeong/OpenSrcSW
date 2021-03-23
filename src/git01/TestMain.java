package git01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class TestMain {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, TransformerException {
		// TODO Auto-generated method stub
		
		File file = new File("C:/Users/정소민/OneDrive/바탕 화면/2주차 실습 html");
		File[] filelist = file.listFiles();
		
		TransformerFactory transformerFactory = null;
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc = docBuilder.newDocument();
		
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		
		
		if(filelist.length>0) {
			for(int i=0; i<filelist.length; i++) {
				String filename = filelist[i].getName();

				Element doc1 = doc.createElement("doc");
				docs.appendChild(doc1);
				
				String s = String.valueOf(i);
				doc1.setAttribute("id", s);
				
				
				FileInputStream fis = new FileInputStream("C:/Users/정소민/OneDrive/바탕 화면/2주차 실습 html/"+filelist[i].getName());
				Reader reader = new InputStreamReader(fis);
				char[] buffer = new char[2000];
				int charnum = reader.read(buffer);
				reader.close();
				String data = new String(buffer, 0, charnum);
				String str = data.replaceAll("<[^>]*>", " ");

				
				str = str.trim();
				str = str.replaceFirst(filename.replace(".html", ""),"");
				System.out.println(str);
				
				
				Element title = doc.createElement("title");
				title.appendChild(doc.createTextNode(filename.replace(".html", "")));
				doc1.appendChild(title);

				
				Element body = doc.createElement("body");
				body.appendChild(doc.createTextNode(str));
				doc1.appendChild(body);
				
				
			}
		}
		

		transformerFactory = transformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("collection.xml")));
		
		transformer.transform(source, result);
			
		
		

	}
}
