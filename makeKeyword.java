package gitlab03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	
	
	@SuppressWarnings("static-access")
	public static void make(String args) throws TransformerException, ParserConfigurationException, SAXException, IOException {
        
		TransformerFactory transformerFactory = null;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document document = docBuilder.newDocument();
		
		Element docs = document.createElement("docs");
		document.appendChild(docs);

		String url = args;
		
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
		Document doc = null;
		try{
			doc = dBuilder.parse(url);
		}catch(MalformedURLException e) {
			
		}
		
		
		Element root = doc.getDocumentElement();

		
		NodeList child = root.getChildNodes();
		
		for(int i = 0; i < child.getLength(); i++) {
			Node node = child.item(i);

			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element)node;
				String nodeName = ele.getNodeName();
				String s = null;
				
				if(nodeName.equals("doc")){
					s = ele.getAttribute("id");
				}
				
				Element doc1 = document.createElement("doc");
				docs.appendChild(doc1);
				doc1.setAttribute("id", s);
				
				
				 
				NodeList children = ele.getChildNodes();
				for(int j = 0; j < children.getLength(); j++) {
					Node node1 = children.item(j);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element ele1 = (Element)node1;
						String nodeName1 = ele1.getNodeName();
						String data = ele1.getTextContent();
						data.trim();
						
						if(nodeName1.equals("title")) {
							Element title = document.createElement("title");
							title.appendChild(document.createTextNode(data));
							doc1.appendChild(title);
						}else {
							KeywordExtractor ke = new KeywordExtractor();
							KeywordList kl = ke.extractKeyword(data, true);
							
							String content = null;
							for(int k=0; k<kl.size(); k++) {
								Keyword kwrd = kl.get(k);
								content += kwrd.getString() + ":" + kwrd.getCnt() + "#";
							}
							
							Element body = document.createElement("body");
							body.appendChild(document.createTextNode(content));
							doc1.appendChild(body);
							
							
						}
						
					}
				}

			}
		}
	
		
        transformerFactory = transformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new FileOutputStream(new File("index.xml")));
		
		transformer.transform(source, result);
		

	}



}
