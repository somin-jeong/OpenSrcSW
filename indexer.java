package gitlab03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("unused")
public class indexer {
	public indexer(String args) {
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void make(String args) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {
		
		
		FileOutputStream filestream = new FileOutputStream("index.post");
		
		ObjectOutputStream oos = new ObjectOutputStream(filestream);

		Map<String, List> hashmap = new HashMap();
		
		
		
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
		String[] title = new String[child.getLength()];
		
		for(int i = 0; i < child.getLength(); i++) {
			Node node = child.item(i);

			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element ele = (Element)node;
				
				// <doc id = "0">
				String nodeName = ele.getNodeName();
				String s = null;
				
				if(nodeName.equals("doc")){
					s = ele.getAttribute("id");
				}
				
				
				// <title> <body>
				NodeList children = ele.getChildNodes();
				
				
				
				for(int j = 0; j < children.getLength(); j++) {
					Node node1 = children.item(j);
					if(node.getNodeType() == Node.ELEMENT_NODE) {
						Element ele1 = (Element)node1;
						String nodeName1 = ele1.getNodeName();
						String data = ele1.getTextContent();
						data.trim();
						if(nodeName1 == "body") {
							data = data.substring(4);
							title[i] = data;
						}
					}
				}			
			}
		}
		
		
		
		for(int j=0; j<child.getLength(); j++) {
			
			String[] str = title[j].split("#");
			
			
			for(int k=0; k<str.length; k++) {
				
				String[] word = str[k].split(":");
				
				int freq=0;
				for(int n=0; n<child.getLength(); n++) {
					String[] str1 = title[n].split("#");
					for(int m=0; m<str1.length; m++) {
						String[] word1 = str1[m].split(":");
						if(word1[0].equals(word[0])) {
							freq++;
						}
					}	
				}
				
				double integer = Integer.parseInt(word[1]);
				
				
				double db = integer * (double) (Math.log((child.getLength()/(double)freq)));
				double tf = (double)(Math.round(db*100)/100.0);

				
				
				List<Object> list = new LinkedList<>();

				Iterator<String> it = hashmap.keySet().iterator();

				while(it.hasNext()) {
					String key = it.next();
					if(key.equals(word[0])) {
						list = (List) hashmap.get(word[0]);
						
					}
				}

				
				list.add(j);
				list.add(tf);
				hashmap.put(word[0], list);
				
				
			}
		}
		
		
		
		oos.writeObject(hashmap);
		oos.close();
		
		FileInputStream fileinputstream = new FileInputStream("index.post");
		ObjectInputStream ois = new ObjectInputStream(fileinputstream);
		
		Object object = ois.readObject();
		ois.close();
		
		System.out.println("읽어온 객체의 type ->" + object.getClass());
		
		HashMap hm = (HashMap) object;
		Iterator<String> it = hm.keySet().iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			List value = (List) hm.get(key);
			System.out.println(key + " -> " + value);
		}
		
		
		
	}
}




